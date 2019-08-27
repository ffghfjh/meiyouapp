package com.meiyou.service.impl;

import com.meiyou.mapper.AppointmentMapper;
import com.meiyou.pojo.Appointment;
import com.meiyou.pojo.AppointmentExample;
import com.meiyou.service.AppointmentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: meiyou
 * @description: 约会服务后台管理业务层
 * @author: JK
 * @create: 2019-08-27 09:13
 **/
@Service
public class AppointmentManagementServiceImpl implements AppointmentManagementService {
    @Autowired
    private AppointmentMapper appointmentMapper;

    /**
    * @Description: 查询所有约会
    * @Author: JK
    * @Date: 2019/8/27
    */
    @Override
    public List<Appointment> selectAllAppointment() {
        List<Appointment> appointments = appointmentMapper.selectByExample(new AppointmentExample());
        return appointments;
    }
}
