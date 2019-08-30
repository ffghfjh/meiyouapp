package com.meiyou.service;

import com.meiyou.model.AppointmentVO;
import com.meiyou.model.ClubVO;
import com.meiyou.model.ShopVO;
import com.meiyou.model.TourVO;

import java.util.List;

import java.util.List;

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
    List<AppointmentVO> selectMyAppointmentAsk(String uid, String token);

    /**
    * @Description: 查询我的旅游报名
    * @Author: JK
    * @Date: 2019/8/26
    */
    List<TourVO> selectMyTourAsk(String uid, String token);

    /**
     * 查询我的会所购买
     * @param uid
     * @return
     */
    List<ClubVO> selectMyClubAsk(Integer uid);

    /**
     * 查询我的导游聘请
     * @param uid
     * @return
     */
    List<ShopVO> selectMyShopAsk(Integer uid);
}
