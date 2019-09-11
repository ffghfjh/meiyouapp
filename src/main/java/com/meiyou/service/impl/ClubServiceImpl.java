package com.meiyou.service.impl;

import com.alibaba.druid.pool.GetConnectionTimeoutException;
import com.meiyou.mapper.ClubBuyMapper;
import com.meiyou.mapper.ClubMapper;
import com.meiyou.mapper.ClubStarMapper;
import com.meiyou.model.ClubVO;
import com.meiyou.model.Coordinate;
import com.meiyou.myEnum.StateEnum;
import com.meiyou.myEnum.TimeTypeEnum;
import com.meiyou.pojo.*;
import com.meiyou.service.ClubService;
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
import java.util.stream.Collectors;

/**
 * @description: 按摩会所业务层接口实现类
 * @author: Mr.Z
 * @create: 2019-08-21 14:31
 **/
//@CacheConfig(cacheNames = "club")
@Service
public class ClubServiceImpl extends BaseServiceImpl implements ClubService {

    @Autowired
    ClubMapper clubMapper;

    @Autowired
    ClubBuyMapper clubBuyMapper;

    @Autowired
    ClubStarMapper clubStarMapper;

    /**
     * 发布按摩会所
     * @param club
     * @param timeType 置顶天数类型
     * @param password 支付密码
     * @return
     */
    @Override
    @Transactional
    @Cacheable(cacheNames = "club")
    public Msg addClub(Club club,String token, String timeType, String password, Double longitude, Double latitude) {
        if(!RedisUtil.authToken(club.getPublishId().toString(),token)){
            return Msg.noLogin();
        }
        Msg msg = new Msg();
        Date now = new Date();
        club.setCreateTime(now);
        club.setUpdateTime(now);

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
        club.setOutTime(new Date(millisecond));
        club.setState(StateEnum.INIT.getValue());

        //获取用户密码和余额
        String payWord = getUserByUid(club.getPublishId()).getPayWord();
        Float money = getUserByUid(club.getPublishId()).getMoney();

        //从系统数据表获取发布费用
        String publish_money = getRootMessage("publish_money");

        //计算支付金额
        Float pay_money = Float.valueOf(top_money) + Float.valueOf(publish_money);

        if(payWord == null){
            msg.setMsg("请设置支付密码!");
            msg.setCode(1000);
            return msg;
        }
        if(!password.equals(payWord)){
            msg.setMsg("支付密码错误!");
            msg.setCode(1001);
            return msg;
        }
        //用户金额与支付金额进行比较
        if(money < pay_money){
            msg.setMsg("发布失败,账户余额不足!");
            msg.setCode(1002);
            return msg;
        }else {
            int rows = clubMapper.insertSelective(club);

            if (rows != 1) {
                return Msg.fail();
            }

            //添加地理位置到缓存
            Boolean result = setPosition(longitude, latitude, club.getId(), Constants.GEO_CLUB);
            if (!result) {
                msg.setCode(505);
                msg.setMsg("获取地理位置失败");
                return msg;
            }

            //执行扣钱操作
            User user = new User();
            money = money - pay_money;
            user.setMoney(money);
            user.setUpdateTime(new Date());

            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(club.getPublishId());
            userMapper.updateByExampleSelective(user,example);

            msg.setMsg("发布成功,获取地理位置成功!");
            msg.setCode(100);
            return msg;
        }
    }

    /**
     * 取消发布 只更改状态
     * @param uid
     * @param cid
     * @return
     */
    @Transactional
    @Override
    //@CachePut(key = "#result.id")
    public Msg updateClub(Integer uid,String token, Integer cid) {
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }

        Integer state = clubMapper.selectByPrimaryKey(cid).getState();
        if(state != StateEnum.INIT.getValue()){
            return Msg.fail();
        }
        //更改状态为已失效
        Club club = new Club();
        club.setState(StateEnum.INVALID.getValue());
        club.setUpdateTime(new Date());

        ClubExample example = new ClubExample();
        example.createCriteria().andIdEqualTo(cid).andPublishIdEqualTo(uid);
        int rows = clubMapper.updateByExampleSelective(club,example);

        if(rows != 1){
            return Msg.fail();
        }

        //取消发布退钱(发布金+置顶金)

        String publish_money = getRootMessage("publish_money");

        Club cb = clubMapper.selectByPrimaryKey(cid);
        Date outTime = cb.getOutTime();
        Date createTime = cb.getCreateTime();

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
     * 发布者不想看这个发布的了
     * @param uid
     * @param token
     * @param clubId
     * @return
     */
    @Override
    public Msg updateClubIgnore(Integer uid, String token, Integer clubId) {
//        if(!RedisUtil.authToken(uid.toString(),token)){
//            return Msg.noLogin();
//        }

        Integer state = clubMapper.selectByPrimaryKey(clubId).getState();
        if(state == StateEnum.INVALID.getValue() || state == StateEnum.EXPIRE.getValue()){
            Club club = new Club();
            club.setId(clubId);
            club.setUpdateTime(new Date());
            club.setState(StateEnum.IGNORE.getValue());

            clubMapper.updateByPrimaryKeySelective(club);
            return Msg.success();
        }
        return Msg.fail();
    }

    /**
     * 发布者不想看了
     * @param uid
     * @param token
     * @param clubBuyId
     * @return
     */
    @Override
    public Msg updateClubBuyDelete(Integer uid, String token, Integer clubBuyId) {
//        if(RedisUtil.authToken(uid.toString(),token)){
//            return Msg.noLogin();
//        }

        ClubBuy clubBuy = new ClubBuy();
        clubBuy.setId(clubBuyId);
        clubBuy.setUpdateTime(new Date());

        Integer state = clubBuyMapper.selectByPrimaryKey(clubBuyId).getState();
        Msg msg = new Msg();
        //只有状态是已完成、已取消、报名者已删除才可以进行删除操作
        switch (state){
            case 1:
                clubBuy.setState(StateEnum.RESET.getValue());
                break;
            case 2:
                clubBuy.setState(StateEnum.CUT.getValue());
                break;
            case 3:
                clubBuy.setState(StateEnum.IGNORE.getValue());
                break;
            case 7:
                clubBuy.setState(StateEnum.IGNORE.getValue());
                break;
            default:
                msg.setMsg("此状态不能修改");
                msg.setCode(100);
                return msg;
        }
        clubBuyMapper.updateByPrimaryKeySelective(clubBuy);

        return Msg.success();
    }

    /**
     * 查看指定的按摩会所
     * @param cid
     * @return
     */
    @Override
    public Msg selectByCid(Integer uid,String token,Integer cid) {
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }

        Msg msg = new Msg();
        ClubExample example = new ClubExample();
        example.createCriteria().andIdEqualTo(cid);
        List<Club> result = clubMapper.selectByExample(example);
        if(result == null && result.size() == 0){
            msg.setMsg("没有找到对应的Club");
            msg.setCode(404);
            return msg;
        }

        //把购买数量重新赋值给clubVO类
        ClubVO clubVO = setClubToClubVO(result.get(0));

        //返回带有人数参数的ClubVO对象
        msg.add("clubVO",clubVO);
        msg.setCode(100);
        msg.setMsg("成功");
        return msg;
    }

    /**
     * 查找附近的club
     * @param uid
     * @param token
     * @param longitude
     * @param latitude
     * @return
     */
    @Override
    @Caching(
            cacheable = {
                    @Cacheable(value = "nearClub")
            },
            put = {
                    //先执行方法
                    @CachePut(value = "nearClub"),
            }
    )
    public Msg selectClubByPosition(Integer uid, String token, Double longitude, Double latitude) {
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }
        Msg msg = new Msg();

        //查找附近的key
        List<GeoRadiusResponse> geoRadiusResponses = getClubGeoRadiusResponse(uid,longitude,latitude);

        if( geoRadiusResponses.isEmpty() && geoRadiusResponses == null){
        return Msg.fail();
        }

        List<ClubVO> clubVOS = new ArrayList<>();
        for(GeoRadiusResponse result : geoRadiusResponses){
            //获取id
            String member = result.getMemberByString();
            Integer id = Integer.valueOf(member);

            //距离我多远
            Double dis = result.getDistance();
            if (dis != null) {
                dis = 0.00;
            }


            //通过id查找club
            ClubExample example = new ClubExample();
            example.createCriteria()
                    .andIdEqualTo(id)
                    .andOutTimeGreaterThan(new Date())
                    .andStateBetween(StateEnum.INIT.getValue(),StateEnum.COMPLETE.getValue());
            List<Club> clubs = clubMapper.selectByExample(example);
            if(clubs.isEmpty()){
                continue;
            }

            //把club的值转换到ClubVO中
            ClubVO clubVO = setClubToClubVO(clubs.get(0));
            clubVO.setDistance(dis);

            clubVOS.add(clubVO);
        }

        for (int i = 0; i < clubVOS .size(); i++)    {
            for (int j = clubVOS .size()-1; j > i; j--)  {
                Long time= clubVOS .get(j).getCreateTime().getTime();
                Long time1= clubVOS .get(j-1).getCreateTime().getTime();
                if (time.compareTo(time1)>0)    {
                    //互换位置
                    ClubVO clubVO = clubVOS.get(j);
                    clubVOS.set(j, clubVOS.get(j-1));
                    clubVOS.set(j-1, clubVO );
                }
            }
        }

        msg.add("clubVOS",clubVOS);
        msg.setMsg("成功");
        msg.setCode(100);

        return msg;
    }
}
