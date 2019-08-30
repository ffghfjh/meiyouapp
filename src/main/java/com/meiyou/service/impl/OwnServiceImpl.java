package com.meiyou.service.impl;

import com.meiyou.mapper.AuthorizationMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.pojo.Authorization;
import com.meiyou.pojo.AuthorizationExample;
import com.meiyou.pojo.User;
import com.meiyou.pojo.UserExample;
import com.meiyou.service.OwnService;
import com.meiyou.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 个人用户业务层接口实现类
 * @author: Mr.Z
 * @create: 2019-08-30 20:13
 **/
@Service
public class OwnServiceImpl extends BaseServiceImpl implements OwnService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    AuthorizationMapper authorizationMapper;

    @Override
    public Msg changeInfo(User user) {
        Msg msg = new Msg();

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(user.getId());
        Integer rows = userMapper.updateByExampleSelective(user, userExample);
        if(rows == 0){
            Msg.fail();
        }
        msg.setCode(100);
        msg.setMsg("成功");
        return msg;
    }

    @Override
    public Msg changePassword(Integer uid, String newPassword) {
        Msg msg = new Msg();
        AuthorizationExample authorizationExample = new AuthorizationExample();
        authorizationExample.createCriteria().andUserIdEqualTo(uid).andIdentityTypeEqualTo(1);
        List<Authorization> authorizations = authorizationMapper.selectByExample(authorizationExample);
        if(authorizations.isEmpty() && authorizations == null){
            msg.setMsg("不存在此用户");
            msg.setCode(300);
            return msg;
        }
        Authorization authorization = new Authorization();
        authorization.setCredential(newPassword);

        AuthorizationExample example = new AuthorizationExample();
        example.createCriteria().andUserIdEqualTo(uid);
        authorizationMapper.updateByExampleSelective(authorization,example);
        return null;
    }
}
