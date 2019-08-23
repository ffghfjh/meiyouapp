package com.meiyou.service;

import com.meiyou.pojo.Shop;
import com.meiyou.utils.Msg;


/**
 * @description: 景点商家(同城导游)业务层接口
 * @author: Mr.Z
 * @create: 2019-08-21 17:37
 **/
public interface ShopService {

    /**
     * 发布景点商家(同城导游)
     * @param shop
     * @param token
     * @param time
     * @param password
     * @return
     */
    Msg addShop(Shop shop, String token, Integer time, String password, Double latitude, Double longitude);

    /**
     * 取消发布对应id的景点商家(同城导游)
     * @param uid
     * @param token
     * @param sid
     * @return
     */
    Msg updateShop(Integer uid,String token,Integer sid);

    /**
     * 查找指定用户所发布的景点商家(同城导游)
     * @param uid
     * @return
     */
    Msg selectByUid(Integer uid,String token);

    /**
     * 查找景点商家(同城导游)id的详细信息
     * @param sid
     * @return
     */
    Msg selectBySid(Integer uid,String token,Integer sid);

    /**
     * 根据经纬度查找附近的景点商家
     * @param longitude
     * @param latitude
     * @return
     */
    Msg selectShop(float longitude, float latitude);
}
