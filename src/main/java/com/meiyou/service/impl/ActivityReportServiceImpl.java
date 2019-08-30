package com.meiyou.service.impl;

import com.meiyou.mapper.ActivityReportMapper;
import com.meiyou.pojo.ActivityReport;
import com.meiyou.service.ActivityReportService;
import com.meiyou.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/26 13:20
 * @description：动态举报实现层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Service
public class ActivityReportServiceImpl implements ActivityReportService {

    @Autowired
    ActivityReportMapper reportMapper;

    /**
     * 动态举报
     * @param aid  动态id
     * @param uid  举报人id
     * @param type 举报类型
     * @return
     */
    @Override
    public Msg report(int aid, int uid, String type) {
        ActivityReport activityReport = new ActivityReport();
        activityReport.setActivityId(aid);
        activityReport.setReporterId(uid);
        activityReport.setType(type);
        activityReport.setCreateTime(new Date());
        activityReport.setUpdateTeim(new Date());
        int insert = reportMapper.insert(activityReport);
        if (insert == 0) {
            return Msg.fail();
        }
        return Msg.success();
    }
}
