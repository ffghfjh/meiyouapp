package com.meiyou.controller;

import com.meiyou.model.AppointmentVO;
import com.meiyou.model.ClubVO;
import com.meiyou.model.ShopVO;
import com.meiyou.model.TourVO;
import com.meiyou.service.MyPublishService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: meiyou
 * @description: 我的发布控制层
 * @author: JK
 * @create: 2019-08-26 15:04
 **/
@RestController
@Api(value = "我的发布控制层", tags = {"我的发布控制层"})
public class MyPublishController {
    @Autowired
    private MyPublishService myPublishService;

    @PostMapping("/selectMyPublishAppointmentList")
    @ApiOperation(value = "通过用户id查找指定用户id发布的全部约会",notes = "返回为AppointmentVO类,nums为报名人数")
    public Msg selectMyPublishAppointmentList(@RequestParam("uid") String uid,
                                   @RequestParam("token") String token){
//        if(!RedisUtil.authToken(uid,token)){
//            return Msg.noLogin();
//        }
        Msg msg = new Msg();
        List<AppointmentVO> appointmentList = myPublishService.selectAppointmentList(uid, token);
        if (appointmentList.isEmpty()){
            msg.add("appointmentList",null);
        }else {
            msg.add("appointmentList",appointmentList);
        }
        msg.setCode(100);
        msg.setMsg("查询成功");
        return msg;
    }

    @PostMapping("/selectMyPublishTourList")
    @ApiOperation(value = "通过用户id查找指定用户id发布的全部旅游",notes = "返回为TourVO类,nums为报名人数")
    public Msg selectMyPublishTourList(@RequestParam("uid") String uid,
                                              @RequestParam("token") String token){
//        if(!RedisUtil.authToken(uid,token)){
//            return Msg.noLogin();
//        }
        Msg msg = new Msg();
        List<TourVO> tourList = myPublishService.selectTourList(uid, token);
        if (tourList.isEmpty()){
            msg.add("tourList",null);
        }else {
            msg.add("tourList",tourList);
        }
        msg.setCode(100);
        msg.setMsg("查询成功");
        return msg;
    }


    @PostMapping("/selectMyPublishClubList")
    @ApiOperation(value = "通过用户id查找指定用户id发布的全部会所",notes = "返回为ClubVO类,nums为报名人数")
    public Msg selectMyPublishClubList(@RequestParam("uid") String uid,
                                              @RequestParam("token") String token){
//        if(!RedisUtil.authToken(uid,token)){
//            return Msg.noLogin();
//        }
        Msg msg = new Msg();
        List<ClubVO> clubList = myPublishService.selectClubByUid(Integer.valueOf(uid),token);
        if (clubList.isEmpty()){
            msg.add("clubList",null);
        }else {
            msg.add("clubList",clubList);
        }
        msg.setCode(100);
        msg.setMsg("查询成功");
        return msg;
    }


    @PostMapping("/selectMyPublishShopList")
    @ApiOperation(value = "通过用户id查找指定用户id发布的全部景点商家",notes = "返回为ShopVO类,nums为报名人数")
    public Msg selectMyPublishShopList(@RequestParam("uid") String uid,
                                              @RequestParam("token") String token){
//        if(!RedisUtil.authToken(uid,token)){
//            return Msg.noLogin();
//        }
        Msg msg = new Msg();
        List<ShopVO> shopList = myPublishService.selectShopByUid(Integer.valueOf(uid), token);
        if (shopList.isEmpty()){
            msg.add("shopList",null);
        }else {
            msg.add("shopList",shopList);
        }
        msg.setCode(100);
        msg.setMsg("查询成功");
        return msg;
    }
}
