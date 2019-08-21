package com.meiyou.service.impl;

import com.meiyou.pojo.Shop;
import com.meiyou.service.ShopService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 景点商家(同城导游)业务层实现类
 * @author: Mr.Z
 * @create: 2019-08-21 17:38
 **/
@Service
public class ShopServiceImpl implements ShopService{

    @Override
    public void addShop(Shop shop) {

    }

    @Override
    public void deleteShop(Integer id) {

    }

    @Override
    public List<Shop> selectByUid(Integer id) {
        return null;
    }

    @Override
    public Shop selectBySid(Integer id) {
        return null;
    }

    @Override
    public List<Shop> selectShop(float longitude, float latitude) {
        return null;
    }
}
