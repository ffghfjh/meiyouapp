package com.meiyou.service;

import com.meiyou.pojo.Activity;
import com.meiyou.utils.Msg;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/21 14:06
 * @description：附近动态服务层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
public interface ActivityService {

    //发布动态
    int postActivity(Activity activity);

    //删除动态
    int removeActivity(int aid);

    //通过用户id删除所有动态
    int removeAllActivityByUid();

    //修改动态
    int updateActivity(Activity activity);

    //查询我的单个动态
    Activity getActivityByAid(int aid);

    //通过用户id查询他的所有动态
    List<Activity> listActivityByUid(int uid);

    //获取所有动态
    List<Activity> listActivity();

    //通过经纬度获取所有动态
    List<Activity> listActivityBy(float longitude, float latitude);



}