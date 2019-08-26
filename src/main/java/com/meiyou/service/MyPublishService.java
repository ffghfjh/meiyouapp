package com.meiyou.service;

import com.meiyou.utils.Msg;

/**
 * @program: meiyou
 * @description: 我的发布接口
 * @author: JK
 * @create: 2019-08-26 15:03
 **/
public interface MyPublishService {

    /**
     * 查询我的约会发布
     */
    Msg selectAppointmentList(String uid,String token);

    /**
     * 查询我的旅游发布
     */
    Msg selectTourList(String uid, String token);
}
