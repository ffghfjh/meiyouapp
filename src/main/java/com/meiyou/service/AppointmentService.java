package com.meiyou.service;

import com.meiyou.pojo.Appointment;
import com.meiyou.utils.Msg;

/**
 * @program: meiyouapp
 * @description: 约会接口
 * @author: JK
 * @create: 2019-08-21 14:12
 **/
public interface AppointmentService {
    Msg insert(Appointment appointment);
}
