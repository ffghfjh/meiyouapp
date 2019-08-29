package com.meiyou.service.impl;

import com.meiyou.mapper.ShopMapper;
import com.meiyou.pojo.Shop;
import com.meiyou.pojo.ShopExample;
import com.meiyou.service.AdminShopManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Mr.Z
 * @create: 2019-08-28 14:34
 **/
@Service
public class AdminShopManagementServiceImpl implements AdminShopManagementService {

    @Autowired
    ShopMapper shopMapper;

    @Override
    public List<Shop> selectAll() {
        return findAll();
    }

    @Override
    public Shop selectBySid(Integer sid) {
        return shopMapper.selectByPrimaryKey(sid);
    }

    /**
     * 查找全部发布的同城导游
     * @return
     */
    public List<Shop> findAll(){
        ShopExample shopExample = new ShopExample();
        shopExample.createCriteria();
        List<Shop> shops = shopMapper.selectByExample(shopExample);
        return shops;
    }
}
