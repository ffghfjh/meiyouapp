package com.meiyou.service;

import com.meiyou.utils.Msg;

/**
 * @program: meiyou
 * @description: 手机验证码业务层
 * @author: dengshilin
 * @create: 2019-08-21 16:41
 **/
public interface SendCodeApiService {

    Msg sendRegistCode(String phone); //发送注册验证码
    Msg sendRebackPwd(String phone);  //发送找回密码验证码
}