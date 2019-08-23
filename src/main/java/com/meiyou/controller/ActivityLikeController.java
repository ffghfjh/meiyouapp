package com.meiyou.controller;

import com.meiyou.pojo.ActivityLike;
import com.meiyou.service.ActivityLikeService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/21 21:15
 * @description：动态点赞表
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Api(value = "ActivityLikeController", tags = {"动态点赞控制层"})
@Controller
@RequestMapping(value = "ActivityLike")
public class ActivityLikeController {

    @Autowired
    ActivityLikeService activityLikeService;

    @ApiOperation(value = "动态点赞接口", notes = "type为0时取消点赞，为1时点赞", httpMethod = "POST")
    @RequestMapping(value = "/like")
    public Msg like(int aid, int uid, int type) {
        return activityLikeService.like(aid, uid, type);
    }

}

