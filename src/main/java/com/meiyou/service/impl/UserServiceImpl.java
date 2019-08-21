package com.meiyou.service.impl;

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


    @Override
    public Msg alipayLogin(String auth_code) {

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
