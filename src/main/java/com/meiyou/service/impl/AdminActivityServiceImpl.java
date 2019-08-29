package com.meiyou.service.impl;

import com.meiyou.mapper.ActivityMapper;
import com.meiyou.mapper.ActivityReportMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.pojo.*;
import com.meiyou.service.AdminActivityService;
import com.meiyou.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 管理员获得举报信息
     *
     * @return
     */
    @Override
    public Msg listActivityReport() {
        ActivityReportExample example = new ActivityReportExample();
        example.setOrderByClause("id desc");
        //获得所有举报信息
        List<ActivityReport> activityReports = activityReportMapper.selectByExample(example);
        if (activityReports == null || activityReports.isEmpty()) {
            return Msg.fail();
        }
        //前端表格统计序号
        int count = 0;
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
            //统计当前行数
            count++;
            //举报人
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("id", count);
            hashMap.put("reporter", "["+ reporter.getId() +"]" + reporter.getNickname());
            hashMap.put("activityContent", activity1.getContent());
            hashMap.put("activityMan", "[" + user.getId() +"]");
        }
        return null;
    }
}
