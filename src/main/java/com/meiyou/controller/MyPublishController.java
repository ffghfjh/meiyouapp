package com.meiyou.controller;

import com.meiyou.service.MyPublishService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

//    @GetMapping("/selectClubList")
//    @ApiOperation(value = "通过用户id查找指定用户id发布的全部按摩会所",notes = "返回为ClubVO类,nums为报名人数")
//    public Msg getClubByUid(@RequestParam("uid") Integer uid,
//                            @RequestParam("token") String token){
//        return myPublishService.selectClubByUid(uid,token);
//    }

//    @GetMapping("/selectShopList")
//    @ApiOperation(value = "通过用户id查找指定用户id发布的全部景点商家",notes = "返回为ShopVO类,nums为报名人数")
//    public Msg getShopByUid(@RequestParam("uid") Integer uid,
//                            @RequestParam("token") String token){
//        return myPublishService.selectShopByUid(uid, token);
//    }

    @GetMapping("/selectMyPublishList")
    @ApiOperation(value = "通过用户id查找指定用户id发布的全部景点商家",notes = "返回为ShopVO类,nums为报名人数")
    public Msg selectMyPublishList(@RequestParam("uid") String uid,
                            @RequestParam("token") String token){
        if(!RedisUtil.authToken(uid,token)){
            return Msg.noLogin();
        }
        Msg msg = new Msg();
        List<Object> appointmentList = myPublishService.selectAppointmentList(uid, token);
        List<Object> tourList = myPublishService.selectTourList(uid, token);
//        myPublishService.selectClubByUid(Integer.valueOf(uid),token);
//        myPublishService.selectShopByUid(Integer.valueOf(uid), token);
        msg.add("appointmentList",appointmentList);
        msg.add("tourList",tourList);
        msg.setCode(100);
        msg.setMsg("成功");
        return msg;
    }
}
