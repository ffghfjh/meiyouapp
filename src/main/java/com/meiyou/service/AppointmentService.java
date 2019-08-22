package com.meiyou.service;

import com.meiyou.pojo.AppointAsk;
import com.meiyou.pojo.Appointment;
import com.meiyou.utils.Msg;

import java.util.List;

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
    Msg insert(Appointment appointment, String password, String token);

    /**
     * 查询我的发布
     */
    List<Appointment> selectAppointmentList();

    /**
     * 取消发布
     */
    Msg deletePublish(Integer id,String token);

    /**
     * 开始报名
     */
    int startEnrollment(Integer uid,Integer id);


    /**
     * 查询所有报名某个约会的人员信息
     */
    List<AppointAsk> selectAppointAskList(Integer appointId);

    /**
     * 从所有报名某个约会的人员信息中选择一个进行确认
     */
    int confirmUserId(Integer askerId,Integer appointId);

    /**
     * 确认到达
     */
}
