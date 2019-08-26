package com.meiyou.controller;

import com.meiyou.service.CommentService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/23 13:03
 * @description：动态的评论
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Api(value = "CommentController", tags = {"动态评论控制层"})
@RestController
@RequestMapping(value = "/Comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * hzy
     * 发布动态
     * @param aid
     * @param uid
     * @param content
     * @return
     */
    @ApiOperation(value = "发布评论", notes = "aid为动态id, uid为发布人id", httpMethod = "POST")
    @PostMapping("/postComment")
    public Msg postComment(int aid, int uid, String content) {
        return commentService.postComment(aid, uid, content);
    }

    @ApiOperation(value = "拉取所有评论", notes = "uid为用户id, aid为动态id", httpMethod = "POST")
    @PostMapping("listCommentByUidAid")
    public Msg listCommentByUidAid(int uid, int aid){
        return commentService.listCommentByUidAid(uid, aid);
    }

    @ApiOperation(value = "拉取所有对我动态下30天内的评论", notes = "uid为我自己的uid", httpMethod = "POST")
    @PostMapping("/listUserCommentForMyActivity")
    public Msg listUserCommentForMyActivity(int uid){
        return commentService.listUserCommentForMyActivity(uid);
    }

    @ApiOperation(value = "获得我动态下30天内未看的评论数", notes = "uid为我自己的uid", httpMethod = "POST")
    @PostMapping("/getNotSeenComment")
    public Msg getNotSeenComment(int uid) {
        return commentService.getNotSeenComment(uid);
    }

//    @ApiOperation(value = "删除我动态下30天之前的评论", notes = "uid为我自己的uid", httpMethod = "POST")
//    @PostMapping("/getNotSeenComment")
//    public Msg getNotSeenComment(int uid) {
//        return commentService.getNotSeenComment(uid);
//    }


}
