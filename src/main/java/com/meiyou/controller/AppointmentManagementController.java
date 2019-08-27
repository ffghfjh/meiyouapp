package com.meiyou.controller;

import com.meiyou.service.AppointmentManagementService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: meiyou
 * @description: 约会服务后台管理控制层
 * @author: JK
 * @create: 2019-08-27 09:15
 **/
@Api(value = "约会服务后台管理控制层", tags = {"约会服务后台管理控制层"})
@RestController
public class AppointmentManagementController {
    @Autowired
    private AppointmentManagementService appointmentManagementService;

    /**
    * @Description: 查询所有的约会
    * @Author: JK
    * @Date: 2019/8/27
    */
    @ApiOperation(value = "发布约会", notes = "发布约会", httpMethod = "POST")
    @RequestMapping(value = "selectAllAppointment")
    public Msg selectAllAppointment(){
        return appointmentManagementService.selectAllAppointment();
    }

}
