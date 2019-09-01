package com.meiyou.controller;

import com.meiyou.model.LayuiTableJson;
import com.meiyou.pojo.Recharge;
import com.meiyou.service.RechargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/31 13:44
 * @description：管理员充值控制层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Api(value = "AdminRechargeController", tags = {"管理员充值控制层"})
@RequestMapping("/AdminRecharge")
@RestController
public class AdminRechargeController {

    @Autowired
    RechargeService rechargeService;

    @Autowired
    AdminController adminController;

    @ApiOperation(value = "获取所有充值记录", notes = "page为页码，limit为条数", httpMethod = "GET")
    @RequestMapping("/listRecharge")
    public LayuiTableJson listRecharge(int page, int limit, HttpServletRequest request) {
        if (adminController.authAdmin(request)) {
            return rechargeService.listRecharge(page, limit);
        }
        System.out.println("管理员未登录");
        return LayuiTableJson.fail();
    }

    @ApiOperation(value = "审核充值记录", notes = "page为页码，limit为条数，id为充值记录id，type为0审核不通过，为1审核通过", httpMethod = "GET")
    @RequestMapping("/checkPassById")
    public LayuiTableJson checkPassById(int page, int limit, int id, int type, HttpServletRequest request) {
        if (adminController.authAdmin(request)) {
            return rechargeService.checkPassById(page, limit, id, type);
        }
        System.out.println("管理员未登录");
        return LayuiTableJson.fail();
    }

}
