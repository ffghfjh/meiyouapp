package com.meiyou.service;

import com.meiyou.pojo.Shop;

import java.util.List;

/**
 * @description: 景点商家(同城导游)业务层接口
 * @author: Mr.Z
 * @create: 2019-08-21 17:37
 **/
public interface ShopService {

    /**
     * 发布景点商家(同城导游)
     * @param shop
     */
    void addShop(Shop shop);

    /**
     * 删除对应id的景点商家(同城导游)
     * @param id
     */
    void deleteShop(Integer id);

    /**
     * 查找指定用户所发布的景点商家(同城导游)
     * @param id
     * @return
     */
    List<Shop> selectByUid(Integer id);

    /**
     * 查找景点商家(同城导游)id的详细信息
     * @param id
     * @return
     */
    Shop selectBySid(Integer id);
}
