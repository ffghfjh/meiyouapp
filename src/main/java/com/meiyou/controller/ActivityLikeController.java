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
@Api(value = "点赞控制层", tags = {"点赞控制层"})
@Controller
@RequestMapping(value = "ActivityLike")
public class ActivityLikeController {

    @Autowired
    ActivityLikeService activityLikeService;

    @ApiOperation(value = "点赞", notes = "点赞", httpMethod = "POST")
    @RequestMapping(value = "like")
    @ResponseBody
    public Msg like(int aid, int uid) {
        int like = activityLikeService.like(aid, uid);
        if (like == 1) {
            return Msg.success();
        }
        return Msg.fail();
    }

    @ApiOperation(value = "点赞", notes = "点赞", httpMethod = "POST")
    @RequestMapping(value = "removeLike")
    @ResponseBody
    public Msg removeLike(int aid, int uid) {
        int like = activityLikeService.removeLike(aid, uid);
        if (like == 1) {
            return Msg.success();
        }
        return Msg.fail();
    }
}

