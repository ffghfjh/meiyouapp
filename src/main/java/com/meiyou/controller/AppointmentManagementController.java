package com.meiyou.controller;

import com.meiyou.service.AppointmentManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: meiyou
 * @description: 约会服务后台管理控制层
 * @author: JK
 * @create: 2019-08-27 09:15
 **/
@Api(value = "约会服务后台管理控制层", tags = {"约会服务后台管理控制层"})
@RequestMapping(value = "/AppointmentManagement")
@RestController
public class AppointmentManagementController {
    @Autowired
    private AppointmentManagementService appointmentManagementService;

    @Autowired
    AdminController adminController;

    /**
    * @Description: 分页查询所有的约会
    * @Author: JK
    * @Date: 2019/8/29
    */
    @ApiOperation(value = "分页查询所有的约会", notes = "分页查询所有的约会", httpMethod = "POST")
    @RequestMapping(value = "/selectAllAppointmentByPage")
    public Map<String,Object> selectAllAppointmentByPage(Integer page, Integer limit,
                                                         Integer publisherId, Integer state
            , HttpServletRequest request){
        if (adminController.authAdmin(request)) {
            return  appointmentManagementService.selectAllAppointmentByPage(page, limit, publisherId,state);
        }
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("admin", "管理员未登录");
        return hashMap;
    }


    /**
    * @Description: 分页查询所有的旅游
    * @Author: JK
    * @Date: 2019/8/27
    */
    @ApiOperation(value = "分页查询所有的旅游", notes = "分页查询所有的旅游", httpMethod = "POST")
    @RequestMapping(value = "/selectAllTourByPage")
    public Map<String,Object> selectAllTourByPage(Integer page, Integer limit
            ,Integer publisherId,Integer state
            , HttpServletRequest request){
        if (adminController.authAdmin(request)) {
            return appointmentManagementService.selectAllTourByPage(page,limit,publisherId,state);
        }
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("hzy", "管理员未登录");
        return hashMap;
    }

}
