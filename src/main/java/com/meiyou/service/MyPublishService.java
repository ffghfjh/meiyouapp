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


    /**
     * 通过用户id查找全部发布的会所
     * @param uid
     * @return
     */
    Msg selectClubByUid(Integer uid, String token);

    /**
     * 查找指定用户所发布的景点商家(同城导游)
     * @param uid
     * @return
     */
    Msg selectShopByUid(Integer uid,String token);
}
