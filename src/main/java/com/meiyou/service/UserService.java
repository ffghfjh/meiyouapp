package com.meiyou.service;

import com.meiyou.utils.Msg;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    //支付宝登录
    public Msg alipayLogin(String auth_code);
    //手机号登录
    public Msg phoneLogin(String phone,String password);
    //用户注册
    public Msg userRegist(String code, String phone, String password,  String nickname, String birthday, boolean sex, String qianming, MultipartFile img, HttpServletRequest req);

}
