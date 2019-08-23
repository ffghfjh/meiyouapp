package com.meiyou.service.impl;

import com.meiyou.mapper.ClubBuyMapper;
import com.meiyou.mapper.ClubMapper;
import com.meiyou.mapper.RootMessageMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.pojo.*;
import com.meiyou.service.ClubBuyService;
import com.meiyou.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @description: 会所购买业务层接口实现类
 * @author: Mr.Z
 * @create: 2019-08-21 15:38
 **/
@Service
public class ClubBuyServiceImpl extends BaseServiceImpl implements ClubBuyService {

    @Autowired
    ClubBuyMapper clubBuyMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ClubMapper clubMapper;

    /**
     * 生成购买会所记录
     * @param clubBuy
     * @return
     */
    @Transactional
    @Override
    public Msg addBuyClub(ClubBuy clubBuy,String token,Integer password) {
//        if(!RedisUtil.authToken(clubBuy.getBuyerId().toString(),token)){
//            return Msg.noLogin();
//        }
        Msg msg = new Msg();
        clubBuy.setCreateTime(new Date());
        clubBuy.setUpdateTime(new Date());

        //从系统数据表获取置顶费用
        String ask_money = getRootMessage("ask_money");

        //获取购买者的支付密码,余额及需要支付的费用(报名缴费+会所项目缴费)
        User result = getUserByUid(clubBuy.getBuyerId());
        String payWord = result.getPayWord();
        Float money = result.getMoney();
        Integer price = clubMapper.selectByPrimaryKey(clubBuy.getClubId()).getProjectPrice()
                +Integer.valueOf(ask_money);
        System.out.println(payWord);

        if(payWord.equals("")){
            msg.setMsg("请设置支付密码!");
            msg.setCode(1000);
            return msg;
        }else if(!payWord.equals(password)){
            msg.setMsg("支付密码错误!");
            msg.setCode(1001);
            return msg;
        }else if(money < price){
            msg.setMsg("发布失败,账户余额不足!");
            msg.setCode(1002);
            return msg;
        }else {
            clubBuyMapper.insert(clubBuy);

            //执行扣钱操作
            User user = new User();
            money = money - price;
            user.setMoney(money);
            user.setId(clubBuy.getBuyerId());
            user.setUpdateTime(new Date());
            userMapper.updateByPrimaryKeySelective(user);
            return Msg.success();
        }
    }

    /**
     * 取消购买(退款全部)
     * @param uid
     * @param cid
     * @param token
     * @return
     */
    @Transactional
    @Override
    public Msg updateBuyClub(Integer uid,Integer cid,String token) {
//        if(!RedisUtil.authToken(uid.toString(),token)){
//            return Msg.noLogin();
//        }

        //从系统数据表获取置顶费用
        String ask_money = getRootMessage("ask_money");

        //获取项目金额
        Integer projectPrice = clubMapper.selectByPrimaryKey(cid).getProjectPrice();

        //修改购买表状态
        ClubBuyExample clubBuyExample = new ClubBuyExample();
        clubBuyExample.createCriteria().andBuyerIdEqualTo(uid).andClubIdEqualTo(cid);
        ClubBuy clubBuy = new ClubBuy();
        clubBuy.setState(1);
        clubBuyMapper.updateByExampleSelective(clubBuy,clubBuyExample);

        //退钱操作
        Float money = Float.valueOf(ask_money)+Float.valueOf(projectPrice);
        User user = getUserByUid(uid);
        user.setMoney(user.getMoney()+money);

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(uid);
        userMapper.updateByExampleSelective(user,userExample);

        return Msg.success();
    }

    @Override
    public Msg selectByUid(Integer uid) {
        Msg msg = new Msg();
        //查找购买按摩会所的记录
        ClubBuyExample clubBuyExample = new ClubBuyExample();
        //购买者id为uid的购买记录
        clubBuyExample.createCriteria().andBuyerIdEqualTo(uid);

        List<ClubBuy> result = clubBuyMapper.selectByExample(clubBuyExample);
        if(result.size() == 0){
            msg.setMsg("没有找到对应的ClubBuy记录");
            msg.setCode(404);
            return msg;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("clubBuy",result);

        msg.setExtend(map);
        msg.setCode(100);
        msg.setMsg("成功");
        return msg;
    }


}
