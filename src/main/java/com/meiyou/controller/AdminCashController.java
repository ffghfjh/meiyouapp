package com.meiyou.controller;

import com.meiyou.model.LayuiTableJson;
import com.meiyou.service.AdminCashService;
import com.meiyou.service.RechargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/31 15:38
 * @description：管理提现控制层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 *
 */
@Api(value = "AdminCashController", tags = {"管理提现控制层"})
@RequestMapping("/AdminCash")
@RestController
public class AdminCashController {

    @Autowired
    AdminCashService adminCashService;

    @Autowired
    AdminController adminController;

    @ApiOperation(value = "获取所有提现", notes = "page为页码，limit为条数", httpMethod = "GET")
    @RequestMapping("/listCash")
    public LayuiTableJson listCash(int page, int limit, HttpServletRequest request) {
        if (adminController.authAdmin(request)) {
            return adminCashService.listCash(page, limit);
        }
        System.out.println("hzy----管理员未登录");
        return LayuiTableJson.fail();
    }

    @ApiOperation(value = "审核提现", notes = "page为页码，limit为条数，id为充值记录id，type为0审核不通过，为1审核通过", httpMethod = "GET")
    @RequestMapping("/checkPassCash")
    public LayuiTableJson checkPassCash(int page, int limit, int id, int type, HttpServletRequest request) {
        if (adminController.authAdmin(request)) {
            return adminCashService.checkPassCash(page, limit, id, type);
        }
        System.out.println("hzy----管理员未登录");
        return LayuiTableJson.fail();
    }

}
