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

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    AdminController adminController;

    @ApiOperation(value = "获得所有用户举报", notes = "page为页码，limit为条数", httpMethod = "POST")
    @RequestMapping(value = "/listUserReport") //必须以Get方式获取，如果用Post方式就获取不到
    public LayuiTableJson listUserReport(int page, int limit, HttpServletRequest request) {
        if (adminController.authAdmin(request)) {
            return userReportService.listUserReport(page, limit);
        }
        System.out.println("管理员没有登录...");
        return LayuiTableJson.fail();
    }

    @ApiOperation(value = "获得所有用户举报", notes = "page为页码，limit为条数, uid为被举报的人, type=0不屏蔽，type=1屏蔽", httpMethod = "POST")
    @RequestMapping(value = "/hideReportedPersonById") //必须以Get方式获取，如果用Post方式就获取不到
    public LayuiTableJson hideReportedPersonById(int page, int limit, int uid, int type, HttpServletRequest request) {
        if (adminController.authAdmin(request)) {
            return userReportService.hideReportedPersonById(page, limit, uid, type);
        }
        System.out.println("管理员没有登录...");
        return LayuiTableJson.fail();
    }

}
