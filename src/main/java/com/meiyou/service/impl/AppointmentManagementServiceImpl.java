package com.meiyou.service.impl;

import com.meiyou.mapper.AppointmentMapper;
import com.meiyou.mapper.TourMapper;
import com.meiyou.pojo.Appointment;
import com.meiyou.pojo.AppointmentExample;
import com.meiyou.pojo.Tour;
import com.meiyou.pojo.TourExample;
import com.meiyou.service.AppointmentManagementService;
import com.meiyou.utils.LayuiDataUtil;
import com.meiyou.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    * @Description: 分页查询所有的约会，根据用户ID精准查询所有约会
    * @Author: JK
    * @Date: 2019/8/29
    */
    @Override
    public Map<String,Object> selectAllAppointmentByPublisherId(Integer pageNo, Integer pageSize, Integer publisherId) {
        Page<Appointment> page = new Page<>();
        Integer offset = pageSize * (pageNo - 1);
        AppointmentExample appointmentExample = new AppointmentExample();

        if (publisherId != null){
            appointmentExample.createCriteria().andPublisherIdEqualTo(publisherId);
            List<Appointment> list = appointmentMapper.selectByExample(appointmentExample);
            if (list.size() == 0){
                Map<String, Object> map = new HashMap<>();
                map.put("code",200);
                map.put("msg","没有该用户");
                return map;
            }
            appointmentExample.setPageNo(offset);
            appointmentExample.setPageSize(pageSize);
            List<Appointment> appointments = appointmentMapper.selectByExample(appointmentExample);
            page.setCount(list.size());
            page.setList(appointments);
            return LayuiDataUtil.getLayuiData(page);
        }
        List<Appointment> list = appointmentMapper.selectByExample(appointmentExample);
        if (list.size() == 0){
            Map<String, Object> map = new HashMap<>();
            map.put("code",200);
            map.put("msg","没有该用户");
            return map;
        }

        appointmentExample.setPageNo(offset);
        appointmentExample.setPageSize(pageSize);
        List<Appointment> appointments = appointmentMapper.selectByExample(appointmentExample);
        page.setCount(list.size());
        page.setList(appointments);
        return LayuiDataUtil.getLayuiData(page);
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
