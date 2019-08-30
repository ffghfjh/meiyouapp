package com.meiyou.service;

import java.util.Map;

/**
 * @program: meiyou
 * @description: 约会服务后台管理接口
 * @author: JK
 * @create: 2019-08-27 09:13
 **/
public interface AppointmentManagementService {

    /**
     * 分页查询所有的约会
     */
    Map<String,Object> selectAllAppointmentByPage(Integer pageNo, Integer pageSize, Integer publisherId,Integer state);

    /**
     * 分页查询所有的旅游
     * @return
     */
    Map<String,Object> selectAllTourByPage(Integer pageNo,Integer pageSize,Integer publisherId,Integer state);
}
