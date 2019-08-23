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
    @Override
    public Msg postComment(int uid, int aid, String content) {
        return null;
    }

    @Override
    public Msg listCommentByAid(int aid) {
        return null;
    }
}
