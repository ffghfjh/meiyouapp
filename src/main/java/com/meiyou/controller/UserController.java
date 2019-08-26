package com.meiyou.controller;

import com.meiyou.service.SendCodeApiService;
import com.meiyou.service.UserService;
import com.meiyou.utils.*;
import com.tls.tls_sigature.tls_sigature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "用户控制器",tags = "用户主控制层")
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
    @ApiOperation(value = "手机号登录",notes = "请把返回的用户id和token和用户account保存")
    public Msg phoneLogin(String phone,String password, HttpServletRequest req){
        if(phone==null||password==null){
            return Msg.nullParam();
        }else{
            Msg msg = userService.phoneLogin(phone,password);
            return msg;
        }
    }

    /**
     * 手机注册
     * @param code
     * @param phone
     * @param password
     * @param nickname
     * @param birthday
     * @param sex
     * @param signature
     * @param img
     * @param req
     * @return
     */
    @RequestMapping(value = "phoneRegist",method = RequestMethod.POST)
    @ApiOperation(value = "手机号注册",notes = "1001 手机号被注册 1003 头像上传失败  1000验证码错误")
    public Msg phoneRegist(String code, String phone, String password,String shareCode, String nickname, String birthday, boolean sex, String signature, MultipartFile img, HttpServletRequest req){
        System.out.println("用户注册");
        if(phone==null||password==null||phone.equals("")||password.equals("")){
            return Msg.nullParam();
        }else{
            Msg msg = userService.userRegist(code,shareCode,phone,password,nickname,birthday,sex,signature,img,req);
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
    @ApiOperation(value = "发送验证码",notes = "type为发送类型 1 用户注册 2 找回密码")
    public Msg sendCode(String phone,int type){
        if(type==1){
            Msg msg = sendCodeApiService.sendRegistCode(phone);
            return msg;
        }else if(type == 2){
            return sendCodeApiService.sendRebackPwd(phone);
        }
        return Msg.fail();
    }

    @RequestMapping(value = "alipayLogin",method = RequestMethod.POST)
    @ApiOperation(value="支付宝登录",notes = "1000 需要绑定手机 接收参数  aliId aliToken 绑定手机要用。")
    public Msg alipayLogin(String auth_code){

        return userService.alipayLogin(auth_code);
    }


    /**
     * 获取签名信息和加签信息
     */
    @RequestMapping(value = "getauthInfo", method = RequestMethod.GET)
    @ApiOperation(value = "获取签名信息和加签信息")
    public String getauthInfo(HttpServletRequest req) {

        String uccount = ShareCodeUtil.getRamdomCount();
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(Constants.PID, Constants.APP_ID, uccount,
                true);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, Constants.APP_PRIVATE_KEY, true);
        String authInfo = info + "&" + sign;
        return authInfo;
    }


    /**
     * 获取usersig
     */
    @RequestMapping(value = "getSig", method = RequestMethod.POST)
    @ApiOperation("获取用户sig")
    public  Msg getSig(int uid,String token) {
        return userService.getSig(uid,token);
    }


    /**
     * 登录鉴权
     */
    @RequestMapping(value = "authToken", method = RequestMethod.GET)
    @ApiOperation("获取用户sig")
    public  Boolean authToken(int uid,String token) {
        if(RedisUtil.authToken(String.valueOf(uid),token)){
            return true;
        }
        return false;
    }
}
