package com.meiyou.service;

import com.meiyou.pojo.ShopBuy;
import com.meiyou.utils.Msg;

import java.util.List;

/**
 * @description: 景点商家(同城导游)购买的业务层接口
 * @author: Mr.Z
 * @create: 2019-08-21 17:38
 **/
public interface ShopBuyService {

    /**
     * 添加景点商家(同城导游)购买记录
     * @param shopBuy
     * @param token
     * @param password
     * @return
     */
    Msg addShopBuy(ShopBuy shopBuy, String token,Integer password);

    /**
     * 通过景点商家(同城导游)id 取消购买指定的景点商家(同城导游)
     * @param uid
     * @param token
     * @param sid
     * @return
     */
    Msg updateShopBuy(Integer uid, String token, Integer sid);

    /**
     * 查找用户的景点商家(同城导游)购买记录
     * @param uid
     * @param token
     * @return
     */
    Msg selectByUid(Integer uid, String token);

    /**
     * 查找指定的景点商家(同城导游)购买记录
     * @param uid
     * @param token
     * @param sid
     * @return
     */
    Msg selectBySid(Integer uid, String token,Integer sid);
}
