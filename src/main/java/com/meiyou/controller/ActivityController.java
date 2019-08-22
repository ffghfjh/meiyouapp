package com.meiyou.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.meiyou.mapper.ActivityMapper;
import com.meiyou.pojo.Activity;
import com.meiyou.service.ActivityService;
import com.meiyou.utils.FileUploadUtil;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/21 13:47
 * @description：附近动态
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Api(value = "动态控制层", tags = {"动态Controller"})
@RestController
@RequestMapping(value = "/Activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @Autowired
    ActivityMapper activityMapper;

    @ApiOperation(value = "用户发布动态", notes = "用户发布动态", httpMethod = "POST")
    @PostMapping(value = "/postActivity")
    public Msg postActivity(int uid, double latitude, double longitude, String content,  MultipartFile[] files
            ,HttpServletRequest request) {
        int i = activityService.postActivity(uid,latitude,longitude,content,files, request);
        if (i == 1) {
            return Msg.success();
        }
        return Msg.fail();
    }

    @ApiOperation(value = "我的动态", notes = "我的动态")
    @GetMapping(value = "/listMyActivity")
    public HashMap<String,String> listMyActivity(int uid, double latitude, double longitude, String content, MultipartFile[] files
            , HttpServletRequest request) {
        int i = activityService.postActivity(uid,latitude,longitude,content,files, request);
        if (i == 1) {

        }
        return null;
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
