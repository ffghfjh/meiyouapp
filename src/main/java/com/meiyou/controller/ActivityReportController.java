package com.meiyou.controller;

import com.meiyou.service.ActivityReportService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/26 13:15
 * @description：动态举报控制层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Api(value = "ActivityReportController", tags = {"动态举报控制层"})
@RestController
@RequestMapping("/ActivityReport")
public class ActivityReportController {

    @Autowired
    ActivityReportService reportService;

    @ApiOperation(value = "举报接口", notes = "aid为动态id，uid为举报人id，type为举报类型", httpMethod = "POST")
    @RequestMapping(value = "/report")
    public Msg report(int aid, int uid, String type) {
        return reportService.report(aid, uid, type);
    }

}


