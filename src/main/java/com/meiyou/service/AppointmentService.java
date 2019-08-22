package com.meiyou.service;

import com.meiyou.pojo.Appointment;

/**
 * @program: meiyouapp
 * @description: 约会接口
 * @author: JK
 * @create: 2019-08-21 14:12
 **/
public interface AppointmentService {
    /**
     * 发布约会
     */
    int insert(Appointment appointment);

    /**
     * 取消发布
     */
    int deletePublish(Integer uid,Integer id);
}
