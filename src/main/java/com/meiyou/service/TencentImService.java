package com.meiyou.service;

import com.meiyou.pojo.User;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-22 10:32
 **/
public interface TencentImService {

     boolean registTencent(User user); //注册腾讯通信云账号

     /**
      * 获取用户在线状态
      * @param account
      * @return
      */
     int getUserState(String account);

     /**
      * 更新用户信息
      * @param user
      * @return
      */
     boolean setUserInfo(User user);

}