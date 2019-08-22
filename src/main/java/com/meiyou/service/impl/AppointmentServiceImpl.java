package com.meiyou.service.impl;

import com.meiyou.mapper.AppointmentMapper;
import com.meiyou.pojo.Appointment;
import com.meiyou.pojo.AppointmentExample;
import com.meiyou.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: meiyouapp
 * @description: 约会接口实现类
 * @author: JK
 * @create: 2019-08-21 14:12
 **/
@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentMapper appointmentMapper;
    /**
    * @Description: 发布约会
    * @Author: JK
    * @Date: 2019/8/21
    */
    @Override
    public int insert(Appointment appointment) {

        int insert = appointmentMapper.insertSelective(appointment);
        return insert;
    }

    /**
    * @Description: 取消发布约会订单
    * @Author: JK
    * @Date: 2019/8/22
    */
    @Override
    public int deletePublish(Integer uid, Integer id) {
        AppointmentExample example = new AppointmentExample();
        example.createCriteria().andIdEqualTo(id)
                .andPublisherIdEqualTo(uid);
        int i = appointmentMapper.deleteByExample(example);
        return i;
    }
}
