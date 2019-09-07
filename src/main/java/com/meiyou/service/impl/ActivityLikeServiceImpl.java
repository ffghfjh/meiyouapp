package com.meiyou.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.meiyou.mapper.ActivityLikeMapper;
import com.meiyou.mapper.ActivityMapper;
import com.meiyou.pojo.*;
import com.meiyou.service.ActivityLikeService;
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
 * @date ：Created in 2019/8/21 21:17
 * @description：动态点赞服务层实现类
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Service
public class ActivityLikeServiceImpl implements ActivityLikeService {

    @Autowired
    ActivityLikeMapper activityLikeMapper;

    //注入动态表mapper
    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    UserService userService;

    /**
     * 删除点赞接口，只是屏蔽这条记录
     *
     * @param likeId 点赞记录id
     * @param token
     * @return
     */
    @Override
    public Msg remove(String uid, String token, int likeId) {
        //验证用户token
        if (!RedisUtil.authToken(uid, token)) {
            return Msg.noLogin();
        }
        //屏蔽这条记录
        ActivityLike activityLike = new ActivityLike();
        activityLike.setId(likeId);
        activityLike.setUpdateTime(new Date());
        activityLike.setBoolClose(true);
        int i = activityLikeMapper.updateByPrimaryKeySelective(activityLike);
        if (i == 1) {
            return Msg.success();
        }
        return Msg.fail();
    }

    //获得我所有动态下所有未读的点赞数
    @Override
    public Msg getNotSeenLikeNumForMyActvity(int uid) {
        //我未看的点赞数
        int count = 0;
        Msg msg = new Msg();
        //获得我自己的所有动态
        ActivityExample example = new ActivityExample();
        ActivityExample.Criteria criteria = example.createCriteria();
        criteria.andPublishIdEqualTo(uid);
        List<Activity> activities = activityMapper.selectByExample(example);
        if (activities == null || activities.isEmpty()) {
            msg.setCode(100);
            msg.setMsg("我还没发布过任何动态");
            msg.add("likeNum", count);
            return msg;
        }
        //遍历所有此条动态下的所有未看的点赞记录
        for (Activity activity : activities) {
            ActivityLikeExample example1 = new ActivityLikeExample();
            ActivityLikeExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andActivityIdEqualTo(activity.getId());
            criteria1.andBoolSeeEqualTo(false);
            List<ActivityLike> activityLikes = activityLikeMapper.selectByExample(example1);
            if (activityLikes == null || activities.isEmpty()) {
                //如果这条动态没有任何点赞，直接走下一条动态
                continue;
            }
            for (ActivityLike activityLike : activityLikes) {
                //判断点赞人是否还在
                User user = userService.getUserById(activityLike.getLikeId());
                boolean boolUser = (user == null || user.getId() == 0 || user.getNickname().equals("找不到任何用户"));
                if (boolUser) {
                    //如果用户不存在的话，直接走下一条点赞记录
                    continue;
                }
                //判断这条点赞记录是否超过20天
                long between = DateUtil.between(activityLike.getCreateTime(), new Date(), DateUnit.DAY);
                if (between > 20) {
                    activityLikeMapper.deleteByPrimaryKey(activityLike.getId());
                    continue;
                }
                //判断这条点赞记录是否已被屏蔽
                if (activityLike.getBoolClose() == true) {
                    continue;
                }
                count++;
            }
        }
        msg.setCode(100);
        msg.setMsg("获取我未读的动态点赞数成功");
        msg.add("likeNum", count);
        return msg;
    }

    /**
     *获取用户对我动态的点赞列表
     * @param uid 我自己的id
     * @return
     */
    @Override
    public Msg
    listUserLikeForMyActivity(int uid) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        Msg msg = new Msg();
        //获取我自己的所有动态
        ActivityExample example = new ActivityExample();
        example.setOrderByClause("id desc");
        ActivityExample.Criteria criteria = example.createCriteria();
        criteria.andPublishIdEqualTo(uid);
        List<Activity> activities = activityMapper.selectByExample(example);
        if (activities == null || activities.isEmpty()) {
            msg.setCode(100);
            msg.setMsg("我还没发布任何动态");
            msg.add("likeNum", 0);
            return msg;
        }
        //遍历我所有动态
        for (Activity activity : activities) {
            //获取对我这条动态的点赞记录
            ActivityLikeExample example1 = new ActivityLikeExample();
            example1.setOrderByClause("id desc");
            ActivityLikeExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andActivityIdEqualTo(activity.getId());
            List<ActivityLike> activityLikes = activityLikeMapper.selectByExample(example1);
            if (activityLikes == null || activityLikes.isEmpty()) {
                continue;
            }
            //遍历这条动态下的点赞记录
            for (ActivityLike activityLike : activityLikes) {
                //获取这条动态的用户是否存在
                User user = userService.getUserById(activityLike.getLikeId());
                boolean boolUser = (user == null || user.getId() == 0 || user.getNickname().equals("找不到任何用户"));
                if (boolUser) {
                    //如果点赞的用户不存在，直接走下一条点赞记录
                    continue;
                }
                //判断这条点赞记录是否超过20天
                long between = DateUtil.between(activityLike.getCreateTime(), new Date(), DateUnit.DAY);
                if (between > 20) {
                    //妙法莲华经
                    activityLikeMapper.deleteByPrimaryKey(activityLike.getId());
                    continue;
                }
                //判断这条记录是否已屏蔽
                if (activityLike.getBoolClose() == true) {
                    continue;
                }
                //更新此条点赞记录为已读状态
                ActivityLike activityLike1 = new ActivityLike();
                activityLike1.setId(activityLike.getId());
                activityLike1.setUpdateTime(new Date());
                activityLike1.setBoolSee(true);
                int i = activityLikeMapper.updateByPrimaryKeySelective(activityLike1);
                if (i == 0) {
                    System.out.println("更新点赞记录为已读状态失败");
                }
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("header", user.getHeader());
                hashMap.put("nickname", user.getNickname());
                //装载点赞时间
                hashMap.put("likeTime", DateUtil.formatDateTime(activityLike.getCreateTime()));
                hashMap.put("ActivityContent", activity.getContent());
                //转载动态时间
                hashMap.put("ActivityTime", DateUtil.formatDateTime(activity.getCreateTime()));
                hashMap.put("aid", activity.getId());
                //装载点赞记录id
                hashMap.put("likeId", activityLike.getId());
                list.add(hashMap);
            }
        }
        msg.setCode(100);
        msg.setMsg("拉取我动态的所有点赞记录成功");
        msg.add("ActivityLike", list);
        msg.add("likeNum", list.size());
        return msg;
    }

    @Override
    public Msg like(int aid, int uid, int type) {
        Msg msg = new Msg();
        if (type == 0) {
            ActivityLikeExample example = new ActivityLikeExample();
            ActivityLikeExample.Criteria criteria = example.createCriteria();
            criteria.andActivityIdEqualTo(aid);
            criteria.andLikeIdEqualTo(uid);
            int i = activityLikeMapper.deleteByExample(example);
            if (i == 0) {
                return Msg.fail();
            }
            ActivityLikeExample example01 = new ActivityLikeExample();
            ActivityLikeExample.Criteria criteria1 = example01.createCriteria();
            criteria1.andActivityIdEqualTo(aid);
            int count = activityLikeMapper.countByExample(example01);
            Activity activity = activityMapper.selectByPrimaryKey(aid);
            Activity activity1 = new Activity();
            activity1.setId(aid);
            //设置更新时间
            activity1.setUpdateTime(new Date());
            activity1.setLikeNum(count);
            int i1 = activityMapper.updateByPrimaryKeySelective(activity1);
            if (i1 == 0) {
                return Msg.fail();
            }
            msg.setCode(100);
            msg.setMsg("取消点赞成功");
            msg.add("likeNum", count);
            return msg;
        }
        ActivityLike activityLike = new ActivityLike();
        activityLike.setCreateTime(new Date());
        activityLike.setUpdateTime(new Date());
        activityLike.setActivityId(aid);
        activityLike.setLikeId(uid);
        activityLike.setBoolSee(false);
        int i = activityLikeMapper.insertSelective(activityLike);
        if (i == 0) {
            return Msg.fail();
        }
        ActivityLikeExample example02 = new ActivityLikeExample();
        ActivityLikeExample.Criteria criteria = example02.createCriteria();
        criteria.andActivityIdEqualTo(aid);
        int count =  activityLikeMapper.countByExample(example02);
        Activity activity = new Activity();
        activity.setId(aid);
        activity.setLikeNum(count);
        int i1 = activityMapper.updateByPrimaryKeySelective(activity);
        if (i1 == 0) {
            return Msg.fail();
        }
        msg.setCode(100);
        msg.setMsg("点赞成功");
        msg.add("likeNum", count);
        return msg;
    }

    @Override
    public boolean getBoolLikeByAidUid(int aid, int uid) {
        ActivityLikeExample example = new ActivityLikeExample();
        ActivityLikeExample.Criteria criteria = example.createCriteria();
        criteria.andActivityIdEqualTo(aid);
        criteria.andLikeIdEqualTo(uid);
        List<ActivityLike> activityLikes = activityLikeMapper.selectByExample(example);
        if (activityLikes == null || activityLikes.size() ==0) {
            return false;
        }
        return true;
    }


}
