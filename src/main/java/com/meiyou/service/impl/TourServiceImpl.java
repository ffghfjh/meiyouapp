package com.meiyou.service.impl;

import com.meiyou.mapper.RootMessageMapper;
import com.meiyou.mapper.TourMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.pojo.*;
import com.meiyou.service.TourService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: meiyou
 * @description: 同城旅游实现类
 * @author: JK
 * @create: 2019-08-22 19:40
 **/
@Service
public class TourServiceImpl implements TourService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RootMessageMapper rootMessageMapper;

    @Autowired
    private TourMapper tourMapper;

    /**
    * @Description: 发布旅游
    * @Author: JK
    * @Date: 2019/8/22
    */
    @Transactional
    @Override
    public Msg insert(Tour tour, String password, String token) {
        boolean authToken = RedisUtil.authToken(tour.getPublishId().toString(), token);
        Msg msg = new Msg();
        //判断是否登录
        if (!authToken){
            return Msg.noLogin();
        }

        //根据发布者id查询出他所有信息
        User user = userMapper.selectByPrimaryKey(tour.getPublishId());
        //获取发布者账户余额
        Float money = user.getMoney();
        RootMessageExample rootMessageExample = new RootMessageExample();
        rootMessageExample.createCriteria().andNameEqualTo("publish_money");
        //查询系统动态数据表中所有的数据
        List<RootMessage> list = rootMessageMapper.selectByExample(rootMessageExample);
        //获取发布金的名称
        RootMessage publishMoney = list.get(0);

        //获取发布金的金额
        String publishMoneyValue = publishMoney.getValue();
        //将发布金从String转换成Integer
        Integer publishMoneyValue1=Integer.parseInt(publishMoneyValue);

        //判断用户输入密码是否正确
        if (!password.equals(user.getPayWord())){
            System.out.println(user.getPayWord());
            msg.setCode(1001);
            msg.setMsg("支付密码错误");
            return msg;
        }

        //设置查询条件，设置系统动态数据表中 name = sincerity_money
        rootMessageExample.createCriteria().andNameEqualTo("sincerity_money");
        List<RootMessage> list1 = rootMessageMapper.selectByExample(rootMessageExample);
        //获取诚意金的名称
        RootMessage sincerityMoney = list1.get(0);
        //获取诚意金的金额
        String sincerityMoneyValue = sincerityMoney.getValue();
        //将诚意金从String转换成Integer
        Integer sincerityMoneyValue1=Integer.parseInt(sincerityMoneyValue);

        //选择平台担保扣款后剩余余额
        float balance = money-(publishMoneyValue1 + tour.getReward());
        //选择线下付款扣款后剩余余额
        float balance1 = money-(publishMoneyValue1 + sincerityMoneyValue1 + tour.getReward());

        //如果选择线下付款
        if (tour.getPayType() == 1){
            if ( balance1 < 0){
                msg.setCode(1002);
                msg.setMsg("余额不足");
                return msg;
            }
            user.setMoney(balance1);
        }else {
            if ( balance < 0){
                msg.setCode(1002);
                msg.setMsg("余额不足");
                return msg;
            }
            user.setMoney(balance);
        }

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(tour.getPublishId());
        //更新用户账户余额
        userMapper.updateByExample(user,userExample);
        int i = tourMapper.insertSelective(tour);
        if (i == 1){
            return Msg.success();
        }else {
            return Msg.fail();
        }
    }
}
