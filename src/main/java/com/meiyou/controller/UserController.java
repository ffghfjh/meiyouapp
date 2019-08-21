package com.meiyou.controller;

import com.meiyou.mapper.AuthorizationMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.pojo.AuthorizationExample;
import com.meiyou.service.UserService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@Api("用户控制器")
public class UserController {


    @Autowired
    UserService userService;


    /**
     * 用户手机号登录
     * @param phone 手机号
     * @param password 登录密码
     * @param req
     * @return
     */
    @RequestMapping(value = "phoneLogin",method = RequestMethod.POST)
    public Msg phoneLogin(String phone,String password, HttpServletRequest req){
        if(phone==null||password==null){
            return Msg.nullParam();
        }else{
            Msg msg = userService.phoneLogin(phone,password);
            return msg;
        }
    }
}
