package com.meiyou.service.impl;

import com.meiyou.mapper.TourMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.model.Coordinate;
import com.meiyou.pojo.Tour;
import com.meiyou.pojo.User;
import com.meiyou.pojo.UserExample;
import com.meiyou.service.RootMessageService;
import com.meiyou.service.TourService;
import com.meiyou.utils.AppointmentUtil;
import com.meiyou.utils.Constants;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.GeoRadiusResponse;

import java.util.ArrayList;
import java.util.HashMap;
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
    private RootMessageService rootMessageService;

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

    @Override
    public Msg selectTourList(String uid, String token) {
        return null;
    }

    @Override
    public Msg deletePublish(Integer id, String token) {
        return null;
    }

    @Override
    public Msg startEnrollment(String uid, String password, Integer id, String token) {
        return null;
    }

    @Override
    public Msg endEnrollment(String uid, Integer id, String token) {
        return null;
    }

    @Override
    public Msg selectTourAskList(String uid, Integer appointId, String token) {
        return null;
    }

    @Override
    public Msg confirmUserId(String uid, Integer askerId, Integer appointId, String token) {
        return null;
    }

    @Override
    public Msg endTour(String uid, Integer id, String token) {
        return null;
    }

    @Override
    public Msg againRelease(String uid, Integer id, String token) {
        return null;
    }

    @Override
    public Msg confirmTour(String uid, Integer id, String token) {
        return null;
    }

    @Override
    public Msg confirmArrive(String uid, Integer id, String token) {
        return null;
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
            Msg noLogin = Msg.noLogin();
            msg.add("noLogin", noLogin);
            return msg;
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
            HashMap<String, Object> map = new HashMap<>();
            ArrayList<Object> list = new ArrayList<>();
            if (state == 1 || state == 2){
                String nickname = user.getNickname();
                String header = user.getHeader();
                String birthday = user.getBirthday();
                Boolean sex = user.getSex();
                String goMessage = tour.getGoMessage();
                String startAddress = tour.getStartAddress();
                String endAddress = tour.getEndAddress();
                String goTime = tour.getGoTime();
                Integer needNum = tour.getNeedNum();
                Integer reward = tour.getReward();
                Integer payType = tour.getPayType();
                Integer confirmId = tour.getConfirmId();
                Integer state1 = tour.getState();
                if (tour.getPayType() == 1){
                    //获取诚意金
                    String sincerityMoneyName = "sincerity_money";
                    int sincerityMoneyValue = appointmentUtil.getRootMessage(sincerityMoneyName);
                    map.put("sincerityMoneyValue",sincerityMoneyValue);
                }
                map.put("nickname",nickname);
                map.put("header",header);
                map.put("birthday",birthday);
                map.put("sex",sex);
                map.put("goMessage",goMessage);
                map.put("startAddress",startAddress);
                map.put("endAddress",endAddress);
                map.put("goTime",goTime);
                map.put("needNum",needNum);
                map.put("reward",reward);
                map.put("payType",payType);
                map.put("confirmId",confirmId);
                map.put("state1",state1);
                list.add(map);

            }
            msg.setCode(100);
            msg.setMsg("获取附近热门旅游成功");
            return msg.add("list",list);
        }
        Msg fail = Msg.fail();
        msg.add("fail",fail);
        return msg;
    }
}
