package com.meiyou.service;

import com.meiyou.pojo.ShopBuy;

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
     */
    void addShopBuy(ShopBuy shopBuy);

    /**
     * 通过景点商家(同城导游)id 取消购买指定的景点商家(同城导游)
     * @param id
     */
    void deleteShopBuy(Integer id);

    /**
     * 通过用户id查找全部景点商家(同城导游)的购买记录
     * @return
     */
    List<ShopBuy> selectByUid();

    /**
     * 通过id查找指定的景点商家(同城导游)购买记录
     * @return
     */
    ShopBuy selectBySid();
}
