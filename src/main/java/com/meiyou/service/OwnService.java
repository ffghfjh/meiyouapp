package com.meiyou.service;

import com.meiyou.pojo.User;
import com.meiyou.utils.Msg;

/**
 * @description: 个人用户业务层接口
 * @author: Mr.Z
 * @create: 2019-08-30 20:10
 **/
public interface OwnService {

    Msg changeInfo(User user);

    Msg changePassword(Integer uid,String newPassword);

    Msg changePayPassword(Integer uid,String newPassword);

    Msg selectByUid(Integer uid);
}
