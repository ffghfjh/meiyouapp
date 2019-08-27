package com.meiyou.service.impl;

import com.meiyou.mapper.AppointmentMapper;
import com.meiyou.pojo.Appointment;
import com.meiyou.pojo.AppointmentExample;
import com.meiyou.service.AppointmentManagementService;
import com.meiyou.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    * @Description: 查询所有的约会订单
    * @Author: JK
    * @Date: 2019/8/27
    */
    @Override
    public Msg selectAppointment() {
        AppointmentExample appointmentExample = new AppointmentExample();
        List<Appointment> appointments = appointmentMapper.selectByExample(appointmentExample);
        Msg msg = new Msg();
        msg.add("appointments",appointments);
        return msg;
    }

    /**
    * @Description: 查询已发布的约会订单
     *              1对应约会表中的状态，
    * @Author: JK
    * @Date: 2019/8/27
    */
    @Override
    public Msg selectAppointment1() {

        return null;
    }

    /**
    * @Description: 询准备赴约的约会订单
     *              4对应约会表中的状态，
    * @Author: JK
    * @Date: 2019/8/27
    */
    @Override
    public Msg selectAppointment4() {
        return null;
    }

    /**
    * @Description: 查询赴约成功的约会订单
     *              5对应约会表中的状态，
    * @Author: JK
    * @Date: 2019/8/27
    */
    @Override
    public Msg selectAppointment5() {
        return null;
    }
}
