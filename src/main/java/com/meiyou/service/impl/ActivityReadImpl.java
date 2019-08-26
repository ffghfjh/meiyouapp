package com.meiyou.service.impl;

import cn.hutool.core.date.DateUtil;
import com.meiyou.mapper.ActivityMapper;
import com.meiyou.mapper.ActivityReadMapper;
import com.meiyou.pojo.*;
import com.meiyou.service.ActivityReadService;
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

    @Autowired
    UserService userService;

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

    /**
     * 谁看过我的动态
     * @param uid 为我自己的id
     * @return
     */
    @Override
    public Msg whoHasSeenMe(int uid) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        Msg msg = new Msg();
        //获得我自己的所有动态
        ActivityExample example = new ActivityExample();
        ActivityExample.Criteria criteria = example.createCriteria();
        criteria.andPublishIdEqualTo(uid);
        List<Activity> activityList = activityMapper.selectByExample(example);
        if (activityList == null || activityList.isEmpty()) {
            msg.setCode(100);
            msg.setMsg("我没有发布过任何动态");
            return msg;
        }
        for (Activity activity : activityList) {
            //遍历浏览记录
            ActivityReadExample readExample = new ActivityReadExample();
            readExample.setOrderByClause("create_time desc");
            ActivityReadExample.Criteria criteria1 = readExample.createCriteria();
            criteria1.andActivityIdEqualTo(activity.getId());
            List<ActivityRead> reads = activityReadMapper.selectByExample(readExample);
            if (reads == null || reads.isEmpty()) {
                //如果这条动态没有被任何人看过，直接走下一个动态
                continue;
            }
            //遍历阅读记录
            for (ActivityRead read : reads) {
                //如果阅读记录为自己本人，直接走下一条阅读记录
                if (read.getReaderId() == uid) {
                    continue;
                }
                //查找阅读人
                User user = userService.getUserById(read.getReaderId());
                boolean boolUser = (user == null || user.getId() == 0 || user.getNickname().equals("找不到任何用户"));
                if (boolUser) {
                    //如果用户不存在，忽略不计
                    continue;
                }
                //hashMap对象的创建放在最后
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("header", user.getHeader());
                hashMap.put("nickname", user.getNickname());
                hashMap.put("uid", user.getId());
                hashMap.put("time", DateUtil.formatDateTime(read.getCreateTime()));
                hashMap.put("activityContent", activity.getContent());
                hashMap.put("aid", activity.getId());
                list.add(hashMap);
            }
        }
        msg.setCode(100);
        msg.setMsg("获取谁看过我成功");
        msg.add("readList", list);
        msg.add("readNum", list.size());
        return msg;
    }
}
