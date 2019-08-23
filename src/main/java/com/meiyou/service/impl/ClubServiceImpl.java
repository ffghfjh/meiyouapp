package com.meiyou.service.impl;

import com.meiyou.mapper.ClubMapper;
import com.meiyou.model.Coordinate;
import com.meiyou.pojo.Club;
import com.meiyou.pojo.ClubExample;
import com.meiyou.pojo.User;
import com.meiyou.service.ClubService;
import com.meiyou.utils.Constants;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

        if(payWord.equals("")){
            msg.setMsg("请设置支付密码!");
            msg.setCode(1000);
            return msg;
        }
        if(!payWord.equals(password)){
            msg.setMsg("支付密码错误!");
            msg.setCode(1001);
            return msg;
            //用户金额与发布金额进行比较
            //Todo
        }
        if(money < Float.valueOf(top_money)*time){
            msg.setMsg("发布失败,账户余额不足!");
            msg.setCode(1002);
            return msg;
        }else {
            int rows = clubMapper.insertSelective(club);

            if (rows != 1) {
                return Msg.fail();
            }

            //添加地理位置到缓存
            String message = setPosition(latitude, longitude, club.getId(), Constants.GEO_CLUB);
            System.out.println(message);

            //执行扣钱操作
            User user = new User();
            //Todo
            money = money - Float.valueOf(top_money)*time - Float.valueOf(publish_money);
            user.setMoney(money);
            user.setId(club.getPublishId());
            user.setUpdateTime(new Date());
            userMapper.updateByPrimaryKeySelective(user);
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
        //Todo
        Club club = new Club();
        club.setPublishId(uid);
        club.setId(cid);
        club.setState(2);
        club.setUpdateTime(new Date());
        int rows = clubMapper.updateByPrimaryKeySelective(club);
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

        //Todo 人数
        msg.add("club",result);
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
//        if(!RedisUtil.authToken(club.getPublishId().toString(),token)){
//            return Msg.noLogin();
//        }

        Msg msg = new Msg();
        Club result = clubMapper.selectByPrimaryKey(cid);
        if(result == null){
            msg.setMsg("没有找到对应的Club");
            msg.setCode(404);
            return msg;
        }

        //Todo 人数
        msg.add("club",result);
        msg.setCode(100);
        msg.setMsg("成功");
        return msg;
    }

    @Override
    @Cacheable(cacheNames = "Club")
    public List<Club> selectClub(float longitude, float latitude) {
        return null;
    }
}
