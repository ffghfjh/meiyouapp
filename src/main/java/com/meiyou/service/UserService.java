package com.meiyou.service;

import com.meiyou.pojo.User;
import com.meiyou.utils.Msg;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    //支付宝登录
    public Msg alipayLogin(String auth_code);
    //手机号登录
    public Msg phoneLogin(String phone,String password);
    //用户注册
    public Msg userRegist(String code,String shareCode, String phone, String password,  String nickname, String birthday, boolean sex, String qianming, MultipartFile img, HttpServletRequest req);
   //获取腾讯云IM鉴权Sig
    public Msg getSig(int uid,String token);

    /**
     * hzy
     * 根据id获取用户信息
     * @param uid
     * @return
     */
    User getUserById(int uid);

    /**
     * 添加金币
     * @param id
     * @param money
     * @return
     */
    boolean addMoney(int id,float money);

    /**
     * 减少金币
     * @param id
     * @param money
     * @return
     */
    boolean delMoney(int id,float money);

}
