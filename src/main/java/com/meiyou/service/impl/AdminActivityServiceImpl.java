package com.meiyou.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meiyou.mapper.ActivityMapper;
import com.meiyou.mapper.ActivityReportMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.model.LayuiTableJson;
import com.meiyou.pojo.*;
import com.meiyou.service.AdminActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/29 14:50
 * @description：管理员动态业务实现层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Service
public class AdminActivityServiceImpl implements AdminActivityService {

    @Autowired
    ActivityReportMapper activityReportMapper;

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 通过动态id不屏蔽动态
     *
     * @param aid
     * @return
     */
    @Override
    public LayuiTableJson noHideActvityById(int page, int limit, int aid) {
        Activity activity = new Activity();
        activity.setId(aid);
        activity.setUpdateTime(new Date());
        activity.setBoolClose(false);
        int i = activityMapper.updateByPrimaryKeySelective(activity);
        return listActivityReport(page, limit);
    }

    /**
     * 通过用户id不屏蔽用户
     *
     * @param uid
     * @return
     */
    @Override
    public LayuiTableJson noHideUserById(int page, int limit, int uid) {
        User user = new User();
        user.setId(uid);
        user.setUpdateTime(new Date());
        user.setBoolClose(false);
        userMapper.updateByPrimaryKeySelective(user);
        return listActivityReport(page, limit);
    }

    /**
     * 通过动态id屏蔽动态
     *
     * @param aid
     * @return
     */
    @Override
    public LayuiTableJson hideActivityById(int page, int limit, int aid) {
        Activity activity = new Activity();
        activity.setId(aid);
        activity.setUpdateTime(new Date());
        activity.setBoolClose(true);
        activityMapper.updateByPrimaryKeySelective(activity);
        return listActivityReport(page, limit);
    }

    /**
     * 通过用户id屏蔽用户
     *
     * @param uid
     * @return
     */
    @Override
    public LayuiTableJson hideUserById(int page, int limit, int uid) {
        User user = new User();
        user.setId(uid);
        user.setUpdateTime(new Date());
        user.setBoolClose(true);
        userMapper.updateByPrimaryKeySelective(user);
        return listActivityReport(page, limit);

    }

    /**
     * 管理员获得举报信息
     *
     * @return
     */
    @Override
    public LayuiTableJson listActivityReport(int page, int limit) {
        PageHelper.startPage(page, limit);//20为每页的大小
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        ActivityReportExample example = new ActivityReportExample();
        example.setOrderByClause("id desc");
        //获得所有举报信息
        List<ActivityReport> activityReports = activityReportMapper.selectByExample(example);
        if (activityReports == null || activityReports.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("id", 0);
            hashMap.put("reporter", "无数据");
            hashMap.put("activityContent", "无数据");
            hashMap.put("activityId", 0);
            hashMap.put("activityMan", 0);
            hashMap.put("userBoolHide", "无数据");
            hashMap.put("activityManId", 0);
            hashMap.put("activityBoolHide", "无数据");
            hashMap.put("type", "无数据");
            hashMap.put("time", "无数据");
            hashMap.put("activityStatus", "无数据");
            list.add(hashMap);
            PageInfo pageInfo = new PageInfo(list);
            return LayuiTableJson.success().addCount(0).addData(pageInfo);
        }
        //遍历举报表
        for (ActivityReport report : activityReports) {
            //查找举报人信息
            User reporter = userMapper.selectByPrimaryKey(report.getReporterId());
            if (reporter == null) {
                continue;
            }
            //查找此条被举报的动态
            Activity activity1 = activityMapper.selectByPrimaryKey(report.getActivityId());
            if (activity1 == null) {
                continue;
            }
            //查找这条动态的主人
            User user = userMapper.selectByPrimaryKey(activity1.getPublishId());
            if (user == null) {
                continue;
            }
            //举报人
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("id", report.getId());
            hashMap.put("reporter", "["+ reporter.getId() +"]" + reporter.getNickname());
            hashMap.put("activityContent", activity1.getContent());
            hashMap.put("activityId", activity1.getId());
            hashMap.put("activityMan", "[" + user.getId() +"]" + user.getNickname());
            hashMap.put("userBoolHide", user.getBoolClose());
            hashMap.put("activityManId", user.getId());
            hashMap.put("activityBoolHide", activity1.getBoolClose());
            hashMap.put("type", report.getType());
            hashMap.put("time", DateUtil.formatDateTime(report.getCreateTime()));
            hashMap.put("activityStatus", activity1.getBoolClose());
            list.add(hashMap);
        }
        if (list.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("id", 0);
            hashMap.put("reporter", "无数据");
            hashMap.put("activityContent", "无数据");
            hashMap.put("activityId", 0);
            hashMap.put("activityMan", 0);
            hashMap.put("userBoolHide", "无数据");
            hashMap.put("activityManId", 0);
            hashMap.put("activityBoolHide", "无数据");
            hashMap.put("type", "无数据");
            hashMap.put("time", "无数据");
            hashMap.put("activityStatus", "无数据");
            list.add(hashMap);
            PageInfo pageInfo = new PageInfo(list);
            return LayuiTableJson.success().addCount(0).addData(pageInfo);
        }
        PageInfo pageInfo = new PageInfo(list);
        return LayuiTableJson.success().addCount(getActivityReportTotalCount()).addData(pageInfo);
    }

    /**
     * 获得所有动态的总行数
     *
     * @return
     */
    @Override
    public int getActivityReportTotalCount() {
        int count = 0;
        List<ActivityReport> activityReports = activityReportMapper.selectByExample(null);
        if (activityReports == null || activityReports.isEmpty()) {
            return count;
        }
        //遍历举报表
        for (ActivityReport report : activityReports) {
            //查找举报人信息
            User reporter = userMapper.selectByPrimaryKey(report.getReporterId());
            if (reporter == null) {
                continue;
            }
            //查找此条被举报的动态
            Activity activity1 = activityMapper.selectByPrimaryKey(report.getActivityId());
            if (activity1 == null) {
                continue;
            }
            //查找这条动态的主人
            User user = userMapper.selectByPrimaryKey(activity1.getPublishId());
            if (user == null) {
                continue;
            }
           count++;
        }
        return count;
    }
}
