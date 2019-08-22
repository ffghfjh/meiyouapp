package com.meiyou.service.impl;

import com.meiyou.mapper.AppointAskMapper;
import com.meiyou.mapper.AppointmentMapper;
import com.meiyou.mapper.RootMessageMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.pojo.*;
import com.meiyou.service.AppointmentService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @program: meiyouapp
 * @description: 约会接口实现类
 * @author: JK
 * @create: 2019-08-21 14:12
 **/
@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private AppointAskMapper appointAskMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RootMessageMapper rootMessageMapper;
    /**
    * @Description: 发布约会
    * @Author: JK
    * @Date: 2019/8/21
    */
    @Transactional
    @Override
    public Msg insert(Appointment appointment, String password, String token) {
        boolean authToken = RedisUtil.authToken(appointment.getPublisherId().toString(), token);
        Msg msg = new Msg();
        //判断是否登录
        if (!authToken){
            return Msg.noLogin();
        }

        //根据发布者id查询出他所有信息
        User user = userMapper.selectByPrimaryKey(appointment.getPublisherId());
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
        float balance = money-(publishMoneyValue1 + appointment.getReward());
        //选择线下付款扣款后剩余余额
        float balance1 = money-(publishMoneyValue1 + sincerityMoneyValue1 + appointment.getReward());

        //如果选择线下付款
        if (appointment.getPayType() == 1){
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
        userExample.createCriteria().andIdEqualTo(appointment.getPublisherId());
        //更新用户账户余额
        userMapper.updateByExample(user,userExample);
        int i = appointmentMapper.insertSelective(appointment);
        if (i == 1){
            return Msg.success();
        }else {
            return Msg.fail();
        }
    }

    /**
    * @Description: 查询我的约会发布列表
    * @Author: JK
    * @Date: 2019/8/22
    */
    @Override
    public List<Appointment> selectAppointmentList() {
        AppointmentExample example = new AppointmentExample();
        List<Appointment> appointments = appointmentMapper.selectByExample(example);
        return appointments;
    }

    /**
    * @Description: 取消发布约会订单
    * @Author: JK
    * @Date: 2019/8/22
    */
    @Transactional
    @Override
    public Msg deletePublish(Integer id,String token) {
        Appointment appointment = appointmentMapper.selectByPrimaryKey(id);
        boolean authToken = RedisUtil.authToken(appointment.getPublisherId().toString(), token);
        Msg msg = new Msg();
        //判断是否登录
        if (!authToken){
            return Msg.noLogin();
        }
        //获取当前订单状态
        Integer state = appointment.getState();
        int i = 0;
        if (state == 0) {
            AppointmentExample example = new AppointmentExample();
            example.createCriteria().andIdEqualTo(id);
            i = appointmentMapper.deleteByExample(example);
            if (i == 1){
                return Msg.success();
            }
            return Msg.fail();
        }
        msg.setCode(1005);
        msg.setMsg("不能取消订单");
        return msg;
    }
    
    /** 
    * @Description: 从多个约会订单中选择一个进行报名
    * @Author: JK 
    * @Date: 2019/8/22 
    */
    @Transactional
    @Override
    public Msg startEnrollment(String uid,String password,Integer id,String token) {
        boolean authToken = RedisUtil.authToken(uid, token);
        Msg msg = new Msg();
        //判断是否登录
        if (!authToken){
            return Msg.noLogin();
        }
        //根据报名者id查询出他所有信息
        User user = userMapper.selectByPrimaryKey(Integer.parseInt(uid));
        //获取报名者账户余额
        Float money = user.getMoney();

        RootMessageExample rootMessageExample = new RootMessageExample();
        rootMessageExample.createCriteria().andNameEqualTo("ask_money");
        //查询系统动态数据表中所有的数据
        List<RootMessage> list = rootMessageMapper.selectByExample(rootMessageExample);
        //获取报名金的名称
        RootMessage askMoney = list.get(0);

        //获取报名金的金额
        String askMoneyValue = askMoney.getValue();
        //将发布金从String转换成Integer
        Integer askMoneyValue1=Integer.parseInt(askMoneyValue);

        //判断用户输入密码是否正确
        if (!password.equals(user.getPayWord())){
            msg.setCode(1001);
            msg.setMsg("支付密码错误");
            return msg;
        }
        //报名扣款后剩余余额
        float balance = money-askMoneyValue1;

        if (balance <0){
            msg.setCode(1002);
            msg.setMsg("余额不足");
            return msg;
        }
        user.setMoney(balance);

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(Integer.parseInt(uid));
        //更新报名者账户余额
        userMapper.updateByExample(user,userExample);

        AppointAsk appointAsk = new AppointAsk();
        appointAsk.setAskerId(Integer.parseInt(uid));
        appointAsk.setAppointId(id);
        appointAsk.setAskState(1);
        appointAsk.setCreateTime(new Date());
        appointAsk.setUpdateTime(new Date());
        //约会记录表中增加一条记录
        appointAskMapper.insertSelective(appointAsk);
        //根据约会订单表id查出该订单所有信息
        Appointment appointment = appointmentMapper.selectByPrimaryKey(id);
        AppointmentExample example = new AppointmentExample();
        example.createCriteria().andIdEqualTo(id);
        appointment.setState(2);
        appointment.setUpdateTime(new Date());
        //更改该订单状态
        int i = appointmentMapper.updateByExample(appointment, example);
        if (i == 1){
            return Msg.success();
        }else {
            return Msg.fail();
        }
    }

    /**
    * @Description: 查询所有报名某个约会的人员信息
    * @Author: JK
    * @Date: 2019/8/22
    */
    @Override
    public List<AppointAsk> selectAppointAskList(Integer appointId) {
        AppointAskExample example = new AppointAskExample();
        example.createCriteria().andAppointIdEqualTo(appointId);
        List<AppointAsk> appointAsks = appointAskMapper.selectByExample(example);
        return appointAsks;
    }

    /**
    * @Description: 从所有报名某个约会的人员信息中选择一个进行确认
    * @Author: JK
    * @Date: 2019/8/22
    */
    @Override
    public int confirmUserId(Integer askerId, Integer appointId) {
        Appointment appointment = appointmentMapper.selectByPrimaryKey(appointId);
        AppointmentExample example = new AppointmentExample();
        example.createCriteria().andIdEqualTo(appointId);
        appointment.setConfirmId(askerId);
        appointment.setState(2);
        int i = appointmentMapper.updateByExample(appointment, example);
        return i;
    }
}
