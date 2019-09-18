package com.meiyou.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.meiyou.config.QueueConfig;
import com.meiyou.mapper.*;
import com.meiyou.mapper.AuthorizationMapper;
import com.meiyou.mapper.RedPacketMapper;
import com.meiyou.mapper.ShareMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.mapper.*;
import com.meiyou.model.AliPayInfo;
import com.meiyou.model.ExpirationMessagePostProcessor;
import com.meiyou.model.WXUserInfo;
import com.meiyou.pojo.*;
import com.meiyou.service.RootMessageService;
import com.meiyou.service.TencentImService;
import com.meiyou.service.UserService;
import com.meiyou.utils.*;
import com.tls.tls_sigature.tls_sigature;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Service
@CacheConfig(cacheNames = "MeiyouCache") //hzy, 配置Redis缓存
public class UserServiceImpl implements UserService {

    @Autowired
    UserReportMapper userReportMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    AuthorizationMapper authMapper;
    @Autowired
    TencentImService imService;
    @Resource
    RedisTemplate redisTemplate;
    @Autowired
    RootMessageService rootMessageService;
    @Autowired
    ShareMapper shareMapper;
    @Autowired
    RedPacketMapper redPacketMapper;
    @Autowired
    AmqpTemplate rabbitTemplate;
    @Autowired
    VideoChatMapper videoChatMapper;
    @Autowired
    TencentImService tencentImService;
    @Autowired
    CashMapper cashMapper;



    // 支付宝调用接口之前的初始化
    AlipayClient alipayClient = new DefaultAlipayClient(Constants.ALIURL, Constants.APP_ID,
            Constants.APP_PRIVATE_KEY, Constants.FORMAT, Constants.CHARSET, Constants.ALIPAY_PUBLIC_KEY,
            Constants.SIGN_TYPE);

    @Override
    @Transactional
    public Msg alipayLogin(String auth_code) {
        Msg msg;
        // 支付宝请求
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();// 初始化请求
        request.setGrantType("authorization_code");// 值为authorization_code时，代表用code换取；值为refresh_token时，代表用refresh_token换取
        request.setCode(auth_code);// 参数为授权码

        try {
            AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                String alipayUserId = response.getUserId();//获取用户id
                AuthorizationExample example = new AuthorizationExample();
                AuthorizationExample.Criteria criteria = example.createCriteria();
                criteria.andIdentityTypeEqualTo(2);//验证方式为支付宝
                criteria.andIdentifierEqualTo(alipayUserId);//唯一支付宝id
                List<Authorization> authorizations = authMapper.selectByExample(example);
                //是否存在该支付宝用户
                if(authorizations.size()>0){
                    Authorization authorization = authorizations.get(0);
                    User user = userMapper.selectByPrimaryKey(authorization.getUserId());//获取用户
                    //验证绑定手机
                    if(authorization.getBoolVerified()){
                        msg  = Msg.success();
                        msg.add("uid",user.getId());
                        msg.add("account",user.getAccount());
                        msg.add("nickName",user.getNickname());
                        msg.add("header",user.getHeader());
                        msg.add("shareCode",user.getShareCode());
                        String token = UUID.randomUUID().toString();
                        //保存token
                        RedisUtil.setToken(String.valueOf(user.getId()),token,Constants.TOKEN_EXPIRES_SECOND);
                        msg.add("token",token);
                        return msg;
                    }else {
                       int uid = user.getId();
                       msg = Msg.fail();
                       msg.setCode(1000);
                       msg.setMsg("未绑定手机");
                       msg.add("aliId",alipayUserId);
                       msg.add("aliToken",authorization.getCredential());//阿里token
                        msg.add("uid",uid);
                       return msg;
                    }
                }
                //不存在用户支付宝信息获取
                else{
                    String access_token = response.getAccessToken();// 访问令牌,通过该令牌调用需要用户信息授权的接口，如alipay.user.info.share
                    System.out.println("授权令牌：" + access_token);
                    AlipayUserInfoShareRequest infoShareRequest = new AlipayUserInfoShareRequest();
                    AlipayUserInfoShareResponse infoShareResponse = alipayClient.execute(infoShareRequest, access_token);
                    if (infoShareResponse.isSuccess()) {
                        Map<String, Object> map = new HashMap<>();
                        AliPayInfo alipayinfo = new AliPayInfo();
                        alipayinfo.setUserId(infoShareResponse.getUserId()); // 支付宝id
                        alipayinfo.setAvatar(infoShareResponse.getAvatar()); // 支付宝头像URL
                        alipayinfo.setGender(infoShareResponse.getGender()); // 支付宝性别
                        System.out.println("手机号" + infoShareResponse.getPhone());//
                        alipayinfo.setIs_certied(infoShareResponse.getIsCertified()); // 支付宝是否实名
                        if(infoShareResponse.getNickName()==null){
                            alipayinfo.setNick_name("未命名");
                        }else{
                            alipayinfo.setNick_name(infoShareResponse.getNickName()); // 支付宝昵称

                        }
                        User user = new User();
                        String userAccount = RandomUtil.randomNumbers(8);//UUID生成账号
                        user.setAccount(userAccount);
                        user.setBgPicture(Constants.USER_BAC_DEFAULT);//设置默认背景
                        user.setBindAlipay(false);//未绑定支付宝

                        user.setBirthday(Constants.AGE);//设置默认年龄
                        user.setBoolClose(false);
                        user.setHeader(alipayinfo.getAvatar());//设置头像
                        user.setNickname(alipayinfo.getNick_name());//设置昵称
                        Date date = new Date();
                        user.setCreateTime(date);
                        user.setUpdateTime(date);
                        user.setMoney(0f);
                        user.setSignature(Constants.SIGNATURE);//设置默认签名
                        if(alipayinfo.getGender().equals("F")){
                            user.setSex(false);//女
                        }else {
                            user.setSex(true);//男
                        }
                        user.setShareCode(ShareCodeUtil.toSerialCode(2));//设置邀请码
                        if(userMapper.insert(user)==1){
                            int uid = user.getId();//获取插入的用户
                            Authorization authorization = new Authorization();
                            authorization.setBoolVerified(false);//设置为激活状态
                            authorization.setCreateTime(date);
                            authorization.setIdentifier(alipayUserId);
                            authorization.setUpdateTime(date);
                            authorization.setIdentityType(2);//设置支付宝方式
                            authorization.setCredential(access_token);//授权凭证
                            authorization.setUserId(uid);//设置用户id
                            if(authMapper.insert(authorization)==1){
                                if(imService.registTencent(user)){
                                    msg = Msg.success();
                                    msg.setCode(1000);//10001需要绑定手机
                                    msg.add("uid",user.getId());
                                    msg.add("account",user.getAccount());
                                    msg.add("nickName",user.getNickname());
                                    msg.add("header",user.getHeader());
                                    msg.add("aliId",authorization.getIdentifier());//阿里ID
                                    msg.add("token",authorization.getCredential());//授权凭证
                                    msg.add("aliId",alipayUserId);
                                    msg.add("aliToken",authorization.getCredential());
                                    return msg;
                                }else {
                                    msg = Msg.fail();
                                    msg.setMsg("腾讯云账号注册失败");
                                    return msg;
                                }
                            }
                            msg = Msg.fail();
                            return msg;
                        }
                    } else {
                        System.out.println("支付宝令牌授权失败");
                        System.out.println("错误码" + infoShareResponse.getCode());
                        System.out.println("明细错误码" + infoShareResponse.getSubCode());
                        System.out.println("调用失败!" + infoShareResponse.getMsg());
                    }
                }
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return Msg.fail();
    }

    @Override
    public Msg phoneLogin(String phone, String password) {

        AuthorizationExample example = new AuthorizationExample();
        AuthorizationExample.Criteria criteria = example.createCriteria();
        criteria.andIdentifierEqualTo(phone);//身份标识（手机）
        criteria.andIdentityTypeEqualTo(1);//授权类型未手机
        criteria.andCredentialEqualTo(password); //密码
        criteria.andBoolVerifiedEqualTo(true);//已激活
        List<Authorization> authorizations = authMapper.selectByExample(example);//查询用户
        if(authorizations.size()==1){   //授权通过e
            Authorization authorization  = authorizations.get(0);
            int uid = authorization.getUserId();//用户id
            User user = userMapper.selectByPrimaryKey(uid);//查询用户
            Msg msg = Msg.success();
            msg.add("uid",user.getId());
            msg.add("account",user.getAccount());
            msg.add("nickName",user.getNickname());
            msg.add("header",user.getHeader());
            msg.add("shareCode",user.getShareCode());
            String token = UUID.randomUUID().toString();//UUID生成token
            if(!RedisUtil.setToken(String.valueOf(user.getId()),token,Constants.TOKEN_EXPIRES_SECOND)){
                System.out.println("写入token失败");
            }
            msg.add("token",token);
            return msg;
        }else if(authorizations == null ||authorizations.size() == 0 ){
           Msg msg = Msg.fail();
           msg.setMsg("用户名或密码错误");
           return msg;
        }else{
            System.out.println("手机号用户重复！！！！！");
            return Msg.fail();
        }
    }



    @Override
    @Transactional
    public Msg userRegist(String code,String shareCodeRegist, String phone, String password,  String nickname,
                          String birthday, boolean sex, String signature, MultipartFile img, HttpServletRequest req) {
        Msg msg;
        //校验验证码
        if(RedisUtil.authCode(phone,code)){
            //检测手机号
            AuthorizationExample example = new AuthorizationExample();
            AuthorizationExample.Criteria criteria = example.createCriteria();
            criteria.andIdentityTypeEqualTo(1);//手机号
            criteria.andIdentifierEqualTo(phone);
            if(authMapper.selectByExample(example).size()>0){
                msg = Msg.fail();
                msg.setCode(1001);//手机号被注册
                msg.setMsg("手机号已注册");
                return msg;
            }else{

                User user = new User();
                String account = RandomUtil.randomNumbers(8);//用户账号
                String shareCode = ShareCodeUtil.toSerialCode(1);//邀请码生成
                user.setShareCode(shareCode);//邀请码
                user.setAccount(account);
                user.setSex(sex);
                user.setNickname(nickname);
                Date date = new Date();
                user.setSignature(signature);
                user.setMoney(0f);
                user.setCreateTime(date);
                user.setUpdateTime(date);
                user.setBindAlipay(false);
                user.setBirthday(birthday);
                user.setBoolClose(false);
                user.setBgPicture(Constants.USER_BAC_DEFAULT);


                //文件上传判断
                Msg fileMsg = FileUploadUtil.uploadUtil(img,"headers",req);
                if(fileMsg.getCode()==100){
                    user.setHeader((String) fileMsg.getExtend().get("path"));
                    if(userMapper.insert(user)==1){
                        int uid = user.getId();
                            addShare(shareCodeRegist,uid);//邀请码业务
                            Authorization authorization = new Authorization();
                            authorization.setUserId(uid);
                            authorization.setIdentityType(1);
                            authorization.setCredential(password);
                            authorization.setBoolVerified(true);
                            authorization.setUpdateTime(date);
                            authorization.setCreateTime(date);
                            authorization.setIdentifier(phone);
                            if(authMapper.insert(authorization)>0){
                                if(imService.registTencent(user)){
                                    return Msg.success();
                                }else{
                                    msg = Msg.fail();
                                    msg.setMsg("腾讯云账号注册失败");
                                    return msg;
                                }
                            }
                    }
                }else {
                    msg = Msg.fail();
                    msg.setCode(1003);
                    msg.setMsg("头像上传失败");
                    System.out.println(msg.toString());
                    return msg;
                }
            }
        }else {
            System.out.println("验证码错误");
            msg = Msg.fail();
            msg.setCode(1000);
            msg.setMsg("验证码错误");
            return msg;
        }
        return Msg.fail();
    }

    @Override
    public Msg getSig(int uid,String token) {
        Msg msg;
        if(RedisUtil.authToken(String.valueOf(uid),token)){
            String account = userMapper.selectByPrimaryKey(uid).getAccount();
            String usersig = tls_sigature.genSig(Constants.SDKAPPID, account, Constants.PRIKEY).urlSig;
            msg = Msg.success();
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("userSig",usersig);
            msg.setExtend(map);
            return msg;
        }else{
            return Msg.noLogin();
        }

    }

    @Override
    @Transactional
    public Msg weChatLogin(String auth_code) {
        Msg msg;
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constants.WX_APP_ID + "&secret="
                + Constants.APPSECRET + "&code=" + auth_code + "&grant_type=authorization_code";
        CloseableHttpClient client;
        CloseableHttpResponse response;

        HttpGet get = new HttpGet(url);
        client = HttpClients.createDefault();
        try {
            response = client.execute(get);
            HttpEntity repentity = response.getEntity();
            String resresult = EntityUtils.toString(repentity, "utf-8");
            JSONObject json = JSONObject.fromObject(resresult);
            String access_token = json.getString("access_token");// 接口调用凭证
            String openid = json.getString("openid");// 授权用户唯一标识

            AuthorizationExample example = new AuthorizationExample();
            AuthorizationExample.Criteria criteria = example.createCriteria();
            criteria.andIdentifierEqualTo(openid);
            criteria.andIdentityTypeEqualTo(3);//微信
            List<Authorization> authorizations = authMapper.selectByExample(example);
            if(authorizations.size()>0){
                  Authorization authorization = authorizations.get(0);
                  User user = userMapper.selectByPrimaryKey(authorization.getUserId());
                  if(authorization.getBoolVerified()){ //已激活

                      msg  = Msg.success();
                      msg.add("uid",user.getId());
                      msg.add("account",user.getAccount());
                      msg.add("nickName",user.getNickname());
                      msg.add("header",user.getHeader());
                      msg.add("shareCode",user.getShareCode());
                      String token = UUID.randomUUID().toString();
                      //保存token
                      RedisUtil.setToken(String.valueOf(user.getId()),token,Constants.TOKEN_EXPIRES_SECOND);
                      msg.add("token",token);
                      return msg;
                  }else {//未激活
                      msg = new Msg();
                      msg.setCode(1000);
                      msg.add("openId",openid);
                      msg.add("accessToken",authorization.getCredential());
                      msg.add("uId",user.getId());
                      return msg;
                  }
            }else {
                System.out.println("微信新用户");

                WXUserInfo info = getWxUserInfo(access_token,openid);
                User user = new User();
                String userAccount = RandomUtil.randomNumbers(8);//UUID生成账号
                user.setAccount(userAccount);
                user.setBgPicture(Constants.USER_BAC_DEFAULT);//设置默认背景
                user.setBindAlipay(false);//未绑定支付宝

                user.setBirthday(Constants.AGE);//设置默认年龄
                user.setBoolClose(false);
                user.setHeader(info.getHeadimgurl());//设置头像
                user.setNickname(info.getNickname());//设置昵称
                user.setSex(info.isSex());//设置昵称
                Date date = new Date();
                user.setCreateTime(date);
                user.setUpdateTime(date);
                user.setMoney(0f);
                user.setSignature(Constants.SIGNATURE);//设置默认签名
                user.setShareCode(ShareCodeUtil.toSerialCode(2));//设置邀请码
                if(userMapper.insert(user)==1){
                    int uid = user.getId();
                    Authorization authorization = new Authorization();
                    authorization.setIdentityType(3);
                    authorization.setIdentifier(openid);
                    authorization.setBoolVerified(false);
                    authorization.setCredential(access_token);
                    authorization.setUserId(uid);
                    authorization.setCreateTime(date);
                    authorization.setUpdateTime(date);
                    if(authMapper.insertSelective(authorization)==1){
                        if(imService.registTencent(user)){
                            msg = Msg.success();
                            msg.setCode(1000);//需要绑定手机号
                            msg.add("openId",openid);
                            msg.add("accessToken",access_token);
                            msg.add("uId",uid);
                            return msg;
                        }else {
                            msg = Msg.fail();
                            msg.setMsg("腾讯云账号注册失败");
                            return msg;
                        }
                    }
                }
            }
            return Msg.fail();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过用户id对用户进行封号
     *
     * @param uuid
     * @param ttype
     * @return
     */
    @Override
    public Msg hideUserById(String uuid, String ttype) {
        int uid = Integer.parseInt(uuid);
        int type = Integer.parseInt(ttype);
        Msg msg = new Msg();
        //先判断用户是否存在
        User user = userMapper.selectByPrimaryKey(uid);
        if (user == null) {
            msg.setCode(1001);
            msg.setMsg("用户不存在");
            return msg;
        }
        user.setId(uid);
        user.setUpdateTime(new Date());
        if (type == 1) {
            user.setBoolClose(true);
            int i = userMapper.updateByPrimaryKeySelective(user);
            if (i == 0) {
                msg.setCode(1002);
                msg.setMsg("封号失败");
                return msg;
            }
            msg.setCode(1003);
            msg.setMsg("封号成功");
            return msg;
        }
        user.setBoolClose(false);
        int i = userMapper.updateByPrimaryKeySelective(user);
        if (i == 0) {
            msg.setCode(1004);
            msg.setMsg("取消封号失败");
            return msg;
        }
        msg.setCode(1005);
        msg.setMsg("取消封号成功");
        return msg;
    }




    /**
     * 获取微信用户信息
     *
     * @param access_token
     * @param openid
     * @return
     */
    public WXUserInfo getWxUserInfo(String access_token, String openid) {

        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid;
        CloseableHttpClient client;
        CloseableHttpResponse response;
        // TODO Auto-generated method stub
        HttpGet get = new HttpGet(url);
        client = HttpClients.createDefault();
        try {
            response = client.execute(get);
            HttpEntity repentity = response.getEntity();
            String resresult = EntityUtils.toString(repentity, "utf-8");
            JSONObject json = JSONObject.fromObject(resresult);
            WXUserInfo wx = new WXUserInfo();
            wx.setOpenid(json.getString("openid"));
            wx.setNickname(json.getString("nickname"));
            if (json.getInt("sex") == 1) {
                wx.setSex(false);
            } else {
                wx.setSex(true);
            }
            wx.setHeadimgurl(json.getString("headimgurl"));
            return wx;
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加邀请记录
     * @param shareCodeRegist 邀请码
     * @param bid 被分享人id
     * @return
     */
    private boolean addShare(String shareCodeRegist,int bid){

        UserExample example1 = new UserExample();
        UserExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andShareCodeEqualTo(shareCodeRegist);
        List<User> list = userMapper.selectByExample(example1);//分享人
        if(list==null||list.size()==0){
            return false;
        }else {
            User user1 = list.get(0);
            Share share = new Share();
            share.setByPeopleId(bid);
            share.setPeopleId(user1.getId());
            Date date = new Date();
            share.setCreateTime(date);
            share.setUpdateTime(date);
            int money = Integer.parseInt(rootMessageService.getMessageByName("share_money"));
            share.setShareMoney(money);
            int i = shareMapper.insert(share);
            if(i==1){
                //添加金币
                if(addMoney(user1.getId(),money)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * hzy
     * 根据id获取用户信息
     *
     * @param uid
     * @return
     */
   // @Cacheable() //缓存到Redis中(待确定是否添加缓存处理)
    @Override
    public User getUserById(int uid) {
        User user = userMapper.selectByPrimaryKey(uid);
        if (user == null) {
            User user1 = new User();
            user1.setId(0);
            user1.setHeader("no Pic");
            user1.setSex(false);
            user1.setNickname("找不到任何用户");
            user1.setBirthday("0");
            return user1;
        }
        return user;
    }

    @Override
    public boolean addMoney(int id, float money) {

        User user = userMapper.selectByPrimaryKey(id);
        user.setMoney(user.getMoney()+money);
        int i = userMapper.updateByPrimaryKey(user);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean delMoney(int id, float money) {
        User user = userMapper.selectByPrimaryKey(id);
        user.setMoney(user.getMoney()-money);
        int i = userMapper.updateByPrimaryKey(user);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Msg sendMoney(int id,String text,int money, String toAccount) {
        Msg msg;
        User me = userMapper.selectByPrimaryKey(id);
        float meMoney = me.getMoney();

        //余额检测
        if(meMoney<money){
            msg = Msg.fail();
            msg.setCode(1000);//余额不足
            msg.setMsg("余额不足");
            return msg;
        }
        me.setMoney(meMoney-money);
        if(userMapper.updateByPrimaryKeySelective(me)==1){
            UserExample example = new UserExample();
            UserExample.Criteria criteria = example.createCriteria();
            criteria.andAccountEqualTo(toAccount);
            int toId = userMapper.selectByExample(example).get(0).getId();
            RedPacket redPacket = new RedPacket();
            redPacket.setContent(text);
            redPacket.setMoney(money);
            redPacket.setState(0);
            redPacket.setReceiveId(toId);
            redPacket.setSenderId(id);
            Date date = new Date();
            redPacket.setCreateTime(date);
            redPacket.setUpdateTime(date);
            if(redPacketMapper.insertSelective(redPacket)==1) {
                long expiration = 86400000;//消息24小时后过期
                System.out.println("发送红包，添加到延迟队列");
                rabbitTemplate.convertAndSend(QueueConfig.DELAY_QUEUE_PER_MESSAGE_TTL_NAME,
                        (Object) (String.valueOf(redPacket.getId())), new ExpirationMessagePostProcessor(expiration));
                msg = Msg.success();
                msg.add("hId",redPacket.getId());
                return msg;
            }
        }
        return Msg.fail();
    }
    /**
    * @Description: 查询用户余额
    * @Author: JK
    * @Date: 2019/8/26
    */
    @Override
    public String selectUserMoney(String uid,String token) {
        boolean authToken = RedisUtil.authToken(uid, token);
        //判断是否登录
        if (!authToken) {
            return "登陆失败";
        }
        User user = userMapper.selectByPrimaryKey(Integer.parseInt(uid));
        Float money = user.getMoney();
        return String.valueOf(money);
    }

    @Override
    public Msg registBindAlipay(int uId, String aliId, String aliToken,String phone,String code,String password,String shareCode) {
        Msg msg;
        //验证码校验
        if(RedisUtil.authCode(phone,code)){  //通过

            //校验手机是否可注册
            AuthorizationExample example1 = new AuthorizationExample();
            AuthorizationExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andIdentifierEqualTo(phone);
            criteria1.andIdentityTypeEqualTo(1);
            if(authMapper.selectByExample(example1).size()>0){
                msg = Msg.fail();
                msg.setCode(1001);
                msg.setMsg("手机号已被注册");
                return msg;
            }else {
                AuthorizationExample example = new AuthorizationExample();
                AuthorizationExample.Criteria criteria = example.createCriteria();
                criteria.andUserIdEqualTo(uId);
                criteria.andIdentifierEqualTo(aliId);
                criteria.andCredentialEqualTo(aliToken);
                criteria.andIdentityTypeEqualTo(2);
                criteria.andBoolVerifiedEqualTo(false);
                List<Authorization> authorizations = authMapper.selectByExample(example);
                if(authorizations.size()>0){
                    Authorization authorization = authorizations.get(0);
                    authorization.setBoolVerified(true);//开放

                    Authorization newAuthorization = new Authorization();
                    newAuthorization.setBoolVerified(true);
                    newAuthorization.setIdentifier(phone);
                    newAuthorization.setIdentityType(1);//类型为手机号
                    newAuthorization.setUserId(uId);
                    newAuthorization.setCredential(password);
                    Date date = new Date();
                    newAuthorization.setCreateTime(date);
                    newAuthorization.setUpdateTime(date);
                    if(authMapper.updateByPrimaryKeySelective(authorization)==1&&authMapper.insertSelective(newAuthorization)==1){
                        addShare(shareCode,uId);//添加分享记录
                        User user = userMapper.selectByPrimaryKey(uId);
                        msg = Msg.success();
                        msg.add("uid",user.getId());
                        msg.add("account",user.getAccount());
                        msg.add("nickName",user.getNickname());
                        msg.add("header",user.getHeader());
                        msg.add("shareCode",user.getShareCode());
                        String token = UUID.randomUUID().toString();
                        RedisUtil.setToken(String.valueOf(user.getId()),token,Constants.TOKEN_EXPIRES_SECOND);//写入token
                        msg.add("token",token);
                        return msg;
                    }else{
                        System.out.println("更新用户数据失败");
                    }
                }
            }
        }else{
            msg = Msg.fail();
            msg.setCode(1000);
            msg.setMsg("验证码错误");
            return msg;
        }
        return Msg.fail();
    }

    @Override
    public Msg registBindWeChat(int uId, String openId, String accesssToken, String phone, String code, String password, String shareCode) {
        Msg msg;

        //验证码校验
        if(RedisUtil.authCode(phone,code)){

            //校验手机是否可注册
            AuthorizationExample example1 = new AuthorizationExample();
            AuthorizationExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andIdentifierEqualTo(phone);
            criteria1.andIdentityTypeEqualTo(1);
            if(authMapper.selectByExample(example1).size()>0){
                msg = Msg.fail();
                msg.setCode(1001);
                msg.setMsg("手机号已被注册");
                return msg;
            }

            AuthorizationExample example = new AuthorizationExample();
            AuthorizationExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(uId);
            criteria.andIdentifierEqualTo(openId);
            criteria.andCredentialEqualTo(accesssToken);
            criteria.andIdentityTypeEqualTo(3);//微信方式
            criteria.andBoolVerifiedEqualTo(false);
            List<Authorization> authorizations = authMapper.selectByExample(example);
            if(authorizations.size()>0) {
                Authorization authorization = authorizations.get(0);
                authorization.setBoolVerified(true);//更新激活状态

                Authorization newAuthorization = new Authorization();
                newAuthorization.setBoolVerified(true);
                newAuthorization.setIdentifier(phone);
                newAuthorization.setIdentityType(1);//类型为手机号
                newAuthorization.setUserId(uId);
                newAuthorization.setCredential(password);
                Date date = new Date();
                newAuthorization.setCreateTime(date);
                newAuthorization.setUpdateTime(date);
                if (authMapper.updateByPrimaryKeySelective(authorization) == 1 && authMapper.insertSelective(newAuthorization) == 1) {
                    addShare(shareCode,uId);//添加分享记录
                    User user = userMapper.selectByPrimaryKey(uId);
                    msg = Msg.success();
                    msg.add("uid",user.getId());
                    msg.add("account",user.getAccount());
                    msg.add("nickName",user.getNickname());
                    msg.add("header",user.getHeader());
                    String token = UUID.randomUUID().toString();
                    RedisUtil.setToken(String.valueOf(user.getId()),token,Constants.TOKEN_EXPIRES_SECOND);//写入token
                    msg.add("token",token);
                    msg.add("shareCode",user.getShareCode());
                    return msg;
                }else {
                    System.out.println("更新用户数据失败");
                }
              }else {
                System.out.println("未找到该微信用户");
                }
        }else {
            msg = Msg.fail();
            msg.setCode(1000);
            msg.setMsg("验证码错误");
            return msg;
        }
        return Msg.fail();
    }

    @Override
    public Msg registBindQQ(int uId, String qqOpenId, String qqToken, String phone, String code, String password, String shareCode) {
        Msg msg;
        //验证码校验
        if(RedisUtil.authCode(phone,code)){
            //校验手机是否可注册
            AuthorizationExample example1 = new AuthorizationExample();
            AuthorizationExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andIdentifierEqualTo(phone);
            criteria1.andIdentityTypeEqualTo(1);
            if(authMapper.selectByExample(example1).size()>0){
                msg = Msg.fail();
                msg.setCode(1001);
                msg.setMsg("手机号已被注册");
                return msg;
            }
            AuthorizationExample example = new AuthorizationExample();
            AuthorizationExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(uId);
            criteria.andIdentifierEqualTo(qqOpenId);
            criteria.andCredentialEqualTo(qqToken);
            criteria.andIdentityTypeEqualTo(4);//QQ方式
            criteria.andBoolVerifiedEqualTo(false);
            List<Authorization> authorizations = authMapper.selectByExample(example);
            if(authorizations.size()>0) {
                Authorization authorization = authorizations.get(0);
                authorization.setBoolVerified(true);//更新激活状态

                Authorization newAuthorization = new Authorization();
                newAuthorization.setBoolVerified(true);
                newAuthorization.setIdentifier(phone);
                newAuthorization.setIdentityType(1);//类型为手机号
                newAuthorization.setUserId(uId);
                newAuthorization.setCredential(password);
                Date date = new Date();
                newAuthorization.setCreateTime(date);
                newAuthorization.setUpdateTime(date);
                if (authMapper.updateByPrimaryKeySelective(authorization) == 1 && authMapper.insertSelective(newAuthorization) == 1) {
                    addShare(shareCode,uId);//添加分享记录
                    User user = userMapper.selectByPrimaryKey(uId);
                    msg = Msg.success();
                    msg.add("uid",user.getId());
                    msg.add("account",user.getAccount());
                    msg.add("nickName",user.getNickname());
                    msg.add("header",user.getHeader());
                    String token = UUID.randomUUID().toString();
                    RedisUtil.setToken(String.valueOf(user.getId()),token,Constants.TOKEN_EXPIRES_SECOND);//写入token
                    msg.add("token",token);
                    msg.add("shareCode",user.getShareCode());
                    return msg;
                }else {
                    System.out.println("更新用户数据失败");
                }
            }else {
                System.out.println("未找到该QQ用户");
            }

        }else {
            msg = Msg.fail();
            msg.setCode(1000);
            msg.setMsg("验证码错误");
            return msg;
        }
        return Msg.fail();
    }

    @Override
    public Msg qqLogin(String qqOpenId, String qqToken) {
       Msg msg;
        AuthorizationExample example = new AuthorizationExample();
        AuthorizationExample.Criteria criteria = example.createCriteria();
        criteria.andIdentityTypeEqualTo(4);
        criteria.andIdentifierEqualTo(qqOpenId);
        criteria.andCredentialEqualTo(qqToken);
        List<Authorization> authorizations = authMapper.selectByExample(example);
        if(authorizations.size()>0){ //存在该用户
            //校验绑定手机
            if(authorizations.get(0).getBoolVerified()){
                Authorization authorization = authorizations.get(0);
                User user = userMapper.selectByPrimaryKey(authorization.getUserId());
                if(authorization.getBoolVerified()) { //已激活
                    msg = Msg.success();
                    msg.add("uid", user.getId());
                    msg.add("account", user.getAccount());
                    msg.add("nickName", user.getNickname());
                    msg.add("header", user.getHeader());
                    msg.add("shareCode",user.getShareCode());
                    String token = UUID.randomUUID().toString();
                    //保存token
                    RedisUtil.setToken(String.valueOf(user.getId()), token, Constants.TOKEN_EXPIRES_SECOND);
                    msg.add("token", token);
                    return msg;
                }
                }else {
                    msg = Msg.success();
                    msg.setCode(1000);//需要绑定手机号
                    msg.add("qqOpenId",qqOpenId);
                    msg.add("qqToken",qqToken);
                    msg.add("uId",authorizations.get(0).getUserId());
                    return msg;
            }
        }else{ //不存在该qq用户
            String url = "https://graph.qq.com/user/get_user_info?access_token="+qqToken+"&oauth_consumer_key="+Constants.QQ_APP_ID+"&openid="+qqOpenId;
            JSONObject json = HttpUtil.getToJSONObject(url);
            System.out.println(json.toString());
            if(json.getInt("ret")==0) {
                String header = json.getString("figureurl_qq_1");
                String nickName = json.getString("nickname");
                boolean sex = false;
                if(json.getString("gender").equals("男")){
                    sex = false;
                }else{
                    sex = true;
                }


                User user = new User();
                String userAccount = RandomUtil.randomNumbers(8);//UUID生成账号
                user.setAccount(userAccount);
                user.setBgPicture(Constants.USER_BAC_DEFAULT);//设置默认背景
                user.setBindAlipay(false);//未绑定支付宝

                user.setBirthday(Constants.AGE);//设置默认年龄
                user.setBoolClose(false);
                user.setHeader(header);//设置头像
                user.setNickname(nickName);//设置昵称
                user.setSex(sex);//设置昵称
                Date date = new Date();
                user.setCreateTime(date);
                user.setUpdateTime(date);
                user.setMoney(0f);
                user.setSignature(Constants.SIGNATURE);//设置默认签名
                user.setShareCode(ShareCodeUtil.toSerialCode(2));//设置邀请码
                if(userMapper.insert(user)==1){
                    int uId = user.getId();
                    Authorization authorization = new Authorization();
                    authorization.setUserId(uId);
                    authorization.setBoolVerified(false);
                    authorization.setIdentifier(qqOpenId);
                    authorization.setCredential(qqToken);
                    authorization.setIdentityType(4);//类型qq
                    authorization.setCreateTime(date);
                    authorization.setUpdateTime(date);

                    if(authMapper.insertSelective(authorization)==1){
                        if(imService.registTencent(user)){
                            msg = Msg.success();
                            msg.setCode(1000);//需要绑定手机号
                            msg.add("qqOpenId",qqOpenId);
                            msg.add("qqToken",qqToken);
                            msg.add("uId",uId);
                            return msg;
                        }else {
                            msg = Msg.fail();
                            msg.setMsg("腾讯云账号注册失败");
                            return msg;
                        }
                    }

                }

            }
        }

        return null;
    }

    @Override
    public Msg getUserInfo(int uId, String token) {
        Msg msg;
        if(RedisUtil.authToken(String.valueOf(uId),token)){
            User user = userMapper.selectByPrimaryKey(uId);
            msg = Msg.success();
            msg.add("bgImg",user.getBgPicture());
            msg.add("money",user.getMoney());
            msg.add("nickName",user.getNickname());
            msg.add("header",user.getHeader());
            msg.add("account",user.getAccount());
            msg.add("uId",user.getId());
            msg.add("signature",user.getSignature());
            msg.add("sex",user.getSex());
            msg.add("age",user.getBirthday());
            int state = tencentImService.getUserState(user.getAccount());
            if(state==1){
               msg.add("state","在线");
            }else{
                msg.add("state","离线");
            }
            return msg;
        }else {
            System.out.println("鉴权失败");
            return Msg.noLogin();
        }
    }

    @Override
    @Transactional
    public void redPackageOverdue(int hid) {
        RedPacket redPacket = redPacketMapper.selectByPrimaryKey(hid);
        if(redPacket!=null){
            redPacket.setState(2);//2为过期
            int sender_id  = redPacket.getSenderId();
            redPacketMapper.updateByPrimaryKey(redPacket);
            addMoney(sender_id,redPacket.getMoney());
        }
    }

    @Override
    public Msg getOtherMsg(String account) {
        Msg msg;
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        List<User> users = userMapper.selectByExample(example);
        if(users.size()>0){
            User user = users.get(0);
            msg = Msg.success();
            msg.add("uId",user.getId());
            msg.add("nickName",user.getNickname());
            msg.add("sex",user.getSex());
            msg.add("old",user.getBirthday());
            msg.add("account",user.getAccount());
            msg.add("bgImg",user.getBgPicture());
            msg.add("header",user.getHeader());
            msg.add("signature",user.getSignature());
            return msg;
        }else {
            msg = Msg.fail();
            msg.setCode(1000);
            msg.setMsg("没有相关用户");
            return msg;
        }

    }

    @Override
    public List<Map<String,Object>> selUserInfoByPage(int page, int number) {
        List<Map<String,Object>> list = new ArrayList<>();
        UserExample example = new UserExample();
        int offset = number*(page-1);
        example.setPageNo(offset);
        example.setPageSize(number);

        List<User> users = userMapper.selectByExample(example);
        for(User user : users){
            Map<String,Object> map = new HashMap<>();
            AuthorizationExample example1 = new AuthorizationExample();
            AuthorizationExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andUserIdEqualTo(user.getId());
            criteria1.andIdentityTypeEqualTo(1);
            List<Authorization> authorizations = authMapper.selectByExample(example1);
            if(authorizations==null||authorizations.size()==0){
                map.put("verified",false);
                map.put("phone","未绑定");
            }else{
                map.put("verified",true);
                Authorization authorization = authorizations.get(0);
                map.put("phone",authorization.getIdentifier());
            }
            map.put("id",user.getId());
            map.put("account",user.getAccount());
            if(user.getSex()){
                map.put("sex","女");
            }
            else {
                map.put("sex","男");
            }
            map.put("age",user.getBirthday());
            map.put("nickName",user.getNickname());
            map.put("createTime", DateUtil.formatDateTime(user.getCreateTime()));
            map.put("money",user.getMoney());
            map.put("close",user.getBoolClose());
            list.add(map);
        }
       return list;
    }

    @Override
    public List<User> selAllUser() {
       return userMapper.selectByExample(null);
    }

    @Override
    public User selUserInfoByAdmin(String account) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        List<User> users = userMapper.selectByExample(example);
        if(users.size()>0){
            User user = users.get(0);
            return user;
        }
        return null;
    }

    @Override
    public Msg videoEnd(String channelId) {

        VideoChatExample example = new VideoChatExample();
        VideoChatExample.Criteria criteria = example.createCriteria();
        criteria.andChannelIdEqualTo(channelId);
        VideoChat videoChat = videoChatMapper.selectByExample(example).get(0);
        String sender  = videoChat.getSenderAccount();
        String receiver  = videoChat.getReceiverAccount();
        UserExample example1 = new UserExample();
        UserExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andAccountEqualTo(sender);
        User senderU = userMapper.selectByExample(example1).get(0); //呼叫放方
        UserExample example2 = new UserExample();
        UserExample.Criteria criteria2 = example2.createCriteria();
        criteria2.andAccountEqualTo(receiver);
        User receiverU = userMapper.selectByExample(example2).get(0);//接收方
        Date date = new Date();
        Date videoDate = videoChat.getGreateTime();
        int minute = getDatePoor(date,videoDate);//通话时长
        float videoRate = Float.parseFloat(rootMessageService.getMessageByName("video_rate"));
        float videoMoney = Float.parseFloat(rootMessageService.getMessageByName("video_money"));
        float totalMoney = minute*videoMoney;//总费用
        float lateMoney = minute*videoMoney*(1-videoRate);//接收方得到费用
        videoChat.setMoney((double)totalMoney*videoRate);
        videoChat.setState(1);
        if(addMoney(receiverU.getId(),(int)lateMoney)&& delMoney(senderU.getId(),(int)totalMoney)&&videoChatMapper.updateByPrimaryKey(videoChat)==1){
            return Msg.success();
        }else {
            return Msg.fail();
        }

    }

    @Override
    public Msg cashAudit(int cashId, int result) {
        Msg msg;
        Cash cash = cashMapper.selectByPrimaryKey(cashId);
        //通过
        if(result==1){
            if(cash.getState()==0){
                cash.setState(1);
                if(cashMapper.updateByPrimaryKey(cash)==1){
                    User user = userMapper.selectByPrimaryKey(cash.getCashId());
                    AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
                    request.setBizContent("{" + "\"out_biz_no\":\"" + cash.getCashNumber() + "\","
                            + "\"payee_type\":\"ALIPAY_LOGONID\"," + "\"payee_account\":\"" + user.getAlipayAccount() + "\","
                            + "\"amount\":\"" + cash.getCashMoney() + "\"," + "\"payer_show_name\":\"美游提现\","
                            + "\"payee_real_name\":\"" + user.getAlipayName() + "\"," + "\"remark\":\"提现\"" + "  }");
                    AlipayFundTransToaccountTransferResponse response = null;
                    try {
                        response = alipayClient.execute(request);
                        if (response.isSuccess()) {
                            msg = Msg.success();
                            return msg;
                        } else {
                            System.out.println("调用失败");
                            msg = Msg.fail();
                            return msg;
                        }
                    } catch (AlipayApiException e) {
                        e.printStackTrace();
                    }
                }
            }
            //不通过
            if(result==0){
                if(cash.getState()==0){
                    cash.setState(2);
                    if(cashMapper.updateByPrimaryKey(cash)==1){
                        return Msg.success();
                    }
                }
            }
        }

        return Msg.fail();
    }

    @Override
    public Msg selUserInfoById(int uId) {
        User user = userMapper.selectByPrimaryKey(uId);
        Msg msg = Msg.success();
        msg.add("uId",uId);
        msg.add("account",user.getAccount());
        msg.add("nickName",user.getNickname());
        msg.add("header",user.getHeader());
        msg.add("bgImg",user.getBgPicture());
        msg.add("signature",user.getSignature());
        if(user.getSex()){
            msg.add("sex","女");
        }else {
            msg.add("sex","男");
        }
        int i = tencentImService.getUserState(user.getAccount());
        if(i==1){
            msg.add("state","在线");
        }
        else{
            msg.add("state","离线");
        }
        msg.add("age",user.getBirthday());

        return msg;
    }

    /**
     * 用户举报
     * @param reporter_id [举报人]
     * @param reported_id [被举报人]
     * @param type [举报类型]
     * @param content [举报备注]
     * @return
     */
    @Override
    public Msg userReport(Integer reporter_id, Integer reported_id,  String type, String content) {
        Msg msg = new Msg();
        //先查询举报人是否存在
        User user = userMapper.selectByPrimaryKey(reporter_id);
        //查询被举报人是否存在
        User user1 = userMapper.selectByPrimaryKey(reported_id);
        if (user == null || user1 == null) {
            msg.setCode(200);
            msg.setMsg("举报人或被举报人不存在");
            msg.add("黄朝阳", "666");
            return msg;
        }
        UserReport userReport = new UserReport();
        userReport.setReporterId(reporter_id);
        userReport.setReportedPersonId(reported_id);
        userReport.setCreateTime(new Date());
        userReport.setUpdateTime(new Date());
        userReport.setType(type);
        userReport.setContent(content);
        int i = userReportMapper.insertSelective(userReport);
        if (i == 0) {
            msg.setCode(200);
            msg.setMsg("插入数据到数据库失败");
            return msg;
        }
        msg.setCode(100);
        msg.setMsg("举报成功！");
        return msg;
    }


    @Override
    public Msg selRedPackage(int id) {
        int state = redPacketMapper.selectByPrimaryKey(id).getState();
        return Msg.success().add("state",state);
    }

    @Override
    @Transactional
    public Msg getRedPackage(int id) {
        RedPacket redPacket = redPacketMapper.selectByPrimaryKey(id);
        if(redPacket.getState()==0){
            redPacket.setState(1);
            Date date = new Date();
            redPacket.setUpdateTime(date);
            if(redPacketMapper.updateByPrimaryKey(redPacket)==1){
                    if(addMoney(redPacket.getReceiveId(),redPacket.getMoney())){
                        return Msg.success();
                    }
            }
        }
        else if(redPacket.getState()==2){
            Msg msg = Msg.success();
            msg.setCode(1000);
            return msg;
        }
        return Msg.fail();
    }


    /**
     * 字符串日期换算年龄
     * @param birth
     * @return
     * @throws ParseException
     */
    public  int getAgeByBirth(String birth) throws ParseException {
        Date birthDay = new SimpleDateFormat("yyyy-M-dd").parse(birth);
        int age = 0;
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }

    public int getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;

        long nh = 1000 * 60 * 60;

        long nm = 1000 * 60;

        // 获得两个时间的毫秒时间差异

        long diff = endDate.getTime() - nowDate.getTime();

        long min = diff % nd % nh / nm;

        return (int)min;
    }

    /**
     * 忘记密码
     * @param phone 电话号码
     * @param code 验证码
     * @param password 新密码
     * @return
     */
    @Override
    public Msg forgetPassword(String phone, String code, String password) {
        Msg msg = new Msg();
        if(RedisUtil.authCode(phone, code)){
            AuthorizationExample authorizationExample = new AuthorizationExample();
            authorizationExample.createCriteria().andIdentifierEqualTo(phone);
            List<Authorization> authorizations = authMapper.selectByExample(authorizationExample);
            if(authorizations.isEmpty()){
                msg.setCode(404);
                msg.setMsg("此用户不存在");
                return msg;
            }

            Authorization authorization = new Authorization();
            authorization.setCredential(password);
            authorization.setUpdateTime(new Date());

            AuthorizationExample example = new AuthorizationExample();
            example.createCriteria().andIdentifierEqualTo(phone);
            authMapper.updateByExampleSelective(authorization,example);

            return Msg.success();
        }
        msg.setCode(500);
        msg.setMsg("验证码错误");
        return msg;
    }
}
