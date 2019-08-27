package com.meiyou.service.impl;

import com.meiyou.mapper.ClubMapper;
import com.meiyou.pojo.Club;
import com.meiyou.pojo.ClubExample;
import com.meiyou.service.AppointmentManagementService;
import com.meiyou.utils.Msg;
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
    ClubMapper clubMapper;

    @Override
    public Msg selectClub() {
        Msg msg = new Msg();

        ClubExample clubExample = new ClubExample();
        clubExample.createCriteria();
        List<Club> clubs = clubMapper.selectByExample(clubExample);

        msg.setMsg("成功");
        msg.setCode(100);
        msg.add("clubs",clubs);
        return msg;
    }
}
