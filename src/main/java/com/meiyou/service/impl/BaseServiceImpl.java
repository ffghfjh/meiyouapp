package com.meiyou.service.impl;

import com.meiyou.mapper.RootMessageMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.pojo.RootMessageExample;
import com.meiyou.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 重复写的一些方法，进行了简单封装，可以一起用
 * @author: Mr.Z
 * @create: 2019-08-23 08:37
 **/
public class BaseServiceImpl {

    @Autowired
    RootMessageMapper rootMessageMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 获取系统数据表数据
     * @param message 需要在系统数据表获取的参数名称
     * @return
     */
    public String getRootMessage(String message){
        RootMessageExample rootMessageExample = new RootMessageExample();
        rootMessageExample.createCriteria().andNameEqualTo(message);
        String result = rootMessageMapper.selectByExample(rootMessageExample).get(0).getValue();
        return result;
    }

    /**
     * 通过用户id查找用户
     * @param uid
     * @return
     */
    public User getUserByUid(Integer uid){
        return userMapper.selectByPrimaryKey(uid);
    }
}
