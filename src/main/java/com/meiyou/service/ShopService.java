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
     * @param timeType
     * @param password
     * @return
     */
    Msg addShop(Shop shop, String token, String timeType, String password, Double longitude, Double latitude);

    /**
     * 取消发布对应id的景点商家(同城导游)
     * @param uid
     * @param token
     * @param sid
     * @return
     */
    Msg updateShop(Integer uid,String token,Integer sid);

    /**
     * 发布者不想看这个发布的了
     * @param uid
     * @param token
     * @param shopId
     * @return
     */
    Msg updateShopIgnore(Integer uid,String token,Integer shopId);

    /**
     * 修改发布的景点商家的状态为已删除-->3
     * @param uid
     * @param token
     * @param shopBuyId
     * @return
     */
    Msg updateShopBuyDelete(Integer uid,String token,Integer shopBuyId);

    /**
     * 查找景点商家(同城导游)id的详细信息
     * @param uid
     * @param token
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
    Msg selectShopByPosition(Integer uid, String token, Double longitude, Double latitude);
}
