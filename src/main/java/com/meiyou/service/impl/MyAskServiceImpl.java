package com.meiyou.service.impl;

import com.meiyou.mapper.*;
import com.meiyou.model.ClubVO;
import com.meiyou.model.ShopVO;
import com.meiyou.pojo.*;
import com.meiyou.service.MyAskService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
public class MyAskServiceImpl extends BaseServiceImpl implements MyAskService {
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

    @Autowired
    ClubMapper clubMapper;

    @Autowired
    ClubBuyMapper clubBuyMapper;

    @Autowired
    ShopMapper shopMapper;

    @Autowired
    ShopBuyMapper shopBuyMapper;

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

    /**
     * 查找指定用户的会所购买记录
     * @param uid
     * @return
     */
    @Override
    @Cacheable(cacheNames = "buy")
    public List<ClubVO> selectMyClubAsk(Integer uid) {

        Msg msg = new Msg();
        //查找购买按摩会所的记录
        ClubBuyExample clubBuyExample = new ClubBuyExample();
        //购买者id为uid的购买记录
        clubBuyExample.createCriteria().andBuyerIdEqualTo(uid);

        List<ClubBuy> result = clubBuyMapper.selectByExample(clubBuyExample);

        //对查找出来的ClubBuy进行封装
        List<ClubVO> clubVOS = new ArrayList<>();
        if(result.isEmpty()){
            return clubVOS;
        }

        for(ClubBuy c : result){
            Club club = clubMapper.selectByPrimaryKey(c.getClubId());

            ClubVO clubVO = setClubToClubVO(club);
            //设置购买者状态
            clubVO.setAskState(c.getState());

            clubVOS.add(clubVO);
        }

        //返回一个封装好的ClubVO类
        return clubVOS;
    }

    /**
     * 查询用户聘请的全部导游记录
     * @param uid
     * @return
     */
    @Override
    public List<ShopVO> selectMyShopAsk(Integer uid) {

        //查找购买按摩会所的记录
        ShopBuyExample shopBuyExample = new ShopBuyExample();
        shopBuyExample.createCriteria().andBuyerIdEqualTo(uid);
        List<ShopBuy> result = shopBuyMapper.selectByExample(shopBuyExample);

        //对查找出来的ShopBuy进行封装
        List<ShopVO> shopVOS = new ArrayList<>();
        if(result.isEmpty()){
            return shopVOS;
        }


        for(ShopBuy shopBuy : result){
            Shop shop = shopMapper.selectByPrimaryKey(shopBuy.getGuideId());

            ShopVO shopVO = setShopToShopVO(shop);
            //设置购买者状态
            shopVO.setState(shopBuy.getState());

            shopVOS.add(shopVO);
        }

        //返回一个封装好的ShopVO类
        return shopVOS;
    }
}
