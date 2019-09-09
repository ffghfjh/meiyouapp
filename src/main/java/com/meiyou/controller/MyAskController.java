package com.meiyou.controller;

import com.meiyou.model.AppointmentVO;
import com.meiyou.model.ClubVO;
import com.meiyou.model.ShopVO;
import com.meiyou.model.TourVO;
import com.meiyou.service.MyAskService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: meiyou
 * @description: 我的报名控制层
 * @author: JK
 * @create: 2019-08-26 15:19
 **/
@Api(value = "我的报名控制层", tags = {"我的报名控制层"})
@RestController
public class MyAskController {
    @Autowired
    private MyAskService myAskService;

    /**
    * @Description: 查询我的约会报名
    * @Author: JK
    * @Date: 2019/8/26
    */

    @ApiOperation(value = "查询我的约会报名", notes = "查询我的约会报名")
    @PostMapping(value = "/selectMyAppointmentAskList")
    public Msg selectMyAppointmentAskList(String uid, String token) {
        if(!RedisUtil.authToken(uid,token)){
            return Msg.noLogin();
        }
        Msg msg = new Msg();
        List<AppointmentVO> appointmentVOS = myAskService.selectMyAppointmentAsk(uid, token);
        if(appointmentVOS.isEmpty()){
            msg.add("appointmentVOS",null);
        }else {
            msg.add("appointmentVOS",appointmentVOS);
        }
        msg.setCode(100);
        msg.setMsg("成功");
        System.out.println(msg);
        return msg;
    }

    @ApiOperation(value = "查询我的旅游报名", notes = "查询我的旅游报名")
    @PostMapping(value = "/selectMyTourAskList")
    public Msg selectMyTourAskList(String uid, String token) {
        if(!RedisUtil.authToken(uid,token)){
            return Msg.noLogin();
        }
        Msg msg = new Msg();
        List<TourVO> tourVOS = myAskService.selectMyTourAsk(uid, token);
        if(tourVOS.isEmpty()){
            msg.add("tourVOS",null);
        }else {
            msg.add("tourVOS",tourVOS);
        }
        msg.setCode(100);
        msg.setMsg("成功");
        System.out.println(msg);
        return msg;
    }

    @ApiOperation(value = "查询我的会所报名", notes = "查询我的会所报名")
    @PostMapping(value = "/selectMyClubAskList")
    public Msg selectMyClubAskList(String uid, String token) {
        if(!RedisUtil.authToken(uid,token)){
            return Msg.noLogin();
        }
        Msg msg = new Msg();
        List<ClubVO> clubVOS = myAskService.selectMyClubAsk(Integer.valueOf(uid));
        if(clubVOS.isEmpty()){
            msg.add("clubVOS",null);
        }else {
            msg.add("clubVOS",clubVOS);
        }
        msg.setCode(100);
        msg.setMsg("成功");
        System.out.println(msg);
        return msg;
    }

    @ApiOperation(value = "查询我的景点商家报名", notes = "查询我的景点商家报名")
    @PostMapping(value = "/selectMyShopAskList")
    public Msg selectMyShopAskList(String uid, String token) {
        if(!RedisUtil.authToken(uid,token)){
            return Msg.noLogin();
        }
        Msg msg = new Msg();
        List<ShopVO> shopVOS = myAskService.selectMyShopAsk(Integer.valueOf(uid));
        if(shopVOS.isEmpty()){
            msg.add("shopVOS",null);
        }else {
            msg.add("shopVOS",shopVOS);
        }
        msg.setCode(100);
        msg.setMsg("成功");
        System.out.println(msg);
        return msg;
    }
}
