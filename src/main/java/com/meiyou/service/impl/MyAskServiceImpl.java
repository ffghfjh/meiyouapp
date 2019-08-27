package com.meiyou.service.impl;

import com.meiyou.mapper.*;
import com.meiyou.pojo.*;
import com.meiyou.service.MyAskService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @program: meiyou
 * @description: 我的报名实现类
 * @author: JK
 * @create: 2019-08-26 15:17
 **/
@Service
public class MyAskServiceImpl implements MyAskService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AppointAskMapper appointAskMapper;
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private TourAskMapper tourAskMapper;
    @Autowired
    private TourMapper tourMapper;

    /**
     * @Description: 查询我的约会报名
     * @Author: JK
     * @Date: 2019/8/26
     */
    @Override
    public Msg selectMyAppointmentAsk(String uid, String token) {
        Msg msg = new Msg();
        new Msg();
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        AppointAskExample appointAskExample = new AppointAskExample();
        HashMap<String, Object> map = new HashMap<>();
        appointAskExample.createCriteria().andAskerIdEqualTo(Integer.parseInt(uid));
        List<AppointAsk> appointAsks = appointAskMapper.selectByExample(appointAskExample);
        if (appointAsks != null && appointAsks.size() != 0) {
            ArrayList<Object> list = new ArrayList<>();
            for (AppointAsk ask : appointAsks) {
                Integer appointId = ask.getAppointId();
                Integer askState = ask.getAskState();
                Appointment appointment = appointmentMapper.selectByPrimaryKey(appointId);
                User user = userMapper.selectByPrimaryKey(appointId);
                String header = null;
                String appointContext = null;
                String appointTime = null;
                String appointAddress = null;

                switch (askState) {
                    case 1:
                        header = user.getHeader();
                        appointContext = appointment.getAppointContext();
                        appointTime = appointment.getAppointTime();
                        appointAddress = appointment.getAppointAddress();

                        map.put("header", header);
                        map.put("appointContext", appointContext);
                        map.put("appointTime", appointTime);
                        map.put("appointAddress", appointAddress);
                        map.put("askState", askState);
                        list.add(map);
                        break;
                    case 2:
                        header = user.getHeader();
                        appointContext = appointment.getAppointContext();
                        appointTime = appointment.getAppointTime();
                        appointAddress = appointment.getAppointAddress();

                        map.put("header", header);
                        map.put("appointContext", appointContext);
                        map.put("appointTime", appointTime);
                        map.put("appointAddress", appointAddress);
                        map.put("askState", askState);
                        list.add(map);
                        break;
                    case 3:
                        header = user.getHeader();
                        appointContext = appointment.getAppointContext();
                        appointTime = appointment.getAppointTime();
                        appointAddress = appointment.getAppointAddress();

                        map.put("header", header);
                        map.put("appointContext", appointContext);
                        map.put("appointTime", appointTime);
                        map.put("appointAddress", appointAddress);
                        map.put("askState", askState);
                        list.add(map);
                        break;
                    case 4:
                        header = user.getHeader();
                        appointContext = appointment.getAppointContext();
                        appointTime = appointment.getAppointTime();
                        appointAddress = appointment.getAppointAddress();

                        map.put("header", header);
                        map.put("appointContext", appointContext);
                        map.put("appointTime", appointTime);
                        map.put("appointAddress", appointAddress);
                        map.put("askState", askState);
                        list.add(map);
                        break;
                    case 5:
                        header = user.getHeader();
                        appointContext = appointment.getAppointContext();
                        appointTime = appointment.getAppointTime();
                        appointAddress = appointment.getAppointAddress();

                        map.put("header", header);
                        map.put("appointContext", appointContext);
                        map.put("appointTime", appointTime);
                        map.put("appointAddress", appointAddress);
                        map.put("askState", askState);
                        list.add(map);
                        break;
                    case 6:
                        header = user.getHeader();
                        appointContext = appointment.getAppointContext();
                        appointTime = appointment.getAppointTime();
                        appointAddress = appointment.getAppointAddress();

                        map.put("header", header);
                        map.put("appointContext", appointContext);
                        map.put("appointTime", appointTime);
                        map.put("appointAddress", appointAddress);
                        map.put("askState", askState);
                        list.add(map);
                        break;
                    case 7:
                        header = user.getHeader();
                        appointContext = appointment.getAppointContext();
                        appointTime = appointment.getAppointTime();
                        appointAddress = appointment.getAppointAddress();

                        map.put("header", header);
                        map.put("appointContext", appointContext);
                        map.put("appointTime", appointTime);
                        map.put("appointAddress", appointAddress);
                        map.put("askState", askState);
                        list.add(map);
                        break;
                }
            }
            msg.setMsg("查询我的约会报名返回成功");
            msg.setCode(100);
            return msg.add("list", list);
        }
        return Msg.fail();
    }

    /**
    * @Description: 查询我的旅游报名
    * @Author: JK
    * @Date: 2019/8/26
    */
    @Override
    public Msg selectMyTourAsk(String uid, String token) {
        Msg msg = new Msg();
        new Msg();
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return Msg.noLogin();
        }
        TourAskExample tourAskExample = new TourAskExample();
        HashMap<String, Object> map = new HashMap<>();
        tourAskExample.createCriteria().andAskerIdEqualTo(Integer.parseInt(uid));
        List<TourAsk> tourAsks = tourAskMapper.selectByExample(tourAskExample);
        if (tourAsks != null && tourAsks.size() != 0) {
            ArrayList<Object> list = new ArrayList<>();
            for (TourAsk ask : tourAsks) {
                Integer appointId = ask.getAppointId();
                Integer askState = ask.getAskState0();
                Tour tour = tourMapper.selectByPrimaryKey(appointId);
                User user = userMapper.selectByPrimaryKey(appointId);
                String header = null;
                String goMessage = null;
                String startAddress = null;
                String endAddress = null;
                String goTime = null;

                switch (askState) {
                    case 1:
                        header = user.getHeader();
                        goMessage = tour.getGoMessage();
                        startAddress = tour.getStartAddress();
                        endAddress = tour.getEndAddress();
                        goTime = tour.getGoTime();


                        map.put("header", header);
                        map.put("goMessage ", goMessage );
                        map.put("startAddress ", startAddress );
                        map.put("endAddress ", endAddress );
                        map.put("goTime ", goTime );
                        map.put("askState", askState);
                        list.add(map);
                        break;
                    case 2:
                        header = user.getHeader();
                        goMessage = tour.getGoMessage();
                        startAddress = tour.getStartAddress();
                        endAddress = tour.getEndAddress();
                        goTime = tour.getGoTime();


                        map.put("header", header);
                        map.put("goMessage ", goMessage );
                        map.put("startAddress ", startAddress );
                        map.put("endAddress ", endAddress );
                        map.put("goTime ", goTime );
                        map.put("askState", askState);
                        list.add(map);
                        break;
                    case 3:
                        header = user.getHeader();
                        goMessage = tour.getGoMessage();
                        startAddress = tour.getStartAddress();
                        endAddress = tour.getEndAddress();
                        goTime = tour.getGoTime();


                        map.put("header", header);
                        map.put("goMessage ", goMessage );
                        map.put("startAddress ", startAddress );
                        map.put("endAddress ", endAddress );
                        map.put("goTime ", goTime );
                        map.put("askState", askState);
                        list.add(map);
                        break;
                    case 4:
                        header = user.getHeader();
                        goMessage = tour.getGoMessage();
                        startAddress = tour.getStartAddress();
                        endAddress = tour.getEndAddress();
                        goTime = tour.getGoTime();


                        map.put("header", header);
                        map.put("goMessage ", goMessage );
                        map.put("startAddress ", startAddress );
                        map.put("endAddress ", endAddress );
                        map.put("goTime ", goTime );
                        map.put("askState", askState);
                        list.add(map);
                        break;
                    case 5:
                        header = user.getHeader();
                        goMessage = tour.getGoMessage();
                        startAddress = tour.getStartAddress();
                        endAddress = tour.getEndAddress();
                        goTime = tour.getGoTime();


                        map.put("header", header);
                        map.put("goMessage ", goMessage );
                        map.put("startAddress ", startAddress );
                        map.put("endAddress ", endAddress );
                        map.put("goTime ", goTime );
                        map.put("askState", askState);
                        list.add(map);
                        break;
                    case 6:
                        header = user.getHeader();
                        goMessage = tour.getGoMessage();
                        startAddress = tour.getStartAddress();
                        endAddress = tour.getEndAddress();
                        goTime = tour.getGoTime();


                        map.put("header", header);
                        map.put("goMessage ", goMessage );
                        map.put("startAddress ", startAddress );
                        map.put("endAddress ", endAddress );
                        map.put("goTime ", goTime );
                        map.put("askState", askState);
                        list.add(map);
                        break;
                    case 7:
                        header = user.getHeader();
                        goMessage = tour.getGoMessage();
                        startAddress = tour.getStartAddress();
                        endAddress = tour.getEndAddress();
                        goTime = tour.getGoTime();


                        map.put("header", header);
                        map.put("goMessage ", goMessage );
                        map.put("startAddress ", startAddress );
                        map.put("endAddress ", endAddress );
                        map.put("goTime ", goTime );
                        map.put("askState", askState);
                        list.add(map);
                        break;
                }
            }
            msg.setMsg("查询我的旅游报名返回成功");
            msg.setCode(100);
            return msg.add("list", list);
        }
        return Msg.fail();
    }
}
