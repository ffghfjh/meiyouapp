package com.meiyou.controller;

import com.meiyou.service.SendCodeApiService;
import com.meiyou.service.UserService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api("用户控制器")
public class UserController {


    @Autowired
    UserService userService;
    @Autowired
    SendCodeApiService sendCodeApiService;
    @Autowired
    RedisTemplate<String,String> redis;

    /**
     * 用户手机号登录
     * @param phone 手机号
     * @param password 登录密码
     * @param req
     * @return
     */
    @RequestMapping(value = "phoneLogin",method = RequestMethod.POST)
    @ApiOperation(value = "手机号登录")
    public Msg phoneLogin(String phone,String password, HttpServletRequest req){
        if(phone==null||password==null){
            return Msg.nullParam();
        }else{
            Msg msg = userService.phoneLogin(phone,password);
            return msg;
        }
    }

    /**
     *
     * @param phone
     * @param type 类型 1 注册  2找回密码
     * @return
     */
    @RequestMapping(value = "sendCode",method = RequestMethod.POST)
    @ApiOperation(value = "发送验证码")
    public Msg sendCode(String phone,int type){
        if(type==1){
            Msg msg = sendCodeApiService.sendRegistCode(phone);
            return msg;
        }else if(type == 2){
            return sendCodeApiService.sendRebackPwd(phone);
        }
        return Msg.fail();
    }
}