package com.meiyou.service.impl;

import com.meiyou.mapper.AuthorizationMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.pojo.Authorization;
import com.meiyou.pojo.AuthorizationExample;
import com.meiyou.pojo.User;
import com.meiyou.pojo.UserExample;
import com.meiyou.service.OwnService;
import com.meiyou.utils.FileUploadUtil;
import com.meiyou.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    public Msg changeHeader(Integer uid, MultipartFile img, HttpServletRequest req) {
        Msg fileMsg = FileUploadUtil.uploadUtil(img, "", req);
        Msg msg = new Msg();
        if(fileMsg.getCode() != 100){
            msg.setCode(500);
            msg.setMsg("头像上传失败");
            return msg;
        }
        User user = new User();
        user.setHeader(fileMsg.getExtend().get("path").toString());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(uid);
        Integer rows = userMapper.updateByExampleSelective(user, userExample);
        if(rows != 1){
            msg.setMsg("更新用户头像失败");
            msg.setCode(501);
            return msg;
        }
        msg.setCode(100);
        msg.setMsg("更新头像成功");
        return msg;
    }

    @Override
    public Msg changePassword(Integer uid, String newPassword) {
        Msg msg = new Msg();
        User user = getUserByUid(uid);
        if(user == null){
            msg.setCode(300);
            msg.setMsg("此用户不存在");
            return msg;
        }
        Authorization authorization = new Authorization();
        authorization.setCredential(newPassword);

        AuthorizationExample example = new AuthorizationExample();
        example.createCriteria().andUserIdEqualTo(uid).andIdentityTypeEqualTo(1);
        Integer rows = authorizationMapper.updateByExampleSelective(authorization, example);
        if(rows != 1){
            return Msg.fail();
        }
        return Msg.success();
    }

    @Override
    public Msg changePayPassword(Integer uid, String newPassword) {
        Msg msg = new Msg();
        User user = getUserByUid(uid);
        if(user == null){
            msg.setCode(300);
            msg.setMsg("此用户不存在");
            return msg;
        }
        User u = new User();
        u.setId(uid);
        u.setPayWord(newPassword);
        Integer rows = userMapper.updateByPrimaryKeySelective(u);
        if(rows != 1){
            return Msg.fail();
        }
        return Msg.success();
    }

    @Override
    public Msg selectByUid(Integer uid) {
        Msg msg = new Msg();
        User user = getUserByUid(uid);
        if(user == null){
            msg.setCode(300);
            msg.setMsg("此用户不存在");
            return msg;
        }
        msg.add("user",user);
        msg.setMsg("成功");
        msg.setCode(100);
        return msg;
    }
}
