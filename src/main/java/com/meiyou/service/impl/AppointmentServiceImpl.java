package com.meiyou.service.impl;

import com.meiyou.mapper.AppointAskMapper;
import com.meiyou.mapper.AppointmentMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.model.AskerVO;
import com.meiyou.model.Coordinate;
import com.meiyou.pojo.*;
import com.meiyou.service.AppointmentService;
import com.meiyou.service.RootMessageService;
import com.meiyou.utils.Constants;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import com.meiyou.utils.RootMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.GeoRadiusResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @program: meiyouapp
 * @description: 约会接口实现类
 * @author: JK
 * @create: 2019-08-21 14:12
 **/
@Service
public class AppointmentServiceImpl extends BaseServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private AppointAskMapper appointAskMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RootMessageUtil rootMessageUtil;
    @Autowired
    private RootMessageService rootMessageService;

    /**
     * @Description: 发布约会
     * @Author: JK
     * @Date: 2019/8/21
     */
    @Transactional
    @Override
    public Msg insert(Appointment appointment, String password, String token, double latitude, double longitude) {
        Msg msg = new Msg();
        boolean authToken = RedisUtil.authToken(appointment.getPublisherId().toString(), token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }

        //根据发布者id查询出他所有信息
        User user = userMapper.selectByPrimaryKey(appointment.getPublisherId());
        //获取发布者账户余额
        Float money = user.getMoney();
        //获取发布金
        String publishMoneyName = "publish_money";
        int publishMoneyValue = rootMessageUtil.getRootMessage(publishMoneyName);

        if (password == null){
            msg.setCode(1000);
            msg.setMsg("请输入密码");
            return msg;
        }

        //判断用户输入密码是否正确
        if (!password.equals(user.getPayWord())) {
            msg.setCode(1001);
            msg.setMsg("支付密码错误");
            return msg;
        }

        //获取诚意金
        String sincerityMoneyName = "sincerity_money";
        int sincerityMoneyValue = rootMessageUtil.getRootMessage(sincerityMoneyName);

        //选择平台担保扣款后剩余余额
        float balance = money - (publishMoneyValue + appointment.getReward());
        //选择线下付款扣款后剩余余额
        float balance1 = money - (publishMoneyValue + sincerityMoneyValue + appointment.getReward());

        //如果选择线下付款
        if (appointment.getPayType() == 1) {
            if (balance1 < 0) {
                msg.setCode(1002);
                msg.setMsg("余额不足");
                return msg;
            }
            user.setMoney(balance1);
        } else {
            if (balance < 0) {
                msg.setCode(1002);
                msg.setMsg("余额不足");
                return msg;
            }
            user.setMoney(balance);
        }

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(appointment.getPublisherId());
        //更新用户账户余额
        userMapper.updateByExample(user, userExample);

        int i = appointmentMapper.insertSelective(appointment);
        if (i == 1) {
            //获取发布时定位
            Coordinate coordinate = new Coordinate();
            coordinate.setLatitude(latitude);
            coordinate.setLongitude(longitude);
            coordinate.setKey(appointment.getId().toString());
            Long aLong = RedisUtil.addReo(coordinate, Constants.GEO_APPOINTMENT);
            if (aLong == 1){
                return Msg.success();
            }
        }
        return Msg.fail();
    }

    /**
     * @Description: 取消发布约会订单
     * @Author: JK
     * @Date: 2019/8/22
     */
    @Transactional
    @Override
    public Msg deleteAppointmentPublish(Integer id, String token) {
        Appointment appointment = appointmentMapper.selectByPrimaryKey(id);
        boolean authToken = RedisUtil.authToken(appointment.getPublisherId().toString(), token);
        Msg msg = new Msg();
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        //获取当前订单状态
        Integer state = appointment.getState();
        int i = 0;
        if (state == 1) {
            AppointmentExample example = new AppointmentExample();
            example.createCriteria().andIdEqualTo(id)
                        .andPublisherIdEqualTo(appointment.getPublisherId());
            appointment.setState(0);
            appointment.setUpdateTime(new Date());
            i = appointmentMapper.updateByExample(appointment, example);
            if (i == 1) {
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
    public Msg appointmentAsk(String uid, String password, Integer id, String token) {
        Msg msg = new Msg();
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        //根据报名者id查询出他所有信息
        User user = userMapper.selectByPrimaryKey(Integer.parseInt(uid));
        //获取报名者账户余额
        Float money = user.getMoney();

        //获取报名金的金额
        String askMoneyName = "ask_money";
        int askMoneyValue = rootMessageUtil.getRootMessage(askMoneyName);

        if (password == null){
            msg.setCode(1000);
            msg.setMsg("请输入密码");
            return msg;
        }
        //判断用户输入密码是否正确
        if (!password.equals(user.getPayWord())) {
            msg.setCode(1001);
            msg.setMsg("支付密码错误");
            return msg;
        }
        //报名扣款后剩余余额
        float balance = money - askMoneyValue;

        if (balance < 0) {
            msg.setCode(1002);
            msg.setMsg("余额不足");
            return msg;
        }
        user.setMoney(balance);

        //查询该报名者是否已经报名
        AppointAskExample appointAskExample = new AppointAskExample();
        appointAskExample.createCriteria().andAskerIdEqualTo(Integer.parseInt(uid))
                .andAskStateEqualTo(1).andAppointIdEqualTo(id);
        List<AppointAsk> appointAsks = appointAskMapper.selectByExample(appointAskExample);
        if (appointAsks.size() > 0){
            msg.setCode(250);
            msg.setMsg("请勿重复报名");
            return msg;
        }

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(Integer.parseInt(uid));
        //更新报名者账户余额
        userMapper.updateByExample(user, userExample);

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
        if (i == 1) {
            return Msg.success();
        } else {
            return Msg.fail();
        }
    }

    /**
     * @Description: 取消报名, 退还美金
     * @Author: JK
     * @Date: 2019/8/23
     */
    @Transactional
    @Override
    public Msg endAppointmentAsk(String uid, Integer id, String token) {
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        AppointAskExample appointAskExample = new AppointAskExample();
        appointAskExample.createCriteria().andAppointIdEqualTo(id)
                .andAskerIdEqualTo(Integer.parseInt(uid))
                .andAskStateEqualTo(1);
        AppointAsk appointAsk = new AppointAsk();
        appointAsk.setAskState(0);
        appointAsk.setUpdateTime(new Date());


        //将报名人员从报名态改为未报名态
        int i = appointAskMapper.updateByExampleSelective(appointAsk, appointAskExample);

        AppointAskExample appointAskExample1 = new AppointAskExample();
        appointAskExample1.createCriteria().andAppointIdEqualTo(id)
                .andAskStateEqualTo(1)
                .andAskerIdEqualTo(Integer.parseInt(uid));
        //获取当前约会订单报名人数
        List<AppointAsk> appointAsks = appointAskMapper.selectByExample(appointAskExample1);
        //当前约会订单报名人数为空并且为0，则修改该约会订单状态为1
        if (appointAsks == null || appointAsks.size() == 0) {
            AppointmentExample appointmentExample = new AppointmentExample();
            appointmentExample.createCriteria().andIdEqualTo(id);
            Appointment appointment = new Appointment();
            appointment.setState(1);
            appointment.setUpdateTime(new Date());
            appointmentMapper.updateByExampleSelective(appointment, appointmentExample);
        }


        //根据报名者id查询出他所有信息
        User user = userMapper.selectByPrimaryKey(Integer.parseInt(uid));
        //获取发布者账户余额
        Float money = user.getMoney();
        //获取报名金的金额
        String askMoneyName = "ask_money";
        int askMoneyValue = rootMessageUtil.getRootMessage(askMoneyName);
        //金额退还后账户余额
        float balance = money + askMoneyValue;

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(Integer.parseInt(uid));
        User user1 = new User();
        user1.setMoney(balance);
        int i1 = userMapper.updateByExampleSelective(user1, userExample);

        int i2 = i + i1;
        if (i2 == 2) {
            return Msg.success();
        }
        return Msg.fail();
    }

    /**
     * @Description: 查询所有报名某个约会的人员信息
     * @Author: JK
     * @Date: 2019/8/22
     */
    @Override
    public Msg selectAppointAskList(String uid, Integer appointId, String token) {
        Msg msg = new Msg();
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        AppointAskExample example = new AppointAskExample();
        example.createCriteria().andAppointIdEqualTo(appointId);
        List<AppointAsk> appointAsks = appointAskMapper.selectByExample(example);

        if (appointAsks != null && appointAsks.size() != 0) {
            msg.add("appointAsks", appointAsks);
            msg.setCode(100);
            msg.setMsg("约会报名对象返回成功");
            return msg;
        }
        Msg fail = Msg.fail();
        msg.add("fail", fail);
        return msg;
    }

    /**
     * @Description: 从所有报名某个约会的人员信息中选择一个进行确认,
     * 没有被选中的人退还报名金
     * @Author: JK
     * @Date: 2019/8/22
     */
    @Transactional
    @Override
    public Msg confirmAppointmentUserId(String uid, Integer askerId, Integer appointId, String token) {
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        Appointment appointment = appointmentMapper.selectByPrimaryKey(appointId);
        AppointmentExample example = new AppointmentExample();
        example.createCriteria().andIdEqualTo(appointId)
                    .andPublisherIdEqualTo(Integer.parseInt(uid));
        appointment.setConfirmId(askerId);
        //在约会表中3是选中人员等待赴约状态
        appointment.setState(3);
        appointment.setUpdateTime(new Date());
        int i = appointmentMapper.updateByExample(appointment, example);


        AppointAsk appointAsk = new AppointAsk();
        AppointAskExample appointAskExample = new AppointAskExample();
        appointAskExample.createCriteria().andAskerIdEqualTo(askerId).andAppointIdEqualTo(appointId)
                .andAskStateEqualTo(1);

        //约会报名表中2是被选中状态
        appointAsk.setAskState(2);
        appointAsk.setUpdateTime(new Date());
        int i1 = appointAskMapper.updateByExampleSelective(appointAsk, appointAskExample);

        AppointAskExample appointAskExample1 = new AppointAskExample();
        appointAskExample1.createCriteria().andAskStateEqualTo(1)
                .andAskerIdNotEqualTo(askerId);

        List<AppointAsk> appointAsks = appointAskMapper.selectByExample(appointAskExample1);
        int i2 = 0;
        int i3 = 0;
        if (appointAsks != null && appointAsks.size() != 0) {
            for (AppointAsk ask : appointAsks) {
                AppointAsk appointAsk1 = new AppointAsk();
                //获取申请人ID
                Integer askerId1 = ask.getAskerId();
                //根据报名者id查询出他所有信息
                User user = userMapper.selectByPrimaryKey(askerId1);
                //获取发布者账户余额
                Float money = user.getMoney();
                //获取报名金的金额
                String askMoneyName = "ask_money";
                int askMoneyValue = rootMessageUtil.getRootMessage(askMoneyName);
                //金额退还后账户余额
                float balance = money + askMoneyValue;

                //没有被选中状态改为3
                appointAsk1.setAskState(3);
                appointAsk1.setUpdateTime(new Date());
                i2 = appointAskMapper.updateByExampleSelective(appointAsk1, appointAskExample1);
                if (i2 != 1){
                    return Msg.fail();
                }

                User user1 = new User();
                UserExample userExample = new UserExample();
                userExample.createCriteria().andIdEqualTo(askerId1);
                user1.setMoney(balance);
                user1.setUpdateTime(new Date());
                i3 = userMapper.updateByExampleSelective(user1, userExample);
                if (i3 != 1){
                    return Msg.fail();
                }
            }
        }

        int i4 = i + i1 + i2 + i3;
        if (i4 == 4) {
            return Msg.success();
        }
        return Msg.fail();
    }

    /**
     * @Description: 对方取消赴约，重新发布，不退还报名金
     * @Author: JK
     * @Date: 2019/8/23
     */
    @Transactional
    @Override
    public Msg endAppointment(String uid, Integer id, String token) {
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        AppointAskExample appointAskExample = new AppointAskExample();
        appointAskExample.createCriteria().andAppointIdEqualTo(id)
                .andAskerIdEqualTo(Integer.parseInt(uid))
                .andAskStateEqualTo(2);
        AppointAsk appointAsk = new AppointAsk();
        appointAsk.setAskState(5);
        appointAsk.setUpdateTime(new Date());


        //将报名人员从被选中状态改为取消赴约状态
        int i = appointAskMapper.updateByExampleSelective(appointAsk, appointAskExample);

        AppointmentExample appointmentExample = new AppointmentExample();
        appointmentExample.createCriteria().andIdEqualTo(id).andStateEqualTo(3);
        Appointment appointment = new Appointment();
        appointment.setState(1);
        appointment.setConfirmId(0);
        appointment.setUpdateTime(new Date());
        int i1 = appointmentMapper.updateByExampleSelective(appointment, appointmentExample);

        int i2 = i + i1;
        if (i2 == 2) {
            return Msg.success();
        }
        return Msg.fail();
    }

    /**
     * @Description: 由于发布者自己原因重新发布，退还报名金
     * @Author: JK
     * @Date: 2019/8/23
     */
    @Transactional
    @Override
    public Msg againReleaseAppointment(String uid, Integer id, String token) {
        Appointment appointment = appointmentMapper.selectByPrimaryKey(id);
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        //获取当前订单状态
        Integer state = appointment.getState();
        //如果是有人报名等待选中状态，则退还所有报名者的报名金
        int i1 = 0;
        int i2 = 0;
        System.out.println(state);
        if (state == 2) {
            AppointAskExample appointAskExample = new AppointAskExample();
            appointAskExample.createCriteria().andAskStateEqualTo(1)
                    .andAppointIdEqualTo(id);

            List<AppointAsk> appointAsks = appointAskMapper.selectByExample(appointAskExample);
            for (AppointAsk appointAsk : appointAsks) {
                AppointAsk appointAsk1 = new AppointAsk();
                //获取申请人ID
                Integer askerId = appointAsk.getAskerId();
                //根据报名者id查询出他所有信息
                User user = userMapper.selectByPrimaryKey(askerId);
                //获取发布者账户余额
                Float money = user.getMoney();
                //获取报名金的金额
                String askMoneyName = "ask_money";
                int askMoneyValue = rootMessageUtil.getRootMessage(askMoneyName);
                //金额退还后账户余额
                float balance = money + askMoneyValue;
                User user1 = new User();
                UserExample userExample = new UserExample();
                userExample.createCriteria().andIdEqualTo(askerId);
                user1.setMoney(balance);
                user1.setUpdateTime(new Date());
                i1 = userMapper.updateByExampleSelective(user1, userExample);
                if (i1 != 1){
                    return Msg.fail();
                }

                AppointAskExample appointAskExample1 = new AppointAskExample();
                appointAskExample1.createCriteria().andAskStateEqualTo(1)
                        .andAppointIdEqualTo(id).andAskerIdEqualTo(askerId);
                //退还报名金后，报名者状态从1变成8
                appointAsk1.setAskState(8);
                appointAsk1.setUpdateTime(new Date());
                i2 = appointAskMapper.updateByExampleSelective(appointAsk1, appointAskExample1);

                if (i2 != 1){
                    return Msg.fail();
                }
            }
            AppointmentExample appointmentExample = new AppointmentExample();
            appointmentExample.createCriteria().andIdEqualTo(id).andStateEqualTo(2);
            appointment.setState(1);
            appointment.setUpdateTime(new Date());
            int i3 = appointmentMapper.updateByExampleSelective(appointment, appointmentExample);
            int i = i1 + i2 + i3;
            if (i == 3) {
                return Msg.success();
            }
        }

        if (state == 3) {
            Integer confirmId = appointment.getConfirmId();
            //根据报名者id查询出他所有信息
            User user = userMapper.selectByPrimaryKey(confirmId);
            //获取发布者账户余额
            Float money = user.getMoney();
            //获取报名金的金额
            String askMoneyName = "ask_money";
            int askMoneyValue = rootMessageUtil.getRootMessage(askMoneyName);
            //金额退还后账户余额
            float balance = money + askMoneyValue;

            User user1 = new User();
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(confirmId);
            user1.setMoney(balance);
            user1.setUpdateTime(new Date());
            //修改金额
            i1 = userMapper.updateByExampleSelective(user1, userExample);

            if (i1 != 1){
                return Msg.fail();
            }

            AppointAskExample appointAskExample = new AppointAskExample();
            appointAskExample.createCriteria().andAskStateEqualTo(2)
                    .andAppointIdEqualTo(id);
            AppointAsk appointAsk = new AppointAsk();
            //退还报名金后，报名者状态从2变成8
            appointAsk.setAskState(8);
            appointAsk.setUpdateTime(new Date());
            i2 = appointAskMapper.updateByExampleSelective(appointAsk, appointAskExample);

            AppointmentExample appointmentExample = new AppointmentExample();
            appointmentExample.createCriteria().andIdEqualTo(id).andStateEqualTo(3);
            appointment.setState(1);
            appointment.setUpdateTime(new Date());
            int i3 = appointmentMapper.updateByExampleSelective(appointment, appointmentExample);

            int i = i1 + i2 + i3;
            if (i == 3) {
                return Msg.success();
            }
        }

        if (state == 4) {
            Integer confirmId = appointment.getConfirmId();
            //根据报名者id查询出他所有信息
            User user = userMapper.selectByPrimaryKey(confirmId);
            //获取发布者账户余额
            Float money = user.getMoney();
            //获取报名金的金额
            String askMoneyName = "ask_money";
            int askMoneyValue = rootMessageUtil.getRootMessage(askMoneyName);
            //金额退还后账户余额
            float balance = money + askMoneyValue;

            User user1 = new User();
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(confirmId);
            user1.setMoney(balance);
            user1.setUpdateTime(new Date());
            //修改金额
            i1 = userMapper.updateByExampleSelective(user1, userExample);


            AppointAskExample appointAskExample = new AppointAskExample();
            appointAskExample.createCriteria().andAskStateEqualTo(6)
                    .andAppointIdEqualTo(id);
            AppointAsk appointAsk = new AppointAsk();
            //退还报名金后，报名者状态从2变成8
            appointAsk.setAskState(8);
            appointAsk.setUpdateTime(new Date());
            i2 = appointAskMapper.updateByExampleSelective(appointAsk, appointAskExample);

            AppointmentExample appointmentExample = new AppointmentExample();
            appointmentExample.createCriteria().andIdEqualTo(id).andStateEqualTo(4);
            appointment.setState(1);
            appointment.setUpdateTime(new Date());
            int i3 = appointmentMapper.updateByExampleSelective(appointment, appointmentExample);

            int i = i1 + i2 + i3;
            if (i == 3) {
                return Msg.success();
            }
        }
            return Msg.fail();
    }

    /**
     * @Description: 报名人确认赴约
     * @Author: JK
     * @Date: 2019/8/23
     */
    @Transactional
    @Override
    public Msg confirmAppointment(String uid, Integer id, String token) {
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        AppointAskExample appointAskExample = new AppointAskExample();
        appointAskExample.createCriteria().andAskStateEqualTo(3)
                .andAppointIdEqualTo(id);
        AppointAsk appointAsk = new AppointAsk();
        appointAsk.setAskState(6);
        appointAsk.setUpdateTime(new Date());
        //更改报名者状态为6，确认赴约
        int i = appointAskMapper.updateByExampleSelective(appointAsk, appointAskExample);

        AppointmentExample appointmentExample = new AppointmentExample();
        appointmentExample.createCriteria().andIdEqualTo(id).andStateEqualTo(3);
        Appointment appointment = new Appointment();
        appointment.setState(4);
        appointment.setUpdateTime(new Date());
        //更改发布者状态为4，报名者确定赴约
        int i1 = appointmentMapper.updateByExampleSelective(appointment, appointmentExample);

        int i2 = i + i1;
        if (i2 == 2){
            return Msg.success();
        }
        return Msg.fail();
    }

    /**
     * @Description: 确认报名人已到达
     * @Author: JK
     * @Date: 2019/8/23
     */
    @Transactional
    @Override
    public Msg confirmAppointmentArrive(String uid, Integer id, String token) {
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        AppointmentExample appointmentExample = new AppointmentExample();
        appointmentExample.createCriteria().andIdEqualTo(id)
                .andStateEqualTo(4)
                .andPublisherIdEqualTo(Integer.parseInt(uid));
        Appointment appointment = new Appointment();
        appointment.setState(5);
        appointment.setUpdateTime(new Date());
        //更改发布者状态为5，报名者已到达，订单完成
        int i = appointmentMapper.updateByExampleSelective(appointment, appointmentExample);

        AppointAskExample appointAskExample = new AppointAskExample();
        appointAskExample.createCriteria().andAskStateEqualTo(6)
                .andAppointIdEqualTo(id);
        AppointAsk appointAsk = new AppointAsk();
        if (i == 1){
            appointAsk.setAskState(7);
        }
        appointAsk.setAskState(6);
        //更改报名者状态为7，报名者已到达，订单完成
        int i1 = appointAskMapper.updateByExampleSelective(appointAsk, appointAskExample);
        int i2 = i + i1;
        if (i2 == 2){
            return Msg.success();
        }
        return Msg.fail();
    }

    /**
    * @Description: 查看热门约会
    * @Author: JK
    * @Date: 2019/8/24
    */
    @Override
    public Msg selectHotAppointment(String uid, String token,double latitude, double longitude) {
        Msg msg = new Msg();
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }

        //范围半径
        String range = rootMessageService.getMessageByName("range");
        double radius = Double.parseDouble(range);
        //从Redis获取附近的所有用户的动态
        Coordinate coordinate = new Coordinate();
        coordinate.setKey(uid);
        coordinate.setLongitude(longitude);
        coordinate.setLatitude(latitude);
        List<GeoRadiusResponse> responseList = RedisUtil.geoQueryAppointment(coordinate,radius);
        //判断附近是否有热门约会
        if (responseList == null || responseList.size() == 0) {
            return Msg.fail();
        }
        ArrayList<Object> list = new ArrayList<>();
        for (GeoRadiusResponse response : responseList) {
            //获取缓存中的key
            String memberByString = response.getMemberByString();
            if (memberByString == null){
                return Msg.fail();
            }

            Appointment appointment = appointmentMapper.selectByPrimaryKey(Integer.parseInt(memberByString));
            Integer state = appointment.getState();
            //获取用户id
            Integer publisherId = appointment.getPublisherId();

            //如果发布者等于报名者，则跳出本次循环
            if (publisherId == Integer.parseInt(uid)){
                continue;
            }
            User user = userMapper.selectByPrimaryKey(publisherId);
            HashMap<String, Object> map = new HashMap<>();
            if (state == 1 || state == 2){
                String nickname = user.getNickname();
                String header = user.getHeader();
                String birthday = user.getBirthday();
                Boolean sex = user.getSex();
                String appointContext = appointment.getAppointContext();
                String appointTime = appointment.getAppointTime();
                String appointAddress = appointment.getAppointAddress();
                String appointImgs = appointment.getAppointImgs();
                Integer needNumber = appointment.getNeedNumber();
                Integer reward = appointment.getReward();
                Integer payType = appointment.getPayType();
                Integer confirmId = appointment.getConfirmId();
                Integer state1 = appointment.getState();
                Integer id = appointment.getId();
                if (appointment.getPayType() == 1){
                    //获取诚意金
                    String sincerityMoneyName = "sincerity_money";
                    int sincerityMoneyValue = rootMessageUtil.getRootMessage(sincerityMoneyName);
                    map.put("sincerityMoneyValue",sincerityMoneyValue);
                }
                map.put("nickname",nickname);
                map.put("header",header);
                map.put("birthday",birthday);
                map.put("sex",sex);
                map.put("appointContext",appointContext);
                map.put("appointTime",appointTime);
                map.put("appointAddress",appointAddress);
                map.put("appointImgs",appointImgs);
                map.put("needNumber",needNumber);
                map.put("reward",reward);
                map.put("payType",payType);
                map.put("confirmId",confirmId);
                map.put("state1",state1);
                map.put("id",id);
                list.add(map);

            }

        }
        msg.setCode(100);
        msg.setMsg("获取附近热门约会成功");
        return msg.add("list",list);
    }

    /**
    * @Description: 查询报名约会的全部人员
    * @Author: JK
    * @Date: 2019/8/29
    */
    @Override
    public Msg selectAllAppointmentById(Integer uid, String token, Integer id) {
        if (!RedisUtil.authToken(uid.toString(), token)) {
            return Msg.noLogin();
        }

        Msg msg = new Msg();
        //判断访问者是否为发布者
        Integer publishId = appointmentMapper.selectByPrimaryKey(id).getPublisherId();
        if (publishId != uid) {
            msg.setCode(506);
            msg.setMsg("没有访问权限");
            return msg;
        }

        //查找报名约会的记录
        AppointAskExample appointAskExample = new AppointAskExample();
        appointAskExample.createCriteria().andAppointIdEqualTo(id).andAskStateEqualTo(1);

        List<AppointAsk> appointAsks = appointAskMapper.selectByExample(appointAskExample);

        if (appointAsks == null && appointAsks.size() == 0) {
            msg.setCode(404);
            msg.setMsg("找不到指定的约会记录");
            return msg;
        }

        //对查找出来的ClubBuy进行封装
        List<AskerVO> askerVOS = new ArrayList<>();
        for (AppointAsk c : appointAsks) {
            User buyer = getUserByUid(c.getAskerId());

            AskerVO askerVO = new AskerVO();
            askerVO.setId(buyer.getId());
            askerVO.setNickname(buyer.getNickname());
            askerVO.setHeader(buyer.getHeader());
            askerVO.setBirthday(buyer.getBirthday());
            askerVO.setSex(buyer.getSex());
            askerVO.setSignature(buyer.getSignature());
            askerVO.setAskState(c.getAskState());

            askerVOS.add(askerVO);
        }

        //返回一个封装好的askerVO类
        msg.add("askerVOS", askerVOS);
        msg.setMsg("成功");
        msg.setCode(100);
        return msg;
    }
}