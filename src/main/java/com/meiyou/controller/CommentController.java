package com.meiyou.controller;

import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/23 13:03
 * @description：动态的评论
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Api(value = "评论控制层", tags = {"评论Controller"})
@RestController
@RequestMapping(value = "/Comment")
public class CommentController {

    /**
     * hzy
     * 发布动态
     * @param aid
     * @param uid
     * @param content
     * @return
     */
    public Msg postComment(int aid, int uid, String content) {
        return null;
    }

}
