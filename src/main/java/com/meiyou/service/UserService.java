package com.meiyou.service;

import com.meiyou.utils.Msg;

public interface UserService {
    //支付宝登录
    public Msg alipayLogin(String auth_code);
    //手机号登录
    public Msg phoneLogin(String phone,String password);
}
