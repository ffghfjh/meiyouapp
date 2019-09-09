package com.meiyou.controller;

import com.meiyou.model.LayuiTableJson;
import com.meiyou.service.AdminActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/29 13:58
 * @description：管理员动态控制层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Api(value = "AdminActivityController", tags = {"动态举报（管理员）控制层"})
@RequestMapping("/AdminActivity")
@RestController
public class
AdminActivityController {

    @Autowired
    AdminActivityService adminActivityService;

    @Autowired
    AdminController adminController;

    @RequestMapping("/noHideActvityById")
    @ApiOperation(value = "通过动态id不屏蔽动态", notes = "aid为动态id", httpMethod = "POST")
    public LayuiTableJson noHideActvityById(int page, int limit, int aid, HttpServletRequest request) {
        if (adminController.authAdmin(request)) {
            return adminActivityService.noHideActvityById(page, limit, aid);
        }
        System.out.println("管理员没有登录...");
        return LayuiTableJson.fail();
    }

    @RequestMapping("/noHideUserById")
    @ApiOperation(value = "通过用户id不屏蔽用户", notes = "uid为用户id", httpMethod = "POST")
    public LayuiTableJson noHideUserById(int page, int limit, int uid, HttpServletRequest request) {
        if (adminController.authAdmin(request)) {
            return adminActivityService.noHideUserById(page, limit, uid);
        }
        System.out.println("管理员没有登录...");
        return LayuiTableJson.fail();

    }

    @RequestMapping("/hideUserById")
    @ApiOperation(value = "通过用户id屏蔽用户", notes = "uid为用户id", httpMethod = "POST")
    public LayuiTableJson hideUserById(int page, int limit, int uid, HttpServletRequest request) {
        if (adminController.authAdmin(request)) {
            return adminActivityService.hideUserById(page, limit, uid);
        }
        System.out.println("管理员没有登录...");
        return LayuiTableJson.fail();
    }

    @RequestMapping("/hideActivityById")
    @ApiOperation(value = "通过动态id屏蔽动态", notes = "aid为动态id", httpMethod = "POST")
    public LayuiTableJson hideActivityById(int page, int limit, int aid, HttpServletRequest request) {
        if (adminController.authAdmin(request)) {
            return adminActivityService.hideActivityById(page, limit, aid);
        }
        System.out.println("管理员没有登录...");
        return LayuiTableJson.fail();

    }

    @RequestMapping("/listActivityReport")
    @ApiOperation(value = "管理员获得所有举报", notes = "page为第几页，limit为条数", httpMethod = "POST")
    public LayuiTableJson listActivityReport(@RequestParam(value = "page") int page,
                                             @RequestParam(value = "limit") int limit, HttpServletRequest request) {
        if (adminController.authAdmin(request)) {
            return adminActivityService.listActivityReport(page, limit);
        }
        System.out.println("管理员没有登录...");
        return LayuiTableJson.fail();
    }

}
