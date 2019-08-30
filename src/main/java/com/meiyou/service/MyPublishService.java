package com.meiyou.service;

import com.meiyou.model.AppointmentVO;
import com.meiyou.model.ClubVO;
import com.meiyou.model.ShopVO;
import com.meiyou.model.TourVO;
import com.meiyou.utils.Msg;

import java.util.List;

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
    List<AppointmentVO> selectAppointmentList(String uid, String token);

    /**
     * 查询我的旅游发布
     */
    List<TourVO> selectTourList(String uid, String token);


    /**
     * 通过用户id查找全部发布的会所
     * @param uid
     * @return
     */
    List<ClubVO> selectClubByUid(Integer uid, String token);

    /**
     * 查找指定用户所发布的景点商家(同城导游)
     * @param uid
     * @return
     */
    List<ShopVO> selectShopByUid(Integer uid, String token);
}
