package com.meiyou;

import com.meiyou.mapper.ActivityReportMapper;
import com.meiyou.pojo.ActivityReport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/26 14:28
 * @description：举报测试
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportTest {

    @Autowired
    ActivityReportMapper activityReportMapper;

    @Test
    public void test() {
        ActivityReport activityReport = new ActivityReport();
        activityReport.setActivityId(1);
        activityReport.setReporterId(1);
        activityReport.setType(1);
        activityReport.setCreateTime(new Date());
        activityReport.setUpdateTeim(new Date());
        int insert = activityReportMapper.insert(activityReport);
        if (insert == 0) {
            System.out.println("hzy---插入到数据库失败...");
        }
        System.out.println("hzy----插入到数据成功");
    }

}
