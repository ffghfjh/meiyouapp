package com.meiyou.service.impl;

import com.meiyou.mapper.RootMessageMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.model.Coordinate;
import com.meiyou.pojo.RootMessageExample;
import com.meiyou.pojo.User;
import com.meiyou.utils.Constants;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
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

    /**
     * 发布添加地理位置
     * @param latitude 经度
     * @param longitude 纬度
     * @param key 活动的id
     * @param value
     * @return
     */
    public Boolean setPosition(Double latitude, Double longitude, Integer key, String value){
        //添加地理位置和aid到Redis缓存中
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(latitude);
        coordinate.setLongitude(longitude);
        coordinate.setKey(Integer.toString(key));
        Long reo = RedisUtil.addReo(coordinate, value);
        if (reo == null) {
            return false;
        }
        return true;
    }
}
