package com.meiyou.service.impl;

import cn.hutool.core.date.DateTime;
import com.meiyou.mapper.CommentMapper;
import com.meiyou.pojo.Comment;
import com.meiyou.pojo.CommentExample;
import com.meiyou.pojo.User;
import com.meiyou.service.CommentService;
import com.meiyou.service.UserService;
import com.meiyou.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/23 14:16
 * @description：评论服务实现层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserService userService;

    /**
     * 发布评论
     * @param uid
     * @param aid
     * @param content
     * @return
     */
    @Override
    public Msg postComment(int uid, int aid, String content) {
        Comment comment = new Comment();
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        comment.setActivityId(aid);
        comment.setPersonId(uid);
        comment.setContent(content);
        comment.setBoolSee(false);
        comment.setBoolSee(false);
        int insert = commentMapper.insert(comment);
        if (insert == 0) {
            return Msg.fail();
        }
        return Msg.success();
    }

    /**
     * 通过动态id拉取所有评论
     * @param aid
     * @return
     */
    @Override
    public Msg listCommentByAid(int aid) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(aid);
        List<Comment> comments = commentMapper.selectByExample(example);
        if (comments == null || comments.size() == 0) {
            return Msg.fail();
        }
        for (Comment comment : comments) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            //查询这条评论的用户信息
            User user = userService.getUserById(comment.getPersonId());
            hashMap.put("header", user.getHeader());
            hashMap.put("nickname", user.getNickname());
            hashMap.put("sex", user.getSex());
            hashMap.put("content", comment.getContent());
            hashMap.put("time", comment.getCreateTime());
            //获取这条评论的所有点赞数

            hashMap.put("cid", comment.getId());
            list.add(hashMap);
        }
        Msg msg = new Msg();
        msg.setCode(100);
        msg.setMsg("拉取所有评论成功");
        msg.add("commentList", list);
        return msg;
    }
}
