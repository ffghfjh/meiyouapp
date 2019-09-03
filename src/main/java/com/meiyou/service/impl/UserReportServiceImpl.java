package com.meiyou.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meiyou.mapper.UserMapper;
import com.meiyou.mapper.UserReportMapper;
import com.meiyou.model.LayuiTableJson;
import com.meiyou.pojo.*;
import com.meiyou.service.UserReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/30 14:26
 * @description：用户举报业务实现层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Service
public class UserReportServiceImpl implements UserReportService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserReportMapper userReportMapper;


    /**
     * 通过账号屏蔽用户
     *
     * @param account
     * @return
     */
    @Override
    public LayuiTableJson hidePersonByAccount(String account) {
        return null;
    }

    /**
     * 屏蔽被举报人
     *
     * @param uid
     * @param type
     * @return
     */
    @Override
    public LayuiTableJson hideReportedPersonById(int page, int limit, int uid, int type) {
        //查找被屏蔽的用户是否存在
        User user = userMapper.selectByPrimaryKey(uid);
        if (user == null) {
            return listUserReport(page, limit);
        }
        User user1 = new User();
        user1.setId(uid);
        user1.setUpdateTime(new Date());
        //屏蔽
        if (type == 1) {
            user1.setBoolClose(true);
        }
        //不屏蔽
        if (type == 0) {
            user1.setBoolClose(false);
        }
        userMapper.updateByPrimaryKeySelective(user1);
        return listUserReport(page, limit);
    }

    /**
     * 获得所有用户举报信息
     *
     * @return
     */
    @Override
    public LayuiTableJson listUserReport(int page, int limit) {
        PageHelper.startPage(page, limit);
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        UserReportExample example = new UserReportExample();
        example.setOrderByClause("id desc");
        //获得所有举报信息
        List<UserReport> userReportList = userReportMapper.selectByExample(example);
        if (userReportList == null || userReportList.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            //举报编号
            hashMap.put("report_id", 0);
            hashMap.put("reporter", "无数据");
            hashMap.put("reported", "无数据");
            //在前端不展示的被举报人id
            hashMap.put("pid", 0);
            hashMap.put("reportedBoolClose", "无数据");
            hashMap.put("type", "无数据");
            hashMap.put("content", "无数据");
            hashMap.put("time", "无数据");
            list.add(hashMap);
            PageInfo pageInfo = new PageInfo(list);
            return LayuiTableJson.success().addCount(0).addData(pageInfo);
        }
        //遍历举报表
        for (UserReport report : userReportList) {
            //查找举报人信息
            User reporter = userMapper.selectByPrimaryKey(report.getReporterId());
            if (reporter == null) {
                continue;
            }
            //查找被举报人信息
            User reportedPerson = userMapper.selectByPrimaryKey(report.getReportedPersonId());
            if (reportedPerson == null) {
                continue;
            }
            //举报人
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            //举报编号
            hashMap.put("report_id", report.getId());
            hashMap.put("reporter", "[" + reporter.getAccount() + "] " + reporter.getNickname());
            hashMap.put("reported", "[" + reportedPerson.getAccount() + "] " + reportedPerson.getNickname());
            //在前端不展示的被举报人id
            hashMap.put("pid", reportedPerson.getId());
            hashMap.put("reportedBoolClose", reportedPerson.getBoolClose());
            hashMap.put("type", report.getType());
            hashMap.put("content", report.getContent());
            hashMap.put("time", DateUtil.formatDateTime(report.getCreateTime()));
            list.add(hashMap);
        }
        if (list.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            //举报编号
            hashMap.put("report_id", 0);
            hashMap.put("reporter", "无数据");
            hashMap.put("reported", "无数据");
            //在前端不展示的被举报人id
            hashMap.put("pid", 0);
            hashMap.put("reportedBoolClose", "无数据");
            hashMap.put("type", "无数据");
            hashMap.put("content", "无数据");
            hashMap.put("time", "无数据");
            list.add(hashMap);
            PageInfo pageInfo = new PageInfo(list);
            return LayuiTableJson.success().addCount(0).addData(pageInfo);
        }
        PageInfo pageInfo = new PageInfo(list);
        return LayuiTableJson.success().addCount(getUserReportTotolCount()).addData(pageInfo);
    }

    /**
     * 获得用户举报总条数
     *
     * @return
     */
    @Override
    public int getUserReportTotolCount() {
        int count = 0;
        //搜索用户举报表
        List<UserReport> userReportList = userReportMapper.selectByExample(null);
        if (userReportList == null) {
            return count;
        }
        for (UserReport userReport : userReportList) {
            //查找举报人是否存在
            User user = userMapper.selectByPrimaryKey(userReport.getReporterId());
            //查找被举报人是否存在
            User reported = userMapper.selectByPrimaryKey(userReport.getReporterId());
            if (user == null || reported == null) {
                continue;
            }
            count++;
        }
        return count;
    }
}
