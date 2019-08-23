package com.meiyou.service.impl;

import com.meiyou.mapper.RootMessageMapper;
import com.meiyou.mapper.TourMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.model.Coordinate;
import com.meiyou.pojo.Tour;
import com.meiyou.pojo.User;
import com.meiyou.pojo.UserExample;
import com.meiyou.service.TourService;
import com.meiyou.utils.AppointmentUtil;
import com.meiyou.utils.Constants;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private AppointmentUtil appointmentUtil;

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
        int publishMoneyValue = appointmentUtil.getRootMessage(publishMoneyName);

        //判断用户输入密码是否正确
        if (!password.equals(user.getPayWord())){
            msg.setCode(1001);
            msg.setMsg("支付密码错误");
            return msg;
        }

        //获取诚意金
        String sincerityMoneyName = "sincerity_money";
        int sincerityMoneyValue = appointmentUtil.getRootMessage(sincerityMoneyName);

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

        //获取发布时定位
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(latitude);
        coordinate.setLongitude(longitude);
        RedisUtil.addReo(coordinate, Constants.GEO_TOUR);

        int i = tourMapper.insertSelective(tour);
        if (i == 1){
            return Msg.success();
        }else {
            return Msg.fail();
        }
    }
}
