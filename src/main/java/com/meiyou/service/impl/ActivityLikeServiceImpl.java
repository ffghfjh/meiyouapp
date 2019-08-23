package com.meiyou.service.impl;

import com.meiyou.mapper.ActivityLikeMapper;
import com.meiyou.mapper.ActivityMapper;
import com.meiyou.pojo.Activity;
import com.meiyou.pojo.ActivityExample;
import com.meiyou.pojo.ActivityLike;
import com.meiyou.pojo.ActivityLikeExample;
import com.meiyou.service.ActivityLikeService;
import com.meiyou.service.ActivityService;
import com.meiyou.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            //在activity表中点赞数减1
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
