package com.meiyou.service.impl;

import com.meiyou.mapper.AuthorizationMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.pojo.Authorization;
import com.meiyou.pojo.AuthorizationExample;
import com.meiyou.pojo.User;
import com.meiyou.service.UserService;
import com.meiyou.utils.Msg;
import io.netty.util.internal.logging.Log4JLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(authorizations.size()==1){   //授权通过
            Authorization authorization  = authorizations.get(0);
            int uid = authorization.getUserId();//用户id
            User user = userMapper.selectByPrimaryKey(uid);//查询用户
            Msg msg = Msg.success();
            msg.add("uid",user.getId());
            msg.add("account",user.getAccount());
            msg.add("header",user.getHeader());
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
