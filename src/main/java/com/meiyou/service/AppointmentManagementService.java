package com.meiyou.service;

import com.meiyou.utils.Msg;

/**
 * @program: meiyou
 * @description: 约会服务后台管理接口
 * @author: JK
 * @create: 2019-08-27 09:13
 **/
public interface AppointmentManagementService {

    /**
     * 查询所有的约会订单
     */
    Msg selectAppointment();

    /**
     * 查询已发布的约会订单，1对应约会表中的状态，
     * @return
     */
    Msg selectAppointment1();

    /**
     * 查询准备赴约的约会订单，4对应约会表中的状态，
     * @return
     */
    Msg selectAppointment4();

    /**
     * 查询赴约成功的约会订单，5对应约会表中的状态，
     * @return
     */
    Msg selectAppointment5();


}
