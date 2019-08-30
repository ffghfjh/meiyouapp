package com.meiyou.controller;

import com.meiyou.model.LayuiTableJson;
import com.meiyou.service.UserReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/30 14:22
 * @description：管理员查看用户举报
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Api(value = "AdminUserReportController", tags = {"管理员查看举报用户"})
@RestController
@RequestMapping("/AdminUserReport")
public class AdminUserReportController {

    @Autowired
    UserReportService userReportService;

    @ApiOperation(value = "获得所有用户举报", notes = "page为页码，limit为条数", httpMethod = "POST")
    @PostMapping(value = "/listUserReport")
    public LayuiTableJson listUserReport(int page, int limit) {
        return userReportService.listUserReport(page, limit);
    }

}
