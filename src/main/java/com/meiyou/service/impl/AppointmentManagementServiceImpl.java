package com.meiyou.service.impl;

import com.meiyou.mapper.AppointmentMapper;
import com.meiyou.mapper.TourMapper;
import com.meiyou.pojo.Appointment;
import com.meiyou.pojo.AppointmentExample;
import com.meiyou.pojo.Tour;
import com.meiyou.pojo.TourExample;
import com.meiyou.service.AppointmentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private TourMapper tourMapper;

    /**
    * @Description: 查询所有约会
    * @Author: JK
    * @Date: 2019/8/28
    */
    @Override
    public List<Appointment> selectAllAppointment() {
        return appointmentMapper.selectByExample(new AppointmentExample());
    }

    /**
    * @Description: 分页查询所有约会
    * @Author: JK
    * @Date: 2019/8/27
    */
    @Override
    public List<Appointment> selectAllAppointmentByPage(Integer pageNo,Integer pageSize) {
        AppointmentExample appointmentExample = new AppointmentExample();
        Integer offset = pageSize * (pageNo - 1);
        appointmentExample.setPageNo(offset);
        appointmentExample.setPageSize(pageSize);
        List<Appointment> appointments = appointmentMapper.selectByExample(appointmentExample);
        return appointments;
    }

    /**
    * @Description: 查询所有的旅游
    * @Author: JK
    * @Date: 2019/8/28
    */
    @Override
    public List<Tour> selectAllTour() {
        return tourMapper.selectByExample(new TourExample());
    }

    /**
    * @Description: 分页查询所有旅游
    * @Author: JK
    * @Date: 2019/8/28
    */
    @Override
    public List<Tour> selectAllTourByPage(Integer pageNo, Integer pageSize) {
        TourExample tourExample = new TourExample();
        Integer offset = pageSize * (pageNo - 1);
        tourExample.setPageNo(offset);
        tourExample.setPageSize(pageSize);
        List<Tour> tours = tourMapper.selectByExample(tourExample);
        return tours;
    }
}
