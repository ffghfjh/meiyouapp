package com.meiyou.controller;

import com.meiyou.pojo.Shop;
import com.meiyou.service.ShopService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 同城导游控制器
 * @author: Mr.Z
 * @create: 2019-08-23 11:30
 **/
@RestController
@Api(value = "同城导游控制器",tags = "同城导游")
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    ShopService shopService;

    @PostMapping("/add")
    @ApiOperation(value = "发布同城导游",notes = "1000-请设置支付密码!,1001-支付密码错误!,1002-发布失败,账户余额不足!")
    public Msg addShop(@RequestParam("publish_id") Integer publishId,
                       @RequestParam("token") String token,
                       @RequestParam("service_area") String service_area,
                       @RequestParam("travel_time") String travel_time,
                       @RequestParam("charge") Integer charge,
                       @RequestParam("imgs_url") String imgsUrl,
                       @RequestParam("time") Integer time,
                       @RequestParam("password") String password){
        Shop shop = new Shop();
        shop.setPublishId(publishId);
        shop.setImgsUrl(imgsUrl);
        shop.setServiceArea(service_area);
        shop.setTravelTime(travel_time);
        shop.setCharge(charge);
        return shopService.addShop(shop,token,time,password);
    }

    @PutMapping("/update")
    @ApiOperation(value = "取消发布聘请同城导游",notes = "取消即更发布状态，实际数据不删除")
    public Msg updateShop(@RequestParam("uid") Integer uid,
                          @RequestParam("token") String token,
                          @RequestParam("sid") Integer sid){
        return shopService.updateShop(uid,token,sid);
    }

    @GetMapping("/get")
    @ApiOperation(value = "通过用户id查找指定用户id发布的全部同城导游",notes = "查找")
    public Msg getClubByUid(@RequestParam("uid") Integer uid,
                            @RequestParam("token") String token){
        return shopService.selectByUid(uid,token);
    }

    @GetMapping("/find")
    @ApiOperation(value = "通过同城导游id查找对应的同城导游信息",notes = "查找")
    public Msg findClubByCid(@RequestParam("uid") Integer uid,
                             @RequestParam("token") String token,
                             @RequestParam("sid") Integer sid){
        return shopService.selectBySid(uid,token,sid);
    }
}
