package com.meiyou.service.impl;

import com.meiyou.mapper.ClubBuyMapper;
import com.meiyou.mapper.ClubMapper;
import com.meiyou.model.ClubVO;
import com.meiyou.model.Coordinate;
import com.meiyou.pojo.*;
import com.meiyou.service.ClubService;
import com.meiyou.utils.Constants;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.GeoRadiusResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @description: 按摩会所业务层接口实现类
 * @author: Mr.Z
 * @create: 2019-08-21 14:31
 **/
@Service
public class ClubServiceImpl extends BaseServiceImpl implements ClubService {

    @Autowired
    ClubMapper clubMapper;

    @Autowired
    ClubBuyMapper clubBuyMapper;

    /**
     * 发布按摩会所
     * @param club
     * @param time 置顶天数
     * @param password 支付密码
     * @return
     */
    @Override
    @Transactional
    public Msg addClub(Club club,String token, Integer time, String password, Double latitude, Double longitude) {
//        if(!RedisUtil.authToken(club.getPublishId().toString(),token)){
//            return Msg.noLogin();
//        }
        Msg msg = new Msg();
        Date now = new Date();
        club.setCreateTime(now);
        club.setUpdateTime(now);
        //添加过期时间
        Long millisecond = now.getTime()+24*60*60*1000*time;
        club.setOutTime(new Date(millisecond));
        club.setState(0);

        //获取用户密码和余额
        String payWord = getUserByUid(club.getPublishId()).getPayWord();
        Float money = getUserByUid(club.getPublishId()).getMoney();

        //从系统数据表获取置顶费用和发布费用
        String top_money = getRootMessage("top_money");
        String publish_money = getRootMessage("publish_money");

        //计算支付金额
        Float pay_money = Float.valueOf(top_money) * time + Float.valueOf(publish_money);

        if(payWord.equals("")){
            msg.setMsg("请设置支付密码!");
            msg.setCode(1000);
            return msg;
        }
        if(!payWord.equals(password)){
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
            Boolean result = setPosition(latitude, longitude, club.getId(), Constants.GEO_CLUB);
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

            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(club.getPublishId());
            userMapper.updateByExampleSelective(user,example);

            return Msg.success();
        }
    }

    /**
     * 取消发布 只更改状态
     * @param uid
     * @param cid
     * @return
     */
    @Override
    public Msg updateClub(Integer uid,String token, Integer cid) {
//        if(!RedisUtil.authToken(club.getPublishId().toString(),token)){
//            return Msg.noLogin();
//        }

        Integer state = clubMapper.selectByPrimaryKey(cid).getState();
        if(state != 0){
            return Msg.fail();
        }
        //更改状态为已失效
        Club club = new Club();
        club.setState(2);
        club.setUpdateTime(new Date());

        ClubExample example = new ClubExample();
        example.createCriteria().andIdEqualTo(cid);
        int rows = clubMapper.updateByExampleSelective(club,example);

        if(rows != 1){
            return Msg.fail();
        }
        return Msg.success();
    }

    /**
     * 查找指定用户发布的有效按摩会所
     * @param uid
     * @return
     */
    @Override
    public Msg selectByUid(Integer uid,String token) {
//        if(!RedisUtil.authToken(club.getPublishId().toString(),token)){
//            return Msg.noLogin();
//        }

        Msg msg = new Msg();
        //查找发布出去的有效按摩会所
        ClubExample clubExample = new ClubExample();
        clubExample.createCriteria().andPublishIdEqualTo(uid);
        List<Club> result = clubMapper.selectByExample(clubExample);
        if(result.size() == 0){
            msg.setMsg("没有找到对应的Club");
            msg.setCode(404);
            return msg;
        }

        ArrayList<ClubVO> clubVOS = new ArrayList<>();
        for(Club club : result){
            //把每一个重新赋值的clubVO类加到新的集合中
            clubVOS.add(setClubToClubVO(club));
        }

        msg.add("clubVO",clubVOS);
        msg.setCode(100);
        msg.setMsg("成功");
        return msg;
    }

    /**
     * 查看指定的按摩会所
     * @param cid
     * @return
     */
    @Override
    public Msg selectByCid(Integer uid,String token,Integer cid) {
//        if(!RedisUtil.authToken(uid.toString(),token)){
//            return Msg.noLogin();
//        }

        Msg msg = new Msg();
        ClubExample example = new ClubExample();
        example.createCriteria().andIdEqualTo(cid);
        List<Club> result = clubMapper.selectByExample(example);
        if(result.size() == 0){
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
    public Msg selectClubByPosition(Integer uid, String token, Double longitude, Double latitude) {
//        if(!RedisUtil.authToken(uid.toString(),token)){
//            return Msg.noLogin();
//        }
        String range = getRootMessage("range");
        Coordinate coordinate = new Coordinate();
        coordinate.setKey(Integer.toString(uid));
        coordinate.setLatitude(latitude);
        coordinate.setLongitude(longitude);

        List<GeoRadiusResponse> geoRadiusResponses = RedisUtil.geoQueryClub(coordinate, Double.valueOf(range));
        if(geoRadiusResponses.size() == 0){
            return Msg.fail();
        }

        ArrayList<ClubVO> clubVOS = new ArrayList<>();
        for(GeoRadiusResponse result : geoRadiusResponses){
            //获取id
            String member = result.getMemberByString();

            //距离我多远
            Double dis = result.getDistance();
            if (dis != null) {
                dis = 0.00;
            }
            Integer id = Integer.valueOf(member);
            Club club = clubMapper.selectByPrimaryKey(id);

            ClubVO clubVO = new ClubVO();
            clubVO.setId(club.getId());
            clubVO.setCreateTime(club.getCreateTime());
            clubVO.setUpdateTime(club.getUpdateTime());
            clubVO.setPublishId(club.getPublishId());
            clubVO.setImgsUrl(club.getImgsUrl());
            clubVO.setProjectName(club.getProjectName());
            clubVO.setProjectDesc(club.getProjectDesc());
            clubVO.setProjectAddress(club.getProjectAddress());
            clubVO.setProjectPrice(club.getProjectPrice());
            clubVO.setMarketPrice(club.getMarketPrice());
            clubVO.setOutTime(club.getOutTime());
            clubVO.setState(club.getState());
            clubVO.setDistance(dis);

            clubVOS.add(clubVO);
        }

        Msg msg = new Msg();
        msg.add("clubVOS",clubVOS);
        msg.setMsg("成功");
        msg.setCode(100);

        return msg;
    }

    /**
     * 把Club对象中的值转移到ClubVO对象中
     * @param club
     * @return
     */
    public ClubVO setClubToClubVO(Club club){

        //查找报名每个会所的人数
        ClubBuyExample example = new ClubBuyExample();
        example.createCriteria().andStateBetween(0,1).andClubIdEqualTo(club.getId());
        Integer nums = clubBuyMapper.selectByExample(example).size();

        ClubVO clubVO = new ClubVO();
        clubVO.setNums(nums);
        clubVO.setId(club.getId());
        clubVO.setCreateTime(club.getCreateTime());
        clubVO.setUpdateTime(club.getUpdateTime());
        clubVO.setPublishId(club.getPublishId());
        clubVO.setImgsUrl(club.getImgsUrl());
        clubVO.setProjectName(club.getProjectName());
        clubVO.setProjectDesc(club.getProjectDesc());
        clubVO.setProjectAddress(club.getProjectAddress());
        clubVO.setProjectPrice(club.getProjectPrice());
        clubVO.setMarketPrice(club.getMarketPrice());
        clubVO.setOutTime(club.getOutTime());
        clubVO.setState(club.getState());

        return clubVO;
    }
}
