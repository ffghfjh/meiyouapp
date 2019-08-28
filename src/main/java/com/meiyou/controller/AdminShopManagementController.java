package com.meiyou.controller;

import com.meiyou.pojo.Shop;
import com.meiyou.service.AdminShopManagementService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: Mr.Z
 * @create: 2019-08-28 11:10
 **/
@RestController
@Api(value = "管理员同城导游控制器",tags = "用来查看整个同城导游情况")
@RequestMapping("/admin")
public class AdminShopManagementController {

    @Autowired
    AdminShopManagementService service;

    @PostMapping("/findAllShop")
    @ApiOperation(value = "查找全部同城导游",notes = "查找")
    public Msg findAllShop(){
        Msg msg = new Msg();
        List<Shop> shops = service.selectAll();
        if(shops == null && shops.size() == 0){
            return Msg.fail();
        }
        msg.setCode(100);
        msg.setMsg("已找到");
        msg.add("shops",shops);
        return msg;
    }
}
