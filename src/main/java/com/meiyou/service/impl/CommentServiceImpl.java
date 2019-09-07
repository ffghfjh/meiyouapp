package com.meiyou.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.meiyou.mapper.ActivityMapper;
import com.meiyou.mapper.CommentMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.pojo.*;
import com.meiyou.service.CommentLikeService;
import com.meiyou.service.CommentService;
import com.meiyou.service.UserService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
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
    ActivityMapper activityMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentLikeService commentLikeService;


    /**
     * 删除评论，实际为屏蔽此条评论
     *
     * @param uid
     * @param token
     * @param cid
     * @return
     */
    @Override
    public Msg remove(String uid, String token, int cid) {
        //验证token
        if (RedisUtil.authToken(uid, token)) {
            Comment comment = new Comment();
            comment.setId(cid);
            comment.setUpdateTime(new Date());
            comment.setBoolClose(true);
            int i = commentMapper.updateByPrimaryKeySelective(comment);
            if (i == 1) {
                return Msg.success();
            }
            return Msg.fail();
        }
        return Msg.noLogin();
    }

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
        comment.setBoolClose(false);
        comment.setLikeNum(0);
        int insert = commentMapper.insertSelective(comment);
        if (insert == 0) {
            System.out.println("commentMapper插入失败");
            return Msg.fail();
        }
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andActivityIdEqualTo(aid);
        int count = commentMapper.countByExample(example);
        System.out.println("获取到的count：" + count);
        Activity activity = new Activity();
        activity.setId(aid);
        System.out.println("aid: " + aid);
        activity.setCommontNum(count);
        System.out.println("要存储的count" + count);
        int i = activityMapper.updateByPrimaryKeySelective(activity);
        if (i == 0) {
            System.out.println("activityMapper更新失败");
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
    public Msg listCommentByUidAid(int uid, int aid) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andActivityIdEqualTo(aid);
        List<Comment> comments = commentMapper.selectByExample(example);
        if (comments == null || comments.size() == 0) {
            return Msg.fail();
        }
        for (Comment comment : comments) {
            //判读这条评论是否超过20天
            long between = DateUtil.between(comment.getCreateTime(), new Date(), DateUnit.DAY);
            if (between > 20) {
                //娑婆诃
                commentMapper.deleteByPrimaryKey(comment.getId());
                continue;
            }
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            //查询这条评论的用户信息
            User user = userService.getUserById(comment.getPersonId());
            hashMap.put("header", user.getHeader());
            hashMap.put("nickname", user.getNickname());
            hashMap.put("sex", user.getSex());
            hashMap.put("birthday", user.getBirthday());
            hashMap.put("content", comment.getContent());
            //格式化评论时间
            hashMap.put("time", DateUtil.formatDateTime(comment.getCreateTime()));
            hashMap.put("likeNum", comment.getLikeNum());
            hashMap.put("aid", aid);
            hashMap.put("cid", comment.getId());
            //判断我自己是否点赞过这条
            boolean boolLike = commentLikeService.boolLike(uid, comment.getId());
            hashMap.put("boolLike", boolLike);
            list.add(hashMap);
        }
        Msg msg = new Msg();
        msg.setCode(100);
        msg.setMsg("拉取所有评论成功");
        msg.add("commentList", list);
        return msg;
    }

    /**
     * 获取用户对我特定动态的评论
     * @param uid
     * @return
     * @ToDo 目前没有连表User, Activity, Comment操作，需要优化
     * @Todo 待添加Redis缓存处理
     */
    @Override
    public Msg listUserCommentForMyActivity(int uid) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        Msg msg = new Msg();
        //获取我发布的所有动态
        ActivityExample example = new ActivityExample();
        example.setOrderByClause("id desc");
        ActivityExample.Criteria criteria = example.createCriteria();
        criteria.andPublishIdEqualTo(uid);
        List<Activity> activities = activityMapper.selectByExample(example);
        if (activities.isEmpty()) {
            msg.setCode(200);
            msg.setMsg("我没有发布任何动态");
            msg.add("黄朝阳", 666);
            return msg;
        }
        for (Activity activity : activities) {
            //查询对此条动态的所有评论
            CommentExample example1 = new CommentExample();
            //根据更新时间升序排序，使得每次新评论都在最前面
            example1.setOrderByClause("id desc");
            CommentExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andActivityIdEqualTo(activity.getId());
            List<Comment> comments = commentMapper.selectByExample(example1);
            if (comments.isEmpty()) {
                //如果这条动态没有评论的话，直接走下一条动态
                continue;
            }
            //遍历此条动态的所有评论
            for (Comment comment : comments) {
                //查询此条评论对应的用户
                User user = userService.getUserById(comment.getPersonId());
                //判断这个大佬是否存在
                boolean boolUser = (user == null || user.getId() == 0 || user.getNickname().equals("找不到任何用户"));
                if (boolUser) {
                    //如果这个大哥不存在，就直接走下一个评论
                    continue;
                }
                //判断这条评论是否超过20天
                long between = DateUtil.between(comment.getCreateTime(), new Date(), DateUnit.DAY);
                if (between > 20) {
                    commentMapper.deleteByPrimaryKey(comment.getId());
                    continue;
                }
                //判断这条动态是否已屏蔽
                if (comment.getBoolClose() == true) {
                    continue;
                }
                //修改评论为看过的状态
                Comment comment1 = new Comment();
                comment1.setId(comment.getId());
                comment1.setBoolSee(true);
                int i = commentMapper.updateByPrimaryKeySelective(comment1);
                if (i == 0) {
                    System.out.println("hzy----更改评论为已读状态失败...");
                }
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("header", user.getHeader());
                hashMap.put("nickname", user.getNickname());
                //装载评论id
                hashMap.put("cid", comment.getId());
                //装载评论时间
                hashMap.put("comTime", DateUtil.formatDateTime(comment.getCreateTime()));
                //装载评论内容
                hashMap.put("content", comment.getContent());
                //动态id
                hashMap.put("aid", activity.getId());
                hashMap.put("ActivityTime", DateUtil.formatDateTime(activity.getCreateTime()));
                hashMap.put("AcitivityContent", activity.getContent());
                list.add(hashMap);
            }
        }
        msg.setCode(100);
        msg.setMsg("拉取我动态下的所有评论成功！");
        msg.add("commentList", list);
        msg.add("commNum", list.size());
        return msg;
    }

    /**
     * 获得我还没读的评论数
     * @param uid
     * @return
     */
    @Override
    public Msg getNotSeenComment(int uid) {
        Msg msg = new Msg();
        //未看评论总条数
        int count = 0;
        //先获得我自己所有的动态
        ActivityExample example = new ActivityExample();
        ActivityExample.Criteria criteria = example.createCriteria();
        criteria.andPublishIdEqualTo(uid);
        List<Activity> activities = activityMapper.selectByExample(example);
        if (activities.isEmpty()) {
            msg.setCode(200);
            msg.setMsg("我没有发布过任何动态");
            msg.add("comNum", 0);
            return msg;
        }
        //遍历所有动态
        for (Activity activity : activities) {
            //查询此条动态下的所有评论
            CommentExample example1 = new CommentExample();
            CommentExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andActivityIdEqualTo(activity.getId());
            criteria1.andBoolSeeEqualTo(false);
            criteria1.andBoolCloseEqualTo(false);
            List<Comment> comments = commentMapper.selectByExample(example1);
            if (comments.isEmpty()) {
                continue;
            }
            for (Comment comment : comments) {
                User user = userService.getUserById(comment.getPersonId());
                boolean boolUser = (user == null || user.getId() == 0 || user.getNickname().equals("找不到任何用户"));
                if (boolUser) {
                    continue;
                }
                //判读这条评论是否超过20天
                long between = DateUtil.between(comment.getCreateTime(), new Date(), DateUnit.DAY);
                if (between > 20 ) {
                    commentMapper.deleteByPrimaryKey(comment.getId());
                    continue;
                }
                //判断这条评论是否被屏蔽
                if (comment.getBoolClose() == true) {
                    continue;
                }
                count++;
            }
        }
        msg.setCode(100);
        msg.setMsg("获取我动态下30天未读的评论数成功！");
        msg.add("comNum", count);
        return msg;
    }


    /**
     * 修改评论为已读状态
     *
     * @param cid
     * @return
     */
    @Override
    public int updateCommentSeen(int cid) {
        return 0;
    }

}
