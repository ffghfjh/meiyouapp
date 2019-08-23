package com.meiyou.service;

import com.meiyou.utils.Msg;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/23 13:30
 * @description：评论服务层接口层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
public interface CommentService {

    /**
     * 发布评论
     * @param uid
     * @param aid
     * @param content
     * @return
     */
    Msg postComment(int uid, int aid, String content);

    /**
     * 通过动态id获取所有评论
     * @param aid
     * @return
     */
    Msg listCommentByUidAid(int uid, int aid);

}
