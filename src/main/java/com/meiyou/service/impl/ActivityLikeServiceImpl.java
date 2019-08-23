package com.meiyou.service.impl;

import com.meiyou.mapper.ActivityLikeMapper;
import com.meiyou.mapper.ActivityMapper;
import com.meiyou.pojo.ActivityExample;
import com.meiyou.pojo.ActivityLike;
import com.meiyou.pojo.ActivityLikeExample;
import com.meiyou.service.ActivityLikeService;
import com.meiyou.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

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

    @Override
    public int like(int aid, int uid, int type) {
        if (type == 0) {
            ActivityLikeExample example = new ActivityLikeExample();
            ActivityLikeExample.Criteria criteria = example.createCriteria();
            criteria.andActivityIdEqualTo(aid);
            criteria.andLikeIdEqualTo(uid);
            int i = activityLikeMapper.deleteByExample(example);
            if (i == 0) {
                return 0;
            }
            ActivityLikeExample example01 = new ActivityLikeExample();
            ActivityLikeExample.Criteria criteria1 = example01.createCriteria();
            criteria1.andActivityIdEqualTo(aid);
            return activityLikeMapper.countByExample(example01);
        }
        ActivityLike activityLike = new ActivityLike();
        activityLike.setCreateTime(new Date());
        activityLike.setUpdateTime(new Date());
        activityLike.setActivityId(aid);
        activityLike.setLikeId(uid);
        activityLike.setBoolSee(false);
        int i = activityLikeMapper.insertSelective(activityLike);
        if (i == 0) {
            return 0;
        }
        ActivityLikeExample example02 = new ActivityLikeExample();
        ActivityLikeExample.Criteria criteria = example02.createCriteria();
        criteria.andActivityIdEqualTo(aid);
        return activityLikeMapper.countByExample(example02);
    }

    @Override
    public int removeLike(int aid, int uid) {
        ActivityLikeExample example = new ActivityLikeExample();
        ActivityLikeExample.Criteria criteria = example.createCriteria();
        criteria.andActivityIdEqualTo(aid);
        criteria.andLikeIdEqualTo(uid);
        return activityLikeMapper.deleteByExample(example);
    }

}
