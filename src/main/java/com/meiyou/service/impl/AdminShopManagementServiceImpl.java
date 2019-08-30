package com.meiyou.service.impl;

import com.meiyou.mapper.ShopMapper;
import com.meiyou.pojo.Shop;
import com.meiyou.pojo.ShopExample;
import com.meiyou.service.AdminShopManagementService;
import com.meiyou.utils.LayuiDataUtil;
import com.meiyou.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
    * @Description: 分页查询所有的导游
    * @Author: JK
    * @Date: 2019/8/29
    */
    @Override
    public Map<String, Object> selectAllShopByPage(Integer pageNo, Integer pageSize, Integer publisherId, Integer state) {
        Page<Shop> page = new Page<>();
        Integer offset = pageSize * (pageNo - 1);
        ShopExample shopExample = new ShopExample();

        if (publisherId != null){
            shopExample.createCriteria().andPublishIdEqualTo(publisherId);
            List<Shop> list = shopMapper.selectByExample(shopExample);
            if (list.size() == 0){
                Map<String, Object> map = new HashMap<>();
                map.put("code",200);
                map.put("msg","没有数据");
                return map;
            }
            shopExample.setPageNo(offset);
            shopExample.setPageSize(pageSize);
            List<Shop> tours = shopMapper.selectByExample(shopExample);
            page.setCount(list.size());
            page.setList(tours);
            return LayuiDataUtil.getLayuiData(page);
        }

        if (state != null){
            shopExample.createCriteria().andStateEqualTo(state);
            List<Shop> list = shopMapper.selectByExample(shopExample);
            if (list.size() == 0){
                Map<String, Object> map = new HashMap<>();
                map.put("code",200);
                map.put("msg","没有数据");
                return map;
            }
            shopExample.setPageNo(offset);
            shopExample.setPageSize(pageSize);
            List<Shop> appointments = shopMapper.selectByExample(shopExample);
            page.setCount(list.size());
            page.setList(appointments);
            return LayuiDataUtil.getLayuiData(page);
        }


        List<Shop> list = shopMapper.selectByExample(shopExample);
        if (list.size() == 0){
            Map<String, Object> map = new HashMap<>();
            map.put("code",200);
            map.put("msg","没有数据");
            return map;
        }

        shopExample.setPageNo(offset);
        shopExample.setPageSize(pageSize);
        List<Shop> appointments = shopMapper.selectByExample(shopExample);
        page.setCount(list.size());
        page.setList(appointments);
        return LayuiDataUtil.getLayuiData(page);
    }
}
