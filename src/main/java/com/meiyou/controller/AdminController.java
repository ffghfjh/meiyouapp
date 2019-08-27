package com.meiyou.controller;

import com.meiyou.service.AdminService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.ApiOperation;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-26 21:16
 **/
@RestController
public class AdminController {

    @Autowired
    AdminService adminService;
    @RequestMapping(value = "adminLogin",method = RequestMethod.POST)
    @ApiOperation("后台登录")
    public Msg adminLogin(String adminAccount, String adminPassword, HttpServletRequest request){
        Msg msg = adminService.adminLogin(adminAccount,adminPassword);
        if(msg.getCode()==100){
            request.getSession().setAttribute("admin",adminAccount);
        }
        return msg;
    }

    @RequestMapping(value = "authAdmin",method = RequestMethod.GET)
    @ApiOperation("登录校验")
    public boolean authAdmin(HttpServletRequest request){
        if(request.getSession().getAttribute("admin")!=null){
            return true;
        }
        return false;
    }
}