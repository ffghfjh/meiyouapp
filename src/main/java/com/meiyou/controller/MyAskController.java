//package com.meiyou.controller;
//
//import com.meiyou.service.MyAskService;
//import com.meiyou.utils.Msg;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @program: meiyou
// * @description: 我的报名控制层
// * @author: JK
// * @create: 2019-08-26 15:19
// **/
//@Api(value = "我的报名控制层", tags = {"我的报名控制层"})
//@RestController
//public class MyAskController {
//    @Autowired
//    private MyAskService myAskService;
//
//    /**
//    * @Description: 查询我的约会报名
//    * @Author: JK
//    * @Date: 2019/8/26
//    */
//    @ApiOperation(value = "查询我的约会报名", notes = "查询我的约会报名", httpMethod = "GET")
//    @GetMapping(value = "/selectMyAppointmentAsk")
//    public Msg selectMyAppointmentAsk(String uid, String token) {
//        Msg msg = myAskService.selectMyAppointmentAsk(uid, token);
//        return msg;
//    }
//}
