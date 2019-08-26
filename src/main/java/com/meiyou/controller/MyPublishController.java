package com.meiyou.controller;

import com.meiyou.service.MyPublishService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: meiyou
 * @description:
 * @author: JK
 * @create: 2019-08-26 15:04
 **/
@RestController
@Api(value = "我的发布控制层", tags = {"我的发布控制层"})
public class MyPublishController {
    @Autowired
    private MyPublishService myPublishService;

    /**
     * @Description: 查询所有我发布的约会
     * @Author: JK
     * @Date: 2019/8/22
     */
    @ApiOperation(value = "查询所有我发布的约会", notes = "查询所有我发布的约会", httpMethod = "GET")
    @GetMapping(value = "/selectAppointmentList")
    public Msg selectAppointmentList(String uid, String token) {
        Msg msg = myPublishService.selectAppointmentList(uid, token);
        return msg;
    }

    /**
     * @Description: 查询所有我发布的旅游
     * @Author: JK
     * @Date: 2019/8/22
     */
    @ApiOperation(value = "查询所有我发布的旅游", notes = "查询所有我发布的旅游", httpMethod = "GET")
    @GetMapping(value = "/selectTourList")
    public Msg selectTourList(String uid,String token) {
        Msg msg = myPublishService.selectTourList(uid, token);
        return msg;
    }
}
