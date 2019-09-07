package com.meiyou.service;

import com.meiyou.pojo.User;
import com.meiyou.utils.Msg;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 个人用户业务层接口
 * @author: Mr.Z
 * @create: 2019-08-30 20:10
 **/
public interface OwnService {

    Msg changeInfo(User user);

    Msg changeHeader(Integer uid, MultipartFile img, HttpServletRequest req);

    Msg changeBackGroudPicture(Integer uid, MultipartFile img, HttpServletRequest req);

    Msg changePassword(Integer uid,String oldPassword,String newPassword);

    Msg changePayPassword(Integer uid,String oldPassword,String newPassword);

    Msg selectByUid(Integer uid);
}
