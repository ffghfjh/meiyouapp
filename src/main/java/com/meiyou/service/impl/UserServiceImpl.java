package com.meiyou.service.impl;

import com.meiyou.mapper.AuthorizationMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.service.UserService;
import com.meiyou.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    AuthorizationMapper authMapper;



    @Override
    public Msg alipayLogin(String auth_code) {

        return null;
    }

    @Override
    public Msg phoneLogin(String phone, String password) {


        return null;
    }
}
