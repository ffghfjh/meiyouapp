package com.meiyou.service;

import com.meiyou.pojo.Tour;

import java.util.List;
import java.util.Map;

/**
 * @program: meiyou
 * @description: 约会服务后台管理接口
 * @author: JK
 * @create: 2019-08-27 09:13
 **/
public interface AppointmentManagementService {

    /**
     * 分页查询所有的约会，根据用户ID精准查询所有约会
     */
    Map<String,Object> selectAllAppointmentByPublisherId(Integer pageNo, Integer pageSize, Integer publisherId,Integer state);


    /**
     * 查询所有的旅游
     */
    List<Tour> selectAllTour();

    /**
     * 分页查询所有旅游
     * @return
     */
    List<Tour> selectAllTourByPage(Integer pageNo,Integer pageSize);
}
