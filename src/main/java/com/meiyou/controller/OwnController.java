package com.meiyou.controller;

import com.meiyou.pojo.User;
import com.meiyou.service.OwnService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 个人用户控制器层
 * @author: Mr.Z
 * @create: 2019-08-30 20:16
 **/
@RestController
@Api(value = "个人用户控制层", tags = {"个人用户控制层"})
@RequestMapping("/own")
public class OwnController {
    @Autowired
    OwnService ownService;

    @PostMapping("/updateInfo")
    @ApiOperation(value = "修改资料", notes = "修改")
    public Msg updateInfo(@RequestParam("uid") Integer uid,
                          @RequestParam("token") String token,
                          @RequestParam("nickname") String nickname,
                          @RequestParam("sex") Boolean sex,
                          @RequestParam("birthday") String birthday,
                          @RequestParam("signature") String signature
                          ){
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }
        User user = new User();
        user.setId(uid);
        user.setNickname(nickname);
        user.setSex(sex);
        user.setBirthday(birthday);
        user.setSignature(signature);

        return ownService.changeInfo(user);
    }

    @PostMapping("/updateHeader")
    @ApiOperation(value = "修改我的头像", notes = "500-->头像上传失败,501-->更新用户头像失败")
    public Msg updateHeader(@RequestParam("uid") Integer uid,
                            @RequestParam("token") String token,
                            @RequestParam("img") MultipartFile img,
                            HttpServletRequest req){
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }
        return ownService.changeHeader(uid,img,req);
    }

    @PostMapping("/updateBgpicture")
    @ApiOperation(value = "修改我的背景", notes = "503-->背景图片更改失败,504-->更改背景图片失败")
    public Msg updateBgpicture(@RequestParam("uid") Integer uid,
                            @RequestParam("token") String token,
                            @RequestParam("img") MultipartFile img,
                            HttpServletRequest req){
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }
        return ownService.changeBackGroudPicture(uid,img,req);
    }


    @PostMapping("/updatePassword")
    @ApiOperation(value = "修改登录密码", notes = "修改")
    public Msg updatePassword(@RequestParam("uid") Integer uid,
                              @RequestParam("token") String token,
                              @RequestParam("oldPassword") String oldPassword,
                              @RequestParam("newPassword") String newPassword){
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }
        return ownService.changePassword(uid,oldPassword,newPassword);
    }

    @PostMapping("/updatePayWord")
    @ApiOperation(value = "修改支付密码", notes = "修改")
    public Msg updatePayWord(@RequestParam("uid") Integer uid,
                             @RequestParam("token") String token,
                             @RequestParam("oldPassword") String oldPassword,
                             @RequestParam("newPassword") String newPassword){
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }
        return ownService.changePayPassword(uid,oldPassword,newPassword);

    }

    @PostMapping("/selectByUid")
    @ApiOperation(value = "查找用户资料", notes = "查找")
    public Msg selectByUid(@RequestParam("uid") Integer uid,
                           @RequestParam("token") String token){
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }
        return ownService.selectByUid(uid);
    }
}
