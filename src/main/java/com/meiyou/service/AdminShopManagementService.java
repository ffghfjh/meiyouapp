package com.meiyou.service;

import com.meiyou.pojo.Shop;

import java.util.List;
import java.util.Map;

/**
 * @description: 景点商家管理员服务层接口
 * @author: Mr.Z
 * @create: 2019-08-28 10:54
 **/
public interface AdminShopManagementService {

    List<Shop> selectAll();

    Shop selectBySid(Integer sid);

    /**
     * 分页查询所有的导游
     */
    Map<String,Object> selectAllShopByPage(Integer pageNo, Integer pageSize, Integer publisherId, Integer state);
}
