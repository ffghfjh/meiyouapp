package com.meiyou.service.impl;

import com.meiyou.mapper.ShopBuyMapper;
import com.meiyou.mapper.ShopMapper;
import com.meiyou.model.ClubVO;
import com.meiyou.model.Coordinate;
import com.meiyou.model.ShopVO;
import com.meiyou.pojo.*;
import com.meiyou.service.ShopService;
import com.meiyou.utils.Constants;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 景点商家(同城导游)业务层实现类
 * @author: Mr.Z
 * @create: 2019-08-21 17:38
 **/
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
     * @param time
     * @param password
     * @return
     */
    @Override
    public Msg addShop(Shop shop, String token, Integer time, String password,
                       Double latitude, Double longitude) {
//        if(!RedisUtil.authToken(shop.getPublishId().toString(),token)){
//            return Msg.noLogin();
//        }

        Msg msg = new Msg();
        Date now = new Date();
        shop.setUpdateTime(now);
        shop.setCreateTime(now);
        shop.setState(0);
        //添加过期时间
        Long millisecond = now.getTime()+24*60*60*1000*time;
        shop.setOutTime(new Date(millisecond));

        //获取用户密码和余额
        Float money = getUserByUid(shop.getPublishId()).getMoney();
        String payWord = getUserByUid(shop.getPublishId()).getPayWord();

        //从系统数据表获取置顶费用和发布费用
        String top_money = getRootMessage("top_money");
        String publish_money = getRootMessage("publish_money");
        //计算出支付费用
        Float pay_money = Float.valueOf(publish_money) + Float.valueOf(top_money)*time;

        if(payWord.equals("")){
            msg.setMsg("请设置支付密码!!");
            msg.setCode(1000);
            return msg;
        }else if(!payWord.equals(password)){
            msg.setMsg("支付密码错误!!!");
            msg.setCode(1001);
            return msg;
            //用户金额与发布金额进行比较
        }else if(money < pay_money){
            msg.setMsg("发布失败,账户余额不足!!!");
            msg.setCode(1002);
            return msg;
        }else {
            int rows = shopMapper.insertSelective(shop);

            if (rows != 1) {
                return Msg.fail();
            }

            //添加地理位置到缓存
            Boolean result = setPosition(latitude, longitude, shop.getId(), Constants.GEO_SHOP);
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

            return Msg.success();
        }
    }

    /**
     * 取消发布景点商家状态
     * @param uid
     * @param token
     * @param sid
     * @return
     */
    @Override
    public Msg updateShop(Integer uid, String token, Integer sid) {
//        if(!RedisUtil.authToken(uid.toString(),token)){
//            return Msg.noLogin();
//        }
        Integer status = shopMapper.selectByPrimaryKey(sid).getState();
        if(status!=0){
            return Msg.fail();
        }
        //设置状态为2，已失效
        Shop shop = new Shop();
        shop.setState(2);

        ShopExample shopExample = new ShopExample();
        shopExample.createCriteria().andPublishIdEqualTo(uid).andIdEqualTo(sid);
        int rows = shopMapper.updateByExampleSelective(shop, shopExample);
        if (rows != 1){
            return Msg.fail();
        }
        return Msg.success();
    }

    /**
     * 查找用户的shop
     * @param uid
     * @param token
     * @return
     */
    @Override
    public Msg selectByUid(Integer uid, String token) {
//        if(!RedisUtil.authToken(uid.toString(),token)){
//            return Msg.noLogin();
//        }
        Msg msg = new Msg();
        ShopExample shopExample = new ShopExample();
        shopExample.createCriteria().andPublishIdEqualTo(uid);
        List<Shop> result = shopMapper.selectByExample(shopExample);
        if(result == null){
            msg.setCode(404);
            msg.setMsg("没有找到指定对象的Shop");
            return msg;
        }

        //添加人数到VO类中
        ArrayList<ShopVO> shopVOS = new ArrayList<>();
        for(Shop shop : result){
            //把每一个重新赋值的shopVOS类加到新的集合中
            shopVOS.add(setShopToShopVO(shop));
        }

        msg.add("shopVOS",shopVOS);
        msg.setMsg("成功");
        msg.setCode(100);
        return msg;
    }

    /**
     * 查找指定的景点商家
     * @param uid
     * @param token
     * @param sid
     * @return
     */
    @Override
    public Msg selectBySid(Integer uid, String token, Integer sid) {
//        if(!RedisUtil.authToken(uid.toString(),token)){
//            return Msg.noLogin();
//        }
        Msg msg = new Msg();
        ShopExample shopExample = new ShopExample();
        shopExample.createCriteria().andIdEqualTo(sid);
        List<Shop> result = shopMapper.selectByExample(shopExample);
        if(result == null){
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

    @Override
    public Msg selectShop(float longitude, float latitude) {
        return null;
    }

    /**
     * 把Shop对象中的值转移到ShopVO对象中
     * @param Shop
     * @return
     */
    public ShopVO setShopToShopVO(Shop Shop){

        //查找报名每个会所的人数
        ShopBuyExample example = new ShopBuyExample();
        example.createCriteria().andStateBetween(0,1).andGuideIdEqualTo(Shop.getId());
        Integer nums = shopBuyMapper.selectByExample(example).size();

        ShopVO shopVO = new ShopVO();
        shopVO.setNums(nums);
        shopVO.setId(Shop.getId());
        shopVO.setCreateTime(Shop.getCreateTime());
        shopVO.setUpdateTime(Shop.getUpdateTime());
        shopVO.setPublishId(Shop.getPublishId());
        shopVO.setImgsUrl(Shop.getImgsUrl());
        shopVO.setOutTime(Shop.getOutTime());
        shopVO.setState(Shop.getState());
        shopVO.setServiceArea(Shop.getServiceArea());
        shopVO.setCharge(Shop.getCharge());
        shopVO.setTravelTime(Shop.getTravelTime());
        shopVO.setBoolClose(Shop.getBoolClose());

        return shopVO;
    }
}
