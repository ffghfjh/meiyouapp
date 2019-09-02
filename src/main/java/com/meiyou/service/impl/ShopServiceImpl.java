package com.meiyou.service.impl;

import com.meiyou.mapper.ShopBuyMapper;
import com.meiyou.mapper.ShopMapper;
import com.meiyou.model.ShopVO;
import com.meiyou.myEnum.StateEnum;
import com.meiyou.myEnum.TimeTypeEnum;
import com.meiyou.pojo.*;
import com.meiyou.service.ShopService;
import com.meiyou.utils.Constants;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.GeoRadiusResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 景点商家(同城导游)业务层实现类
 * @author: Mr.Z
 * @create: 2019-08-21 17:38
 **/
//@CacheConfig()
@Service
public class ShopServiceImpl extends BaseServiceImpl implements ShopService{

    @Autowired
    ShopMapper shopMapper;

    @Autowired
    ShopBuyMapper shopBuyMapper;

    /**
     * 发布
     * @param shop
     * @param token
     * @param timeType 置顶天数类型
     * @param password
     * @return
     */
    @Override
    @Transactional
    @Cacheable(cacheNames = "shop")
    public Msg addShop(Shop shop, String token, String timeType, String password,
                       Double longitude, Double latitude)  {
        if(!RedisUtil.authToken(shop.getPublishId().toString(),token)){
            return Msg.noLogin();
        }

        Msg msg = new Msg();
        Date now = new Date();
        shop.setUpdateTime(now);
        shop.setCreateTime(now);
        shop.setState(StateEnum.INIT.getValue());

        Integer days = 0;
        String top_money = null;

        TimeTypeEnum type = TimeTypeEnum.getTimeTypeByDesc(timeType);
        if(type == null){
            msg.setMsg("传入的时间类型有错");
            msg.setCode(506);
            return msg;
        }
        switch (type){
            case DAY:
                days = type.getValue();
                top_money = getRootMessage(timeType);
                break;
            case WEEK:
                days = type.getValue();
                top_money = getRootMessage(timeType);
                break;
            case MONTH:
                days = type.getValue();
                top_money = getRootMessage(timeType);
                break;
            case QUARTER:
                days = type.getValue();
                top_money = getRootMessage(timeType);
                break;
            case YEAR:
                days = type.getValue();
                top_money = getRootMessage(timeType);
                break;
        }

        //添加过期时间
        Long millisecond = System.currentTimeMillis()+days*1000*60*60*24L;
        shop.setOutTime(new Date(millisecond));

        //获取用户密码和余额
        Float money = getUserByUid(shop.getPublishId()).getMoney();
        String payWord = getUserByUid(shop.getPublishId()).getPayWord();

        //从系统数据表获取发布费用
        String publish_money = getRootMessage("publish_money");

        //计算出支付费用
        Float pay_money = Float.valueOf(publish_money) + Float.valueOf(top_money);

        if(payWord == null){
            msg.setMsg("请设置支付密码!!");
            msg.setCode(1000);
            return msg;
        }
        if(!password.equals(payWord)){
            msg.setMsg("支付密码错误!!!");
            msg.setCode(1001);
            return msg;
            //用户金额与发布金额进行比较
        }
        //用户金额与支付金额进行比较
        if(money < pay_money){
            msg.setMsg("发布失败,账户余额不足!!!");
            msg.setCode(1002);
            return msg;
        }else {
            int rows = shopMapper.insertSelective(shop);

            if (rows != 1) {
                return Msg.fail();
            }

            //添加地理位置到缓存
            Boolean result = setPosition(longitude, latitude, shop.getId(), Constants.GEO_SHOP);
            if (!result) {
                msg.setCode(505);
                msg.setMsg("获取地理位置失败");
                return msg;
            }
            System.out.println("获取地理位置成功");

            //执行扣钱操作
            User user = new User();
            money = money - pay_money;
            user.setMoney(money);
            user.setUpdateTime(new Date());

            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(shop.getPublishId());
            userMapper.updateByExampleSelective(user,userExample);

            msg.setMsg("获取地理位置成功");
            msg.setCode(100);
            return msg;
        }
    }

    /**
     * 取消发布景点商家状态
     * @param uid
     * @param token
     * @param sid
     * @return
     */
    @Transactional
    @Override
    //@CachePut(key = "#result.id")
    public Msg updateShop(Integer uid, String token, Integer sid) {
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }
        Integer status = shopMapper.selectByPrimaryKey(sid).getState();
        if(status!=StateEnum.INIT.getValue()){
            return Msg.fail();
        }
        //设置状态为2，已失效
        Shop shop = new Shop();
        shop.setState(StateEnum.INVALID.getValue());
        shop.setUpdateTime(new Date());

        ShopExample shopExample = new ShopExample();
        shopExample.createCriteria().andPublishIdEqualTo(uid).andIdEqualTo(sid);
        int rows = shopMapper.updateByExampleSelective(shop, shopExample);
        if (rows != 1){
            return Msg.fail();
        }

        //取消发布退钱(发布金+置顶金)

        String publish_money = getRootMessage("publish_money");

        Shop sp = shopMapper.selectByPrimaryKey(sid);
        Date outTime = sp.getOutTime();
        Date createTime = sp.getCreateTime();

        Long second = outTime.getTime() - createTime.getTime();
        Long days = second/(1000*24*60*60);

        //获取置顶金额
        TimeTypeEnum timeTypeEnum = TimeTypeEnum.getTimeTypeByValue(Integer.parseInt(String.valueOf(days)));
        String top_money = getRootMessage(timeTypeEnum.getDesc());

        //获取用户的钱进行计算退款
        User user = getUserByUid(uid);
        Float money = user.getMoney();

        money = money + Float.valueOf(publish_money)+Float.valueOf(top_money);

        //执行退款
        User own = new User();
        own.setMoney(money);
        own.setId(uid);
        own.setUpdateTime(new Date());
        int i = userMapper.updateByPrimaryKeySelective(own);
        if (i != 1){
            return Msg.fail();
        }

        return Msg.success();
    }

    /**
     * 查找指定的景点商家
     * @param uid
     * @param token
     * @param sid
     * @return
     */
    @Override
    //@Cacheable()
    public Msg selectBySid(Integer uid, String token, Integer sid) {
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }
        Msg msg = new Msg();
        ShopExample shopExample = new ShopExample();
        shopExample.createCriteria().andIdEqualTo(sid);
        List<Shop> result = shopMapper.selectByExample(shopExample);
        if(result == null && result.size() == 0){
            msg.setCode(404);
            msg.setMsg("没有找到指定的Shop");
            return msg;
        }

        //把购买数量重新赋值给shopVO类
        ShopVO shopVO = setShopToShopVO(result.get(0));

        //返回带有人数参数的ShopVO对象
        msg.add("shopVO",shopVO);
        msg.setMsg("成功");
        msg.setCode(100);
        return msg;
    }

    /**
     * 查找附近的shop
     * @param uid
     * @param token
     * @param longitude
     * @param latitude
     * @return
     */
    @Override
    @Caching(
            cacheable = {
                    @Cacheable(value = "nearShop")
            },
            put = {
                    //先执行方法
                    @CachePut(value = "nearShop"),
            }
    )
    public Msg selectShopByPosition(Integer uid, String token, Double longitude, Double latitude) {
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }
        Msg msg = new Msg();

        //查找附近的key
        List<GeoRadiusResponse> geoRadiusResponses = getShopGeoRadiusResponse(uid,longitude,latitude);

        if(geoRadiusResponses == null){
            return Msg.fail();
        }

        List<ShopVO> shopVOS = new ArrayList<>();
        for(GeoRadiusResponse result : geoRadiusResponses){
            //获取id
            String member = result.getMemberByString();

            //距离我多远
            Double dis = result.getDistance();
            if (dis != null) {
                dis = 0.00;
            }

            Integer id = Integer.valueOf(member);

            //通过id查找shop
            ShopExample example = new ShopExample();
            example.createCriteria().andIdEqualTo(id);
            List<Shop> shops = shopMapper.selectByExample(example);
            if(shops.isEmpty()){
                msg.setCode(404);
                msg.setMsg("附近没有找到景点商家");
                return msg;
            }

            if(uid == shops.get(0).getPublishId()){
                continue;
            }

            //把shop的值转换到ShopVO中
            ShopVO shopVO = setShopToShopVO(shops.get(0));
            shopVO.setDistance(dis);

            shopVOS.add(shopVO);
        }

        msg.add("shopVOS",shopVOS);
        msg.setMsg("成功");
        msg.setCode(100);

        return msg;
    }
}
