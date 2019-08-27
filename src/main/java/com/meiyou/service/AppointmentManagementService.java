package com.meiyou.service;

import com.meiyou.pojo.Appointment;

import java.util.List;

/**
 * @program: meiyou
 * @description: 约会服务后台管理接口
 * @author: JK
 * @create: 2019-08-27 09:13
 **/
public interface AppointmentManagementService {

    /**
     * 查询所有约会
     * @return
     */
    List<Appointment> selectAllAppointment();
}
