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
    /**
     * 发布约会
     */
    Msg insert(Appointment appointment, String password, String token,double latitude,double longitude);

    /**
     * 查询我的发布
     */
    Msg selectAppointmentList(String uid,String token);

    /**
     * 取消发布
     */
    Msg deletePublish(Integer id,String token);

    /**
     * 开始报名
     */
    Msg startEnrollment(String uid,String password,Integer id,String token);


    /**
     * 查询所有报名某个约会的人员信息
     */
    Msg selectAppointAskList(String uid,Integer appointId,String token);

    /**
     * 从所有报名某个约会的人员信息中选择一个进行确认
     */
    Msg confirmUserId(Integer askerId,Integer appointId);

    /**
     * 确认到达
     */
}
