package com.meiyou.service.impl;

import com.meiyou.mapper.ActivityMapper;
import com.meiyou.mapper.ActivityReadMapper;
import com.meiyou.pojo.Activity;
import com.meiyou.pojo.ActivityRead;
import com.meiyou.pojo.ActivityReadExample;
import com.meiyou.service.ActivityReadService;
import com.meiyou.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/24 11:57
 * @description：动态阅读量业务实现层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Service
public class ActivityReadImpl implements ActivityReadService {

    @Autowired
    ActivityReadMapper activityReadMapper;


    @Autowired
    ActivityMapper activityMapper;

    /**
     * 动态添加阅读量
     *
     * @param uid
     * @param aid
     * @return
     */
    @Override
    public Msg addReadNum(int uid, int aid) {
        /*
        判断这条动态是不是我自己发的，如果是自己发的就不会增加阅读量
         */
        Msg msg = new Msg();
        Activity activity = activityMapper.selectByPrimaryKey(aid);
        if (activity == null) {
            msg.setCode(200);
            msg.setMsg("找不到动态id");
            return msg;
        }
        if (activity.getPublishId() == uid) {
            msg.setCode(100);
            msg.setMsg("我阅读了自己的动态");
            return msg;
        }
        ActivityRead read = new ActivityRead();
        read.setCreateTime(new Date());
        read.setUpdateTime(new Date());
        read.setActivityId(aid);
        read.setReaderId(uid);
        int i = activityReadMapper.insertSelective(read);
        if (i == 0) {
            msg.setCode(200);
            msg.setMsg("插入到阅读表失败");
            return msg;
        }
        ActivityReadExample readExample = new ActivityReadExample();
        ActivityReadExample.Criteria criteria = readExample.createCriteria();
        criteria.andActivityIdEqualTo(aid);
        int count = activityReadMapper.countByExample(readExample);
        Activity activity1 = new Activity();
        activity1.setId(aid);
        activity1.setUpdateTime(new Date());
        activity1.setReadNum(count);
        int i1 = activityMapper.updateByPrimaryKeySelective(activity1);
        if (i1 == 0) {
            return Msg.fail();
        }
        msg.setCode(100);
        msg.setMsg("动态阅读量加1");
        return msg;
    }
}
