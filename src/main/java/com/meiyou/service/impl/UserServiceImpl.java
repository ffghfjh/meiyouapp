package com.meiyou.service.impl;

import com.meiyou.service.UserService;
import com.meiyou.utils.Msg;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {




    @Override
    public Msg alipayLogin(String auth_code) {

        return null;
    }

    @Override
    public Msg phoneLogin(String phone, String password) {


        return null;
    }
}
