package com.meiyou.controller;

import com.meiyou.service.CommentLikeService;
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
 * @date ：Created in 2019/8/23 19:11
 * @description：评论点赞控制层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Api(value = "CommentLikeController", tags = {"动态评论点赞控制层"})
@RequestMapping("/CommentLike")
@RestController
public class CommentLikeController {

    @Autowired
    CommentLikeService commentLikeService;

    @ApiOperation(value = "评论点赞接口", notes = "uid为我自己的id，不是评论人的id", httpMethod = "POST")
    @PostMapping(value = "/like")
    public Msg like(int uid, int cid, int type) {
        return commentLikeService.like(uid, cid, type);
    }

}
