package com.meiyou.service.impl;

import com.meiyou.mapper.AppointmentMapper;
import com.meiyou.pojo.Appointment;
import com.meiyou.service.AppointmentService;
import com.meiyou.utils.Msg;
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
    public Msg insert(Appointment appointment) {
        Msg msg = new Msg();
        int insert = appointmentMapper.insertSelective(appointment);
        if (insert == 1){
            msg.setCode(100);
            msg.setMsg("增加成功");
        }else {
            msg.setCode(200);
            msg.setMsg("增加失败");
        }
        return msg;
    }
}
