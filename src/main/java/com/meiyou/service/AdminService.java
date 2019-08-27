package com.meiyou.service;

import com.meiyou.utils.Msg;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-26 21:19
 **/
public interface AdminService {

    public Msg adminLogin(String adminAccount, String adminPassword);
}