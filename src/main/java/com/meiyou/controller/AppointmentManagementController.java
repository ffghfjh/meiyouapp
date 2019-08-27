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

    @ApiOperation(value = "约会列表", notes = "约会列表", httpMethod = "POST")
    @RequestMapping(value = "selectAppointment")
    public Msg selectAppointment() {
        return appointmentManagementService.selectAppointment();

    }
}
