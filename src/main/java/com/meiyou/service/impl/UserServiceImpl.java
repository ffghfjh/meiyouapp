package com.meiyou.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.meiyou.mapper.AuthorizationMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.pojo.Authorization;
import com.meiyou.pojo.AuthorizationExample;
import com.meiyou.pojo.User;
import com.meiyou.service.UserService;
import com.meiyou.utils.Constants;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import io.netty.util.Constant;
import io.netty.util.internal.logging.Log4JLoggerFactory;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    AuthorizationMapper authMapper;


    // 支付宝调用接口之前的初始化
    AlipayClient alipayClient = new DefaultAlipayClient(Constants.ALIURL, Constants.APP_ID,
            Constants.APP_PRIVATE_KEY, Constants.FORMAT, Constants.CHARSET, Constants.ALIPAY_PUBLIC_KEY,
            Constants.SIGN_TYPE);

    @Override
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
                criteria.andIdentifierEqualTo(alipayUserId);

                //是否存在该支付宝用户
                List<Authorization> authorizations = authMapper.selectByExample(example);
                if(authorizations.size()>0){
                    Authorization authorization = authorizations.get(0);
                    if(authorization.getBoolVerified()){

                    }else {
                      msg = Msg.fail();
                      msg.setCode(1000);
                    }
                }
                else{

                }
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return null;
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
            String token = UUID.randomUUID().toString();//UUID生成token
            if(!RedisUtil.setToken(String.valueOf(user.getId()),token)){
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



}
