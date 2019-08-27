package com.meiyou.service;

import com.meiyou.utils.Msg;

/**
 * @program: meiyou
 * @description: 我的报名接口
 * @author: JK
 * @create: 2019-08-26 15:17
 **/
public interface MyAskService {

    /**
    * @Description: 查询我的约会报名
    * @Author: JK
    * @Date: 2019/8/26
    */
    Msg selectMyAppointmentAsk(String uid, String token);

    /**
    * @Description: 查询我的旅游报名
    * @Author: JK
    * @Date: 2019/8/26
    */
    Msg selectMyTourAsk(String uid, String token);

    /**
     * 查询我的会所购买
     * @param uid
     * @return
     */
  //  Msg selectMyClubAsk(Integer uid, String token);

    /**
     * 查询我的导游聘请
     * @param uid
     * @param token
     * @return
     */
   // Msg selectMyShopAsk(Integer uid, String token);
}
