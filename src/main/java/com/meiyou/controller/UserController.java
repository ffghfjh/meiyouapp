package com.meiyou.controller;

import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api("用户控制器")
public class UserController {


    /**
     * 用户手机号登录
     * @param phone 手机号
     * @param password 登录密码
     * @param req
     * @return
     */
    @RequestMapping(value = "phoneLogin",method = RequestMethod.GET)
    public Msg phoneLogin(@RequestParam("手机号") String phone,@RequestParam("密码") String password, HttpServletRequest req){
        if(phone==null||password==null){
            return Msg.nullParam();
        }else{
            return null;
        }

    }

}
