package com.meiyou.controller;

import com.meiyou.pojo.Activity;
import com.meiyou.service.ActivityService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/21 13:47
 * @description：附近动态
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Api(value = "ActivityController", tags = {"动态主控制层"})
@RestController
@RequestMapping(value = "/Activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @ApiOperation(value = "删除动态", notes = "删除动态", httpMethod = "POST")
    @PostMapping(value = "/remove")
    public Msg remove(String uid, String token, int aid) {
        return activityService.remove(uid, token, aid);
    }

    @ApiOperation(value = "获取附近所有动态", notes = "获取附近所有动态", httpMethod = "POST")
    @PostMapping(value = "/listNeighborActivity")
    public Msg listNeighborActivity(int uid, double latitude, double longitude) {
        return activityService.listNeighborActivity(uid, latitude, longitude);
    }

    @ApiOperation(value = "用户发布动态", notes = "用户发布动态", httpMethod = "POST")
    @PostMapping(value = "/postActivity")
    public Msg postActivity(int uid, String token, double latitude, double longitude, String content,  MultipartFile[] files,int fileType
            ,HttpServletRequest request) {
        if(RedisUtil.authToken(String.valueOf(uid),token)){
            int i = activityService.postActivity(uid,latitude,longitude,content,files, fileType,request);
            if (i == 1) {
                return Msg.success();
            }
            return Msg.fail();
        }else {
            return Msg.noLogin();
        }
    }

    @ApiOperation(value = "获得我所有的动态", notes = "获得我所有的动态")
    @PostMapping(value = "/listMyActivity")
    public Msg listMyActivity(int uid) {
        return activityService.listUserActivityByUid(uid);
    }


    /**
     * hzy
     * 获取所有动态及其用户
     * @return
     */
    @ApiOperation(value = "管理员获取所有用户动态", notes = "管理员获取所有用户动态", httpMethod = "POST")
    @RequestMapping("/listActivity")
    public List<Activity> listActivity() {
        return activityService.listActivity();
    }


}
