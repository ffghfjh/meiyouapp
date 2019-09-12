package com.meiyou.service.impl;

import com.meiyou.mapper.TourAskMapper;
import com.meiyou.mapper.TourMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.model.AskerVO;
import com.meiyou.model.Coordinate;
import com.meiyou.model.TourVo1;
import com.meiyou.pojo.*;
import com.meiyou.service.RootMessageService;
import com.meiyou.service.TourService;
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
import java.util.List;

/**
 * @program: meiyou
 * @description: 同城旅游实现类
 * @author: JK
 * @create: 2019-08-22 19:40
 **/
@Service
public class TourServiceImpl extends BaseServiceImpl implements TourService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RootMessageService rootMessageService;

    @Autowired
    private TourMapper tourMapper;

    @Autowired
    private RootMessageUtil rootMessageUtil;

    @Autowired
    private TourAskMapper tourAskMapper;

    /**
    * @Description: 发布旅游
    * @Author: JK
    * @Date: 2019/8/22
    */
    @Transactional
    @Override
    public Msg insert(Tour tour, String password, String token, double latitude,double longitude) {
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

        //获取发布金
        String publishMoneyName = "publish_money";
        int publishMoneyValue = rootMessageUtil.getRootMessage(publishMoneyName);
        String payWord = user.getPayWord();
        if (payWord == null||payWord.equals("")) {
            msg.setCode(1000);
            msg.setMsg("请设置支付密码");
            return msg;
        }
        //判断用户输入密码是否正确
        if (!password.equals(user.getPayWord())){
            msg.setCode(1001);
            msg.setMsg("支付密码错误");
            return msg;
        }

        //获取诚意金
        String sincerityMoneyName = "sincerity_money";
        int sincerityMoneyValue = rootMessageUtil.getRootMessage(sincerityMoneyName);

        //选择平台担保扣款后剩余余额
        float balance = money-(publishMoneyValue + tour.getReward());
        //选择线下付款扣款后剩余余额
        float balance1 = money-(publishMoneyValue + sincerityMoneyValue + tour.getReward());

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
            //获取发布时定位
            Coordinate coordinate = new Coordinate();
            coordinate.setLatitude(latitude);
            coordinate.setLongitude(longitude);
            coordinate.setKey(tour.getId().toString());
            Long aLong = RedisUtil.addReo(coordinate, Constants.GEO_TOUR);
            if (aLong == 1){
                return Msg.success();
            }
        }
            return Msg.fail();
    }

    /**
    * @Description: 取消发布旅游订单
    * @Author: JK
    * @Date: 2019/8/26
    */
    @Transactional
    @Override
    public Msg deleteTourPublish(Integer id, String token) {
        Tour tour = tourMapper.selectByPrimaryKey(id);
        boolean authToken = RedisUtil.authToken(tour.getPublishId().toString(), token);
        Msg msg = new Msg();
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        //获取当前订单状态
        Integer state = tour.getState();
        if (state == 1) {
            //根据发布者id查询出他所有信息
            User user = userMapper.selectByPrimaryKey(tour.getPublishId());
            //获取发布者账户余额
            Float money = user.getMoney();
            //获取发布金
            String publishMoneyName = "publish_money";
            int publishMoneyValue = rootMessageUtil.getRootMessage(publishMoneyName);
            //获取诚意金
            String sincerityMoneyName = "sincerity_money";
            int sincerityMoneyValue = rootMessageUtil.getRootMessage(sincerityMoneyName);

            if (tour.getPayType() == 0) {
                //选择平台担保取消发布后返回金额后的剩余余额
                float balance = money + (publishMoneyValue + tour.getReward());
                user.setMoney(balance);
            }else {
                //选择线下付款取消发布后返回金额后的剩余余额
                float balance = money + (publishMoneyValue + sincerityMoneyValue + tour.getReward());
                user.setMoney(balance);
            }

            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(tour.getPublishId());
            //更新用户账户余额
            int i1 = userMapper.updateByExample(user, userExample);

            TourExample tourExample = new TourExample();
            tourExample.createCriteria().andIdEqualTo(id)
                        .andPublishIdEqualTo(tour.getPublishId());
            tour.setState(0);
            tour.setUpdateTime(new Date());
            int i2 = tourMapper.updateByExample(tour, tourExample);

            int i = i1 + i2;
            if (i == 2) {
                return Msg.success();
            }
            return Msg.fail();
        }
        msg.setCode(1005);
        msg.setMsg("不能取消订单");
        return msg;
    }

    /**
    * @Description: 从多个旅游订单中选择一个进行报名
    * @Author: JK
    * @Date: 2019/8/26
    */
    @Transactional
    @Override
    public Msg tourAsk(String uid, String password, Integer id, String token) {
        Msg msg = new Msg();
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        Tour tour1 = tourMapper.selectByPrimaryKey(id);
        Integer publishId = tour1.getPublishId();
        if (publishId == Integer.parseInt(uid)){
            msg.setCode(501);
            msg.setMsg("不能报名自己发布的订单");
            return msg;
        }
        //根据报名者id查询出他所有信息
        User user = userMapper.selectByPrimaryKey(Integer.parseInt(uid));
        //获取报名者账户                       余额
        Float money = user.getMoney();

        //获取报名金的金额
        String askMoneyName = "ask_money";
        int askMoneyValue = rootMessageUtil.getRootMessage(askMoneyName);

        String payWord = user.getPayWord();
        if (payWord == null||payWord.equals("")) {
            msg.setCode(1000);
            msg.setMsg("请设置支付密码");
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
        TourAskExample tourAskExample = new TourAskExample();
        tourAskExample.createCriteria().andAskState0EqualTo(1)
                .andAppointIdEqualTo(id).andAskerIdEqualTo(Integer.parseInt(uid));
        List<TourAsk> tourAsks = tourAskMapper.selectByExample(tourAskExample);

        if (tourAsks.size() > 0){
            msg.setCode(250);
            msg.setMsg("请勿重复报名");
            return msg;
        }

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(Integer.parseInt(uid));
        //更新报名者账户余额
        userMapper.updateByExample(user, userExample);

        TourAsk tourAsk = new TourAsk();
        tourAsk.setAskerId(Integer.parseInt(uid));
        tourAsk.setAppointId(id);
        tourAsk.setAskState0(1);
        tourAsk.setCreateTime(new Date());
        tourAsk.setUpdateTime(new Date());
        //旅游记录表中增加一条记录
        tourAskMapper.insertSelective(tourAsk);
        //根据旅游订单表id查出该订单所有信息
        Tour tour = tourMapper.selectByPrimaryKey(id);
        TourExample tourExample = new TourExample();
        tourExample.createCriteria().andIdEqualTo(id);
        tour.setState(2);
        tour.setUpdateTime(new Date());

        //更改该订单状态
        int i = tourMapper.updateByExample(tour,tourExample);
        if (i == 1) {
            return Msg.success();
        } else {
            return Msg.fail();
        }
    }

    /**
    * @Description: 取消报名, 退还美金
    * @Author: JK
    * @Date: 2019/8/26
    */
    @Transactional
    @Override
    public Msg endTourAsk(String uid, Integer id, String token) {
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        TourAskExample tourAskExample = new TourAskExample();
        tourAskExample.createCriteria().andAppointIdEqualTo(id)
                .andAskerIdEqualTo(Integer.parseInt(uid))
                .andAskState0EqualTo(1);
        TourAsk tourAsk = new TourAsk();
        tourAsk.setAskState0(4);
        tourAsk.setUpdateTime(new Date());
        //将报名人员从报名态改为未报名态
        int i = tourAskMapper.updateByExampleSelective(tourAsk, tourAskExample);

        TourAskExample tourAskExample1 = new TourAskExample();
        tourAskExample1.createCriteria().andAppointIdEqualTo(id)
                .andAskState0EqualTo(1)
                .andAskerIdEqualTo(Integer.parseInt(uid));
        //获取当前旅游订单报名人数
        List<TourAsk> tourAsks = tourAskMapper.selectByExample(tourAskExample1);
        //当前旅游订单报名人数为空并且为0，则修改该约会订单状态为1
        if (tourAsks == null || tourAsks.size() == 0) {
            TourExample tourExample = new TourExample();
            tourExample.createCriteria().andIdEqualTo(id);
            Tour tour = new Tour();
            tour.setState(1);
            tour.setUpdateTime(new Date());
            tourMapper.updateByExampleSelective(tour, tourExample);
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
    * @Description: 查询所有报名某个约会旅游的人员信息
    * @Author: JK
    * @Date: 2019/8/26
    */
    @Override
    public Msg selectTourAskList(String uid, Integer appointId, String token) {
        Msg msg = new Msg();
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        TourAskExample tourAskExample = new TourAskExample();
        tourAskExample.createCriteria().andAppointIdEqualTo(appointId);
        List<TourAsk> tourAsks = tourAskMapper.selectByExample(tourAskExample);

        if (tourAsks != null && tourAsks.size() != 0) {
            msg.add("appointAsks", tourAsks);
            msg.setCode(100);
            msg.setMsg("旅游报名对象返回成功");
            return msg;
        }
        Msg fail = Msg.fail();
        msg.add("fail", fail);
        return msg;
    }

    /**
    * @Description: 从所有报名某个旅游的人员信息中选择一个进行确认,
     *没有被选中的人退还报名金
    * @Author: JK
    * @Date: 2019/8/26
    */
    @Transactional
    @Override
    public Msg confirmTourist(String uid, Integer askerId, Integer appointId, String token) {
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        Tour tour = tourMapper.selectByPrimaryKey(appointId);
        TourExample tourExample = new TourExample();
        tourExample.createCriteria().andIdEqualTo(appointId)
                .andPublishIdEqualTo(Integer.parseInt(uid));
        tour.setConfirmId(askerId);
        //在旅游表中3是选中人员等待赴约状态
        tour.setState(3);
        tour.setUpdateTime(new Date());
        int i = tourMapper.updateByExample(tour, tourExample);
        TourAsk tourAsk = new TourAsk();
        TourAskExample tourAskExample = new TourAskExample();
        tourAskExample.createCriteria().andAskerIdEqualTo(askerId).andAppointIdEqualTo(appointId)
                .andAskState0EqualTo(1);

        //旅游报名表中2是被选中状态
        tourAsk.setAskState0(2);
        tourAsk.setUpdateTime(new Date());
        int i1 = tourAskMapper.updateByExampleSelective(tourAsk, tourAskExample);

        TourAskExample tourAskExample1 = new TourAskExample();
        tourAskExample1.createCriteria().andAskState0EqualTo(1)
                .andAskerIdNotEqualTo(askerId);

        List<TourAsk> tourAsks = tourAskMapper.selectByExample(tourAskExample1);
        int i2 = 0;
        int i3 = 0;
        if (tourAsks != null && tourAsks.size() != 0) {
            for (TourAsk ask : tourAsks) {
                TourAsk tourAsk1 = new TourAsk();
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
                tourAsk1.setAskState0(3);
                tourAsk1.setUpdateTime(new Date());
                i2 = tourAskMapper.updateByExampleSelective(tourAsk1, tourAskExample1);
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
    * @Date: 2019/8/26
    */
    @Transactional
    @Override
    public Msg endTour(String uid, Integer id, String token) {
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        TourAskExample tourAskExample = new TourAskExample();
        tourAskExample.createCriteria().andAppointIdEqualTo(id)
                .andAskerIdEqualTo(Integer.parseInt(uid))
                .andAskState0EqualTo(2);
        TourAsk tourAsk = new TourAsk();
        tourAsk.setAskState0(5);
        tourAsk.setUpdateTime(new Date());


        //将报名人员从被选中状态改为取消赴约状态
        int i = tourAskMapper.updateByExampleSelective(tourAsk, tourAskExample);

        TourExample tourExample = new TourExample();
        tourExample.createCriteria().andIdEqualTo(id).andStateEqualTo(3);
        Tour tour = new Tour();
        tour.setState(1);
        tour.setConfirmId(0);
        tour.setUpdateTime(new Date());
        int i1 = tourMapper.updateByExampleSelective(tour, tourExample);

        int i2 = i + i1;
        if (i2 == 2) {
            return Msg.success();
        }
        return Msg.fail();
    }

    /**
    * @Description: 由于发布者自己原因重新发布，退还报名金
    * @Author: JK
    * @Date: 2019/8/26
    */
    @Transactional
    @Override
    public Msg againReleaseTour(String uid, Integer id, String token) {
        Tour tour = tourMapper.selectByPrimaryKey(id);
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        //获取当前订单状态
        Integer state = tour.getState();
        //如果是有人报名等待选中状态，则退还所有报名者的报名金
        int i1 = 0;
        int i2 = 0;
        if (state == 2) {
            TourAskExample tourAskExample = new TourAskExample();
            tourAskExample.createCriteria().andAskState0EqualTo(1)
                    .andAppointIdEqualTo(id);

            List<TourAsk> tourAsks = tourAskMapper.selectByExample(tourAskExample);
            for (TourAsk tourAsk : tourAsks) {
                //获取申请人ID
                Integer askerId = tourAsk.getAskerId();
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

                TourAskExample tourAskExample1 = new TourAskExample();
                tourAskExample1.createCriteria().andAskState0EqualTo(1)
                        .andAppointIdEqualTo(id).andAskerIdEqualTo(askerId);
                TourAsk tourAsk1 = new TourAsk();
                //退还报名金后，报名者状态从1变成8
                tourAsk1.setAskState0(8);
                tourAsk1.setUpdateTime(new Date());
                i2 = tourAskMapper.updateByExampleSelective(tourAsk1, tourAskExample1);
                if (i2 != 1){
                    return Msg.fail();
                }
            }
            TourExample tourExample = new TourExample();
            tourExample.createCriteria().andIdEqualTo(id).andStateEqualTo(2);
            tour.setState(1);
            tour.setUpdateTime(new Date());
            int i3 = tourMapper.updateByExampleSelective(tour, tourExample);
            int i = i1 + i2 + i3;
            if (i == 3) {
                return Msg.success();
            }
        }


        if (state == 3) {
            Integer confirmId = tour.getConfirmId();
            //根据报名者id查询出他所有信息
            User user = userMapper.selectByPrimaryKey(confirmId);
            //获取发布者账户余额
            Float money = user.getMoney();
            //获取报名金的金额
            String askMoneyName = "ask_money";
            int askMoneyValue = rootMessageUtil.getRootMessage(askMoneyName);
            //金额退还后账户余额
            float balance = money + askMoneyValue;

            TourAskExample tourAskExample = new TourAskExample();
            tourAskExample.createCriteria().andAskState0EqualTo(2)
                    .andAppointIdEqualTo(id);
            TourAsk tourAsk = new TourAsk();
            //取消选中，报名者状态从2变成8
            tourAsk.setAskState0(8);
            tourAsk.setUpdateTime(new Date());
            i2 = tourAskMapper.updateByExampleSelective(tourAsk, tourAskExample);

            TourExample tourExample = new TourExample();
            tourExample.createCriteria().andIdEqualTo(id).andStateEqualTo(3);
            tour.setState(1);
            tour.setUpdateTime(new Date());
            int i3 = tourMapper.updateByExampleSelective(tour, tourExample);

            User user1 = new User();
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(confirmId);
            user1.setMoney(balance);
            user1.setUpdateTime(new Date());
            //修改金额
            i1 = userMapper.updateByExampleSelective(user1, userExample);

            int i = i1 + i2 + i3;
            if (i == 3) {
                return Msg.success();
            }
        }

        if (state == 4) {
            Integer confirmId = tour.getConfirmId();
            //根据报名者id查询出他所有信息
            User user = userMapper.selectByPrimaryKey(confirmId);
            //获取发布者账户余额
            Float money = user.getMoney();
            //获取报名金的金额
            String askMoneyName = "ask_money";
            int askMoneyValue = rootMessageUtil.getRootMessage(askMoneyName);
            //金额退还后账户余额
            float balance = money + askMoneyValue;

            TourAskExample tourAskExample = new TourAskExample();
            tourAskExample.createCriteria().andAskState0EqualTo(6)
                    .andAppointIdEqualTo(id);
            TourAsk tourAsk = new TourAsk();
            //取消选中，报名者状态从6变成8
            tourAsk.setAskState0(8);
            tourAsk.setUpdateTime(new Date());
            i2 = tourAskMapper.updateByExampleSelective(tourAsk, tourAskExample);

            TourExample tourExample = new TourExample();
            tourExample.createCriteria().andIdEqualTo(id).andStateEqualTo(4);
            tour.setState(1);
            tour.setUpdateTime(new Date());
            int i3 = tourMapper.updateByExampleSelective(tour, tourExample);

            User user1 = new User();
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(confirmId);
            user1.setMoney(balance);
            user1.setUpdateTime(new Date());
            //修改金额
            i1 = userMapper.updateByExampleSelective(user1, userExample);

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
    * @Date: 2019/8/26
    */
    @Transactional
    @Override
    public Msg confirmTour(String uid, Integer id, String token) {
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        TourAskExample tourAskExample = new TourAskExample();
        tourAskExample.createCriteria().andAskState0EqualTo(2)
                .andAppointIdEqualTo(id);
        TourAsk tourAsk = new TourAsk();
        tourAsk.setAskState0(6);
        tourAsk.setUpdateTime(new Date());
        //更改报名者状态为6，确认赴约
        int i = tourAskMapper.updateByExampleSelective(tourAsk, tourAskExample);

        TourExample tourExample = new TourExample();
        tourExample.createCriteria().andIdEqualTo(id).andStateEqualTo(3);
        Tour tour = new Tour();
        tour.setState(4);
        tour.setUpdateTime(new Date());
        //更改发布者状态为4，报名者确定赴约
        int i1 = tourMapper.updateByExampleSelective(tour, tourExample);

        int i2 = i + i1;
        if (i2 == 2){
            return Msg.success();
        }
        return Msg.fail();
    }

    /**
    * @Description: 确认报名人已到达
    * @Author: JK
    * @Date: 2019/8/26
    */
    @Transactional
    @Override
    public Msg confirmTourArrive(String uid, Integer id, String token) {
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        TourExample tourExample = new TourExample();
        tourExample.createCriteria().andIdEqualTo(id)
                .andStateEqualTo(4)
                .andPublishIdEqualTo(Integer.parseInt(uid));
        Tour tour = new Tour();
        tour.setState(5);
        tour.setUpdateTime(new Date());
        //更改发布者状态为5，报名者已到达，订单完成
        int i = tourMapper.updateByExampleSelective(tour, tourExample);

        TourAskExample tourAskExample = new TourAskExample();
        tourAskExample.createCriteria().andAskState0EqualTo(6)
                .andAppointIdEqualTo(id);
        TourAsk tourAsk = new TourAsk();
        if (i == 1){
            tourAsk.setAskState0(7);
        }
        //更改报名者状态为7，报名者已到达，订单完成
        int i1 = tourAskMapper.updateByExampleSelective(tourAsk, tourAskExample);
        int i2 = i + i1;
        if (i == 1){
            return Msg.success();
        }
        return Msg.fail();
    }

    /**
    * @Description: 查看热门旅游
    * @Author: JK
    * @Date: 2019/8/24
    */
    @Transactional
    @Override
    public Msg selectHotTour(String uid, String token, double latitude, double longitude) {
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
        List<GeoRadiusResponse> responseList = RedisUtil.geoQueryTour(coordinate,radius);
        //判断附近是否有热门旅游
        if (responseList == null || responseList.size() == 0) {
            return Msg.fail();
        }
        ArrayList<TourVo1> list = new ArrayList<>();
        for (GeoRadiusResponse response : responseList) {
            //获取缓存中的key
            String memberByString = response.getMemberByString();
            if (memberByString == null){
                return Msg.fail();
            }
            Tour tour = tourMapper.selectByPrimaryKey(Integer.parseInt(memberByString));
            Integer state = tour.getState();
            //获取用户id
            Integer publisherId = tour.getPublishId();

            User user = userMapper.selectByPrimaryKey(publisherId);
            if (state == 1 || state == 2){
                String nickname = user.getNickname();
                String header = user.getHeader();
                String birthday = user.getBirthday();
                Boolean sex = user.getSex();
                Integer userId = user.getId();
                String goMessage = tour.getGoMessage();
                String startAddress = tour.getStartAddress();
                String endAddress = tour.getEndAddress();
                String goTime = tour.getGoTime();
                Integer needNum = tour.getNeedNum();
                Integer reward = tour.getReward();
                Integer payType = tour.getPayType();
                Integer confirmId = tour.getConfirmId();
                Integer state1 = tour.getState();
                Integer id = tour.getId();
                Date createTime = tour.getCreateTime();

                TourVo1 tourVo1 = new TourVo1();
                tourVo1.setUid(userId);
                tourVo1.setNickname(nickname);
                tourVo1.setHeader(header);
                tourVo1.setBirthday(birthday);
                tourVo1.setSex(sex);
                tourVo1.setTid(id);
                tourVo1.setGoMessage(goMessage);
                tourVo1.setStartAddress(startAddress);
                tourVo1.setEndAddress(endAddress);
                tourVo1.setGoTime(goTime);
                tourVo1.setNeedNum(needNum);
                tourVo1.setReward(reward);
                tourVo1.setPayType(payType);
                tourVo1.setConfirmId(confirmId);
                tourVo1.setState(state1);
                tourVo1.setCreateTime(createTime);

                if (tour.getPayType() == 1){
                    //获取诚意金
                    String sincerityMoneyName = "sincerity_money";
                    int sincerityMoneyValue = rootMessageUtil.getRootMessage(sincerityMoneyName);
                    tourVo1.setSincerityMoneyValue(sincerityMoneyValue);
                }
                list.add(tourVo1);

            }
        }

        for (int i = 0; i < list .size(); i++)    {
            for (int j = list .size()-1; j > i; j--)  {
                Long time= list .get(j).getCreateTime().getTime();
                Long time1= list .get(j-1).getCreateTime().getTime();
                if (time.compareTo(time1)>0)    {
                    //互换位置
                    TourVo1 tourVo1 = list.get(j);
                    list.set(j, list.get(j-1));
                    list.set(j-1, tourVo1 );
                }
            }
        }

        msg.setCode(100);
        msg.setMsg("获取附近热门旅游成功");
        return msg.add("list",list);
    }

    /**
    * @Description: 查询报名旅游的全部人员
    * @Author: JK
    * @Date: 2019/8/29
    */
    @Override
    public Msg selectAllTourById(Integer uid, String token, Integer id) {
        if (!RedisUtil.authToken(uid.toString(), token)) {
            return Msg.noLogin();
        }

            Msg msg = new Msg();
            //判断访问者是否为发布者
            Integer publishId = tourMapper.selectByPrimaryKey(id).getPublishId();
            if (publishId != uid) {
                msg.setCode(506);
                msg.setMsg("没有访问权限");
                return msg;
            }

            //查找报名旅游的记录
        TourAskExample tourAskExample = new TourAskExample();
        tourAskExample.createCriteria().andAppointIdEqualTo(id).andAskState0EqualTo(1);

        List<TourAsk> tours = tourAskMapper.selectByExample(tourAskExample);

        if (tours == null && tours.size() == 0) {
                msg.setCode(404);
                msg.setMsg("找不到指定的旅游记录");
                return msg;
            }

            //对查找出来的ClubBuy进行封装
            List<AskerVO> askerVOS = new ArrayList<>();
            for (TourAsk c : tours) {
                User buyer = getUserByUid(c.getAskerId());

                AskerVO askerVO = new AskerVO();
                askerVO.setId(buyer.getId());
                askerVO.setNickname(buyer.getNickname());
                askerVO.setHeader(buyer.getHeader());
                askerVO.setBirthday(buyer.getBirthday());
                askerVO.setSex(buyer.getSex());
                askerVO.setSignature(buyer.getSignature());
                askerVO.setAskState(c.getAskState0());

                askerVOS.add(askerVO);
            }

            //返回一个封装好的askerVO类
            msg.add("askerVOS", askerVOS);
            msg.setMsg("成功");
            msg.setCode(100);
            return msg;
        }

    /**
    * @Description: 删除我的发布中旅游已完成和已取消的记录
    * @Author: JK
    * @Date: 2019/9/10
    */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Msg delectMyPublishTourRecord(Integer uid, String token, Integer id) {
        if (!RedisUtil.authToken(uid.toString(), token)) {
            return Msg.noLogin();
        }
        TourExample tourExample = new TourExample();
        tourExample.createCriteria().andIdEqualTo(id);
        Tour tour = new Tour();
        tour.setState(7);
        int i = tourMapper.updateByExampleSelective(tour,tourExample);

       /* //判断旅游报名表中报名者是否删除记录
        TourAskExample tourAskExample = new TourAskExample();
        tourAskExample.createCriteria().andAppointIdEqualTo(id).andAskState0EqualTo(9);
        List<TourAsk> tourAsks = tourAskMapper.selectByExample(tourAskExample);
        if (tourAsks.size() == 1){
                //旅游报名者删除记录，则将数据库中的数据删除
                int i1 = tourAskMapper.deleteByExample(tourAskExample);
                int i2 = tourMapper.deleteByPrimaryKey(null);
            int i3 = i1 + i2;
            if(i3==1){
               throw new RuntimeException();
           }
        }*/
        if (i == 1){
            return Msg.success();
        }
        return Msg.fail();
    }

    /**
    * @Description: 删除我的报名中旅游已完成和已取消的记录
    * @Author: JK
    * @Date: 2019/9/10
    */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Msg delectMyAskTourRecord(Integer uid, String token, Integer id) {
        if (!RedisUtil.authToken(uid.toString(), token)) {
            return Msg.noLogin();
        }
        TourAskExample tourAskExample = new TourAskExample();
        tourAskExample.createCriteria().andIdEqualTo(id);
        TourAsk tourAsk = new TourAsk();
        tourAsk.setAskState0(9);
        int i = tourAskMapper.updateByExampleSelective(tourAsk, tourAskExample);

       /* //查询旅游发布表中的主键id
        TourAsk tourAsk1 = tourAskMapper.selectByPrimaryKey(id);
        Integer appointId = tourAsk1.getAppointId();
        //判断旅游发布表中发布者是否删除记录
        TourExample tourExample = new TourExample();
        tourExample.createCriteria().andIdEqualTo(appointId).andStateEqualTo(7);
        List<Tour> tours = tourMapper.selectByExample(tourExample);
        if (tours.size() == 1){
            //旅游报名者删除记录，则将数据库中的数据删除
            try {
                tourMapper.deleteByExample(tourExample);
                tourAskMapper.deleteByPrimaryKey(id);
            } catch (RuntimeException e) {
                throw new RuntimeException();
            }
        }*/
        if (i == 1){
            return Msg.success();
        }
        return Msg.fail();
    }
}

