package com.meiyou.service;

import com.meiyou.pojo.ShopStar;

import java.util.List;

/**
 * @description: 景点商家评论星级业务层接口
 * @author: Mr.Z
 * @create: 2019-08-21 20:41
 **/
public interface ShopStarService {

    /**
     * 添加景点商家评星
     */
    void addShopStar(ShopStar shopStar);

    /**
     * 通过景点商家id查找全部评论的星级
     * @param id
     * @return
     */
    List<Integer> selectStar(Integer id);

}
