package com.meiyou.controller;

import com.meiyou.service.MyPublishService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/selectClubList")
    @ApiOperation(value = "通过用户id查找指定用户id发布的全部按摩会所",notes = "返回为ClubVO类,nums为报名人数")
    public Msg getClubByUid(@RequestParam("uid") Integer uid,
                            @RequestParam("token") String token){
        return myPublishService.selectClubByUid(uid,token);
    }

    @GetMapping("/selectShopList")
    @ApiOperation(value = "通过用户id查找指定用户id发布的全部景点商家",notes = "返回为ShopVO类,nums为报名人数")
    public Msg getShopByUid(@RequestParam("uid") Integer uid,
                            @RequestParam("token") String token){
        return myPublishService.selectShopByUid(uid, token);
    }
}
