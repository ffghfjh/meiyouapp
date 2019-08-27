package com.meiyou.service.impl;

import com.meiyou.mapper.AdminMapper;
import com.meiyou.pojo.AdminExample;
import com.meiyou.service.AdminService;
import com.meiyou.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-26 21:22
 **/
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Override
    public Msg adminLogin(String adminAccount, String adminPassword) {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andAdminAccountEqualTo(adminAccount);
        criteria.andAdminPasswordEqualTo(adminPassword);
        if(adminMapper.selectByExample(example).size()>0){
            return Msg.success();
        }
        return Msg.fail();
    }
}