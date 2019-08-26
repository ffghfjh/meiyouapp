package com.meiyou.service.impl;

import com.meiyou.mapper.*;
import com.meiyou.pojo.*;
import com.meiyou.service.MyPublishService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @program: meiyou
 * @description:
 * @author: JK
 * @create: 2019-08-26 15:03
 **/
@Service
public class MyPublishServiceImpl implements MyPublishService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private AppointAskMapper appointAskMapper;
    @Autowired
    private TourMapper tourMapper;
    @Autowired
    private TourAskMapper tourAskMapper;

    /**
     * @Description: 查询我的约会发布列表
     * @Author: JK
     * @Date: 2019/8/22
     */
    @Override
    public Msg selectAppointmentList(String uid, String token) {
        Msg msg = new Msg();
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        AppointmentExample example = new AppointmentExample();
        example.createCriteria().andPublisherIdEqualTo(Integer.parseInt(uid));
        List<Appointment> appointments = appointmentMapper.selectByExample(example);
        ArrayList<Object> arrayList = new ArrayList<>();
        for (Appointment appointment : appointments) {

            AppointAskExample appointAskExample = new AppointAskExample();
            appointAskExample.createCriteria().andAskStateEqualTo(1).andAppointIdEqualTo(appointment.getId());
            List<AppointAsk> appointAsks = appointAskMapper.selectByExample(appointAskExample);
            HashMap<String, Object> map = new HashMap<>();
            int size = 0;
            Integer state = appointment.getState();
            if (appointment.getState() == 2){
                ArrayList<Object> arrayList1 = new ArrayList<>();
                for (AppointAsk appointAsk : appointAsks) {
                    Integer askerId = appointAsk.getAskerId();
                    User user = userMapper.selectByPrimaryKey(askerId);
                    String askerHeader = user.getHeader();
                    arrayList1.add(askerHeader);
                    map.put("arrayList1",arrayList1);
                }
                //获取报名的总人数
                size = appointAsks.size();
                map.put("size", size);
                state = appointment.getState();
                map.put("state",state);
            }
            //获取用户id
            Integer publisherId = appointment.getPublisherId();
            User user = userMapper.selectByPrimaryKey(publisherId);

            String nickname = user.getNickname();
            String header = user.getHeader();
            String birthday = user.getBirthday();
            String signature = user.getSignature();
            String appointContext = appointment.getAppointContext();
            String appointTime = appointment.getAppointTime();
            String appointAddress = appointment.getAppointAddress();
            String askerHeader = null;


            map.put("nickname",nickname);
            map.put("header",header);
            map.put("birthday",birthday);
            map.put("appointContext",appointContext);
            map.put("appointTime",appointTime);
            map.put("appointAddress",appointAddress);
            map.put("signature",signature);
            map.put("size",size);
            map.put("askerHeader",askerHeader);
            map.put("state",state);
            arrayList.add(map);
        }

        if (appointments != null && appointments.size() != 0) {
            msg.add("arrayList", arrayList);
            msg.setCode(100);
            msg.setMsg("我的约会发布列表返回成功");
            return msg;
        }
        Msg fail = Msg.fail();
        msg.add("fail", fail);
        return msg;
    }


    /**
     * @Description: 查询我的旅游发布列表
     * @Author: JK
     * @Date: 2019/8/26
     */
    @Override
    public Msg selectTourList(String uid, String token) {
        Msg msg = new Msg();
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        TourExample tourExample = new TourExample();
        tourExample.createCriteria().andPublishIdEqualTo(Integer.parseInt(uid));
        List<Tour> tours = tourMapper.selectByExample(tourExample);
        ArrayList<Object> arrayList = new ArrayList<>();
        for (Tour tour : tours) {
            TourAskExample tourAskExample = new TourAskExample();
            tourAskExample.createCriteria().andAskState0EqualTo(1).andAppointIdEqualTo(tour.getId());
            List<TourAsk> tourAsks = tourAskMapper.selectByExample(tourAskExample);
            HashMap<String, Object> map = new HashMap<>();
            int size = 0;
            Integer state = tour.getState();
            if (tour.getState() == 2){
                ArrayList<Object> arrayList1 = new ArrayList<>();
                for (TourAsk tourAsk : tourAsks) {
                    Integer askerId = tourAsk.getAskerId();
                    User user = userMapper.selectByPrimaryKey(askerId);
                    String askerHeader = user.getHeader();
                    arrayList1.add(askerHeader);
                    map.put("arrayList1",arrayList1);
                }
                //获取报名的总人数
                size = tourAsks.size();
                map.put("size", size);
                state = tour.getState();
                map.put("state",state);
            }
            //获取用户id
            Integer publisherId = tour.getPublishId();
            User user = userMapper.selectByPrimaryKey(publisherId);

            String nickname = user.getNickname();
            String header = user.getHeader();
            String birthday = user.getBirthday();
            String signature = user.getSignature();
            String goMessage = tour.getGoMessage();
            String startAddress = tour.getStartAddress();
            String endAddress = tour.getEndAddress();
            String goTime = tour.getGoTime();
            String askerHeader = null;


            map.put("nickname",nickname);
            map.put("header",header);
            map.put("birthday",birthday);
            map.put("goMessage",goMessage);
            map.put("startAddress",startAddress);
            map.put("endAddress",endAddress);
            map.put("goTime",goTime);
            map.put("signature",signature);
            map.put("size",size);
            map.put("askerHeader",askerHeader);
            map.put("state",state);
            arrayList.add(map);
        }

        if (tours != null && tours.size() != 0) {
            msg.add("arrayList", arrayList);
            msg.setCode(100);
            msg.setMsg("我的旅游发布列表返回成功");
            return msg;
        }
        Msg fail = Msg.fail();
        msg.add("fail", fail);
        return msg;
    }
}
