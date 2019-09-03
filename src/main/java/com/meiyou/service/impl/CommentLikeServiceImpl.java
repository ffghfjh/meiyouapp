package com.meiyou.service.impl;

import com.meiyou.mapper.ActivityMapper;
import com.meiyou.mapper.CommentLikeMapper;
import com.meiyou.mapper.CommentMapper;
import com.meiyou.pojo.*;
import com.meiyou.service.CommentLikeService;
import com.meiyou.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/23 17:08
 * @description：评论点赞业务实现类
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Service
public class CommentLikeServiceImpl implements CommentLikeService {

    @Autowired
    CommentLikeMapper commentLikeMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    ActivityMapper activityMapper;


    @Override
    public Msg like(int uid, int cid, int type) {
        Msg msg = new Msg();
        //取消点赞
        if (type == 0) {
            CommentLikeExample commentLikeExample = new CommentLikeExample();
            CommentLikeExample.Criteria criteria = commentLikeExample.createCriteria();
            criteria.andCommentIdEqualTo(cid);
            criteria.andLikeIdEqualTo(uid);
            int i = commentLikeMapper.deleteByExample(commentLikeExample);
            if (i == 0) {
                return Msg.fail();
            }
            //如果删除成功，阿弥陀佛~
            CommentLikeExample example = new CommentLikeExample();
            CommentLikeExample.Criteria criteria1 = example.createCriteria();
            criteria1.andCommentIdEqualTo(cid);
            int count = commentLikeMapper.countByExample(example);
            //更新评论表的点赞数
            Comment comment = new Comment();
            comment.setId(cid);
            comment.setLikeNum(count);
            comment.setUpdateTime(new Date());
            int i1 = commentMapper.updateByPrimaryKeySelective(comment);
            if (i1 == 0) {
                return Msg.fail();
            }
            msg.setCode(100);
            msg.setMsg("取消点赞成功");
            msg.add("likeNum", count);
            return msg;
        }
        //点赞,毛主席万岁!!!
        CommentLike commentLike = new CommentLike();
        commentLike.setCreateTime(new Date());
        commentLike.setUpdateTime(new Date());
        commentLike.setCommentId(cid);
        commentLike.setLikeId(uid);
        commentLike.setBoolSee(false);
        int i = commentLikeMapper.insertSelective(commentLike);
        if (i == 0) {
            return Msg.fail();
        }
        CommentLikeExample example = new CommentLikeExample();
        CommentLikeExample.Criteria criteria = example.createCriteria();
        criteria.andCommentIdEqualTo(cid);
        int count = commentLikeMapper.countByExample(example);
        //更新评论表点赞数
        Comment comment = new Comment();
        comment.setId(cid);
        comment.setLikeNum(count);
        comment.setUpdateTime(new Date());
        int i1 = commentMapper.updateByPrimaryKeySelective(comment);
        if (i1 == 0) {
            return Msg.fail();
        }
        msg.setCode(100);
        msg.setMsg("点赞评论成功");
        msg.add("likeNum", count);
        //判断我自己是否点赞过这条评论
        return msg;
    }

    @Override
    public boolean boolLike(int uid, int cid) {
        CommentLikeExample example = new CommentLikeExample();
        CommentLikeExample.Criteria criteria = example.createCriteria();
        criteria.andLikeIdEqualTo(uid);
        criteria.andCommentIdEqualTo(cid);
        List<CommentLike> commentLikes = commentLikeMapper.selectByExample(example);
        if (commentLikes.isEmpty()) {
            return false;
        }
        return true;
    }
}
