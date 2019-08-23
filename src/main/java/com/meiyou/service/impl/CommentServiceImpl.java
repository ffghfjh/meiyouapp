package com.meiyou.service.impl;

import com.meiyou.service.CommentService;
import com.meiyou.utils.Msg;
import org.springframework.stereotype.Service;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/23 14:16
 * @description：评论服务实现层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Service
public class CommentServiceImpl implements CommentService {
    /**
     * 发布评论
     *
     * @param uid
     * @param aid
     * @param content
     * @return
     */
    @Override
    public Msg postComment(int uid, int aid, String content) {
        return null;
    }

    /**
     * 通过动态id获取所有评论
     *
     * @param aid
     * @return
     */
    @Override
    public Msg listCommentByAid(int aid) {
        return null;
    }
}
