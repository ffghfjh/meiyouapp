package com.meiyou.service.impl;

import com.meiyou.mapper.ActivityMapper;
import com.meiyou.pojo.Activity;
import com.meiyou.pojo.ActivityExample;
import com.meiyou.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author     ：huangzhaoyang
 * @date       ：Created in 2019/8/21 14:19
 * @description：附近动态服务层接口实现
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Override
    public int postActivity(Activity activity) {
        return 0;
    }

    @Override
    public int removeActivity(int aid) {
        return 0;
    }

    @Override
    public int removeAllActivityByUid() {
        return 0;
    }

    @Override
    public int updateActivity(Activity activity) {
        return 0;
    }

    @Override
    public Activity getActivityByAid(int aid) {
        return null;
    }

    @Override
    public List<Activity> listActivityByUid(int uid) {
        return null;
    }

    @Override
    public List<Activity> listActivity() {
        ActivityExample example = new ActivityExample();
        ActivityExample.Criteria criteria = example.createCriteria();


        return null;
    }

    @Override
    public List<Activity> listActivityBy(float longitude, float latitude) {
        return null;
    }
}
