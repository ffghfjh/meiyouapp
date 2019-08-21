package com.meiyou.controller;

import com.meiyou.mapper.AuthorizationMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.pojo.AuthorizationExample;
import com.meiyou.service.GeoService;
import com.meiyou.service.SendCodeApiService;
import com.meiyou.service.UserService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServer;
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
    @Autowired
    SendCodeApiService sendCodeApiService;
    @Autowired
    RedisTemplate<String,String> redis;
    @Autowired
    GeoService geoServices;
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


    @RequestMapping(value="addUserGeo",method = RequestMethod.POST)
    public Msg addUserGeo(){
        geoServices.addUserGeo(114.1,22.2,"1");
        geoServices.addUserGeo(114.7,22.1,"3");
        geoServices.addUserGeo(114.2,22.2,"4");
        geoServices.getUserGeo(114.1,22.2);

        return Msg.success();
    }
}
