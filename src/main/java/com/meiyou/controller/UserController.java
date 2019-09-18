package com.meiyou.controller;

import com.meiyou.service.RootMessageService;
import com.meiyou.service.SendCodeApiService;
import com.meiyou.service.UserService;
import com.meiyou.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Api(value = "用户控制器",tags = "用户主控制层")
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    SendCodeApiService sendCodeApiService;
    @Autowired
    RootMessageService rootMessageService;
    @Autowired
    RedisTemplate<String,String> redis;

    @RequestMapping(value = "userReport",method = RequestMethod.POST)
    @ApiOperation(value = "用户举报",notes = "uid为用户id, type为举报类型， content为举报备注")
    public Msg userReport(Integer reporter_id, Integer reported_id,  String type, String content) {
        return userService.userReport(reporter_id, reported_id, type, content);
    }

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

    @PostMapping("forgetPassword")
    @ApiOperation(value = "忘记密码", notes = "500-->验证码错误,404-->找不到此用户")
    public Msg forgetPassword(String phone, String code, String password){
        return userService.forgetPassword(phone, code, password);
    }

    @RequestMapping(value = "getUserInfo",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户资料")
    public Msg getUserInfo(int uId,String token){
        return userService.getUserInfo(uId,token);
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
        } else {
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

    @RequestMapping(value = "weChatLogin",method = RequestMethod.POST)
    @ApiOperation(value = "微信登录",notes = "1000 需要绑定手机 接收参数 openId  accessToken")
    public Msg weChatLogin(String auth_code){
        return userService.weChatLogin(auth_code);
    }

    @RequestMapping(value = "qqLogin",method = RequestMethod.POST)
    @ApiOperation(value="QQ登录",notes = "1000 需要绑定手机 接收参数  aliId aliToken 绑定手机要用。")
    public Msg qqLogin(String qqOpenId,String qqToken){
        return userService.qqLogin(qqOpenId,qqToken);
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

    @RequestMapping(value="sendMoney",method = RequestMethod.POST)
    @ApiOperation("发红包")
    public Msg sendMoney(int id,String token,String text,String toAccount,int money) {
        System.out.println("发送红包");
        if (RedisUtil.authToken(String.valueOf(id), token)) {
            return userService.sendMoney(id,text,money,toAccount);
        }
        return Msg.noLogin();
    }
    @RequestMapping(value="selRedPackage",method = RequestMethod.GET)
    @ApiOperation("查询红包状态")
    public Msg selRedPackage(int id){
        return userService.selRedPackage(id);
    }

    @RequestMapping(value = "getRedPackage",method = RequestMethod.POST)
    @ApiOperation("领取红包")
    public Msg getRedPackage(int rid,int uid,String token){
        if(RedisUtil.authToken(String.valueOf(uid),token)){
            return userService.getRedPackage(rid);
        }else {
            return Msg.noLogin();
        }
    }

    @RequestMapping(value = "getOtherInfo",method = RequestMethod.POST)
    @ApiOperation("拉取别人资料通过账号")
    public Msg getOtherInfo(String account){
        return userService.getOtherMsg(account);
    }

    @RequestMapping(value = "video_end",method = RequestMethod.POST)
    @ApiOperation("通话结束")
    public Msg video_end(String channelId){
        return userService.videoEnd(channelId);
    }


    @RequestMapping(value="getShareMoney",method = RequestMethod.GET)
    @ApiOperation("获取分享金")
    public Msg getShareMoney(){
       String shareMoney = rootMessageService.getMessageByName("share_money");
       return Msg.success().add("shareMoney",shareMoney);
    }

    /**
    * @Description: 查询用户余额
    * @Author: JK
    * @Date: 2019/8/26
    */
    @RequestMapping(value = "selectUserMoney", method = RequestMethod.POST)
    @ApiOperation("查询用户余额")
    public String selectUserMoney(String uid,String token) {
        return userService.selectUserMoney(uid,token);
    }


     @RequestMapping(value="registBindAlipay",method = RequestMethod.POST)
     @ApiOperation("手机号绑定支付宝")
    public Msg registBindAlipay(int uid,String aliId,String aliToken,String phone,String code,String password,String shareCode){
       return userService.registBindAlipay(uid,aliId,aliToken,phone,code,password,shareCode);
    }

    @RequestMapping(value = "registBindWeChat",method = RequestMethod.POST)
    @ApiOperation("手机绑定微信")
    public Msg registBindWeChat(int uId,String openId,String accesssToken,String phone,String code,String password,String shareCode){
        System.out.println("参数：openId:"+openId+",accessTken:"+accesssToken+",uid:"+uId);
        return userService.registBindWeChat(uId,openId,accesssToken,phone,code,password,shareCode);
    }

    @RequestMapping(value = "registBindQQ",method = RequestMethod.POST)
    @ApiOperation("手机绑定QQ")
    public Msg registBindQQ(int uId,String qqOpenId,String qqTokenn,String phone,String code,String password,String shareCode){
        System.out.println("参数：qqOpenId:"+qqOpenId+",qqToken:"+qqTokenn+",uid:"+uId);
        return userService.registBindQQ(uId,qqOpenId,qqTokenn,phone,code,password,shareCode);
    }

    @RequestMapping(value = "selUserInfoById",method = RequestMethod.POST)
    @ApiOperation("查询用户资料")
    public Msg selUserInfoById(int uId){
        return userService.selUserInfoById(uId);
    }

}
