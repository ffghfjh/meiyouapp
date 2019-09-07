package com.meiyou.controller;

import com.meiyou.service.ActivityLikeService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/21 21:15
 * @description：动态点赞表
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Api(value = "ActivityLikeController", tags = {"动态点赞控制层"})
@RestController
@RequestMapping(value = "ActivityLike")
public class ActivityLikeController {

    @Autowired
    ActivityLikeService activityLikeService;

    @ApiOperation(value = "删除点赞接口", notes = "uid为用户id,而likeId是点赞记录的id", httpMethod = "POST")
    @RequestMapping(value = "/remove")
    public Msg remove(String uid, String token, int likeId) {
        return activityLikeService.remove(uid, token, likeId);
    }

    @ApiOperation(value = "动态点赞接口", notes = "type为0时取消点赞，为1时点赞", httpMethod = "POST")
    @RequestMapping(value = "/like")
    public Msg like(int aid, int uid, int type) {
        return activityLikeService.like(aid, uid, type);
    }

    @ApiOperation(value = "获取用户对我动态的点赞列表", notes = "uid为我自己的id", httpMethod = "POST")
    @RequestMapping(value = "/listUserLikeForMyActivity")
    public Msg listUserLikeForMyActivity(int uid) {
        return activityLikeService.listUserLikeForMyActivity(uid);
    }

    @ApiOperation(value = "获得我动态下的未读点赞数", notes = "uid为我自己的id", httpMethod = "POST")
    @RequestMapping(value = "/getNotSeenLikeNumForMyActvity")
    public Msg getNotSeenLikeNumForMyActvity(int uid) {
        return activityLikeService.getNotSeenLikeNumForMyActvity(uid);
    }

}

