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
     * 取消发布
     */
    Msg deleteAppointmentPublish(Integer id,String token);

    /**
     * 开始报名
     */
    Msg appointmentAsk(String uid,String password,Integer id,String token);

    /**
     * 取消报名
     */
    Msg endAppointmentAsk(String uid,Integer id,String token);

    /**
     * 查询所有报名某个约会的人员信息
     */
    Msg selectAppointAskList(String uid,Integer appointId,String token);

    /**
     * 从所有报名某个约会的人员信息中选择一个进行确认，
     * 没有被选中的人退还报名金
     */
    Msg confirmAppointmentUserId(String uid,Integer askerId,Integer appointId,String token);

    /**
     * 对方取消赴约，重新发布，不退还报名金
     */
    Msg endAppointment(String uid,Integer id,String token);

    /**
     * 自己重新发布，退还报名金
     */
    Msg againReleaseAppointment(String uid,Integer id,String token);

    /**
     * 报名人确认赴约
     */
    Msg confirmAppointment(String uid,Integer id,String token);

    /**
     * 确认报名人已到达
     */
    Msg confirmAppointmentArrive(String uid,Integer id,String token);

    /**
     * 查看热门约会
     */
    Msg selectHotAppointment(String uid,String token,double latitude, double longitude);

    /**
     * 查询报名约会的全部人员
     */
    Msg selectAllAppointmentById(Integer uid,String token,Integer id);

    /**
     * 删除我的发布中约会已完成和已取消的记录
     */
    Msg delectMyPublishAppointmentRecord(Integer uid,String token,Integer id);

    /**
     * 删除我的报名中约会已完成和已取消的记录
     */
    Msg delectMyAskAppointmentRecord(Integer uid,String token,Integer id);

}
