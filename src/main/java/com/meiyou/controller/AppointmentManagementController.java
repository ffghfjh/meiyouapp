package com.meiyou.controller;

import com.meiyou.pojo.Appointment;
import com.meiyou.pojo.Tour;
import com.meiyou.service.AppointmentManagementService;
import com.meiyou.utils.LayuiDataUtil;
import com.meiyou.utils.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
    * @Description: 分页查询所有的约会
    * @Author: JK
    * @Date: 2019/8/27
    */
    @ApiOperation(value = "分页查询所有的约会", notes = "分页查询所有的约会", httpMethod = "POST")
    @RequestMapping(value = "selectAllAppointmentByPage")
    public Map<String,Object> selectAllAppointment(Integer page, Integer limit){
        Page<Appointment> page1 = new Page<>();
        List<Appointment> list = appointmentManagementService.selectAllAppointment();
        List<Appointment> appointments = appointmentManagementService.selectAllAppointmentByPage(page,limit);
        page1.setList(appointments);
        page1.setCount(list.size());
        return LayuiDataUtil.getLayuiData(page1);
    }


    /**
    * @Description: 分页查询所有的旅游
    * @Author: JK
    * @Date: 2019/8/27
    */
    @ApiOperation(value = "分页查询所有的旅游", notes = "分页查询所有的旅游", httpMethod = "POST")
    @RequestMapping(value = "selectAllTourByPage")
    public Map<String,Object> selectAllTourByPage(Integer page, Integer limit){
        Page<Tour> page1 = new Page<>();
        List<Tour> list = appointmentManagementService.selectAllTour();
        List<Tour> tours = appointmentManagementService.selectAllTourByPage(page,limit);
        page1.setList(tours);
        page1.setCount(list.size());
        return LayuiDataUtil.getLayuiData(page1);
    }

}
