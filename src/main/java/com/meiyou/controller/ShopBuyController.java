package com.meiyou.controller;

import com.meiyou.pojo.ShopBuy;
import com.meiyou.service.ShopBuyService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: Mr.Z
 * @create: 2019-08-23 14:50
 **/
@RestController
@Api(value = "购买景点商家的控制器",tags = "购买景点商家")
@RequestMapping("/shopBuy")
public class ShopBuyController {

    @Autowired
    ShopBuyService shopBuyService;

    @PostMapping("/add")
    @ApiOperation(value = "购买景点商家",notes = "1000-请设置支付密码!,1001-支付密码错误!,1002-发布失败,账户余额不足!")
    public Msg addShopBuy(@RequestParam("uid") Integer uid,
                          @RequestParam("token") String token,
                          @RequestParam("gid") Integer gid,
                          @RequestParam("time") Integer time,
                          @RequestParam("password") Integer password){

        ShopBuy shopBuy = new ShopBuy();
        shopBuy.setBuyerId(uid);
        shopBuy.setGuideId(gid);
        shopBuy.setTime(time);
        return shopBuyService.addShopBuy(shopBuy,token,password);
    }

    @PutMapping("/update")
    @ApiOperation(value = "取消聘请的同城导游",notes = "取消即更发布状态，实际数据不删除")
    public Msg updateShop(@RequestParam("uid") Integer uid,
                          @RequestParam("token") String token,
                          @RequestParam("sid") Integer sid){
        return shopBuyService.updateShopBuy(uid, token, sid);
    }

    @GetMapping("/get")
    @ApiOperation(value = "通过用户id查找指定用户id聘请的全部同城导游",notes = "查找")
    public Msg getClubByUid(@RequestParam("uid") Integer uid,
                            @RequestParam("token") String token){
        return shopBuyService.selectByUid(uid,token);
    }

    @GetMapping("/find")
    @ApiOperation(value = "通过同城导游记录id(shopBuy_id)查找对应的同城导游信息",notes = "查找")
    public Msg findClubByCid(@RequestParam("uid") Integer uid,
                             @RequestParam("token") String token,
                             @RequestParam("sid") Integer sid){
        return shopBuyService.selectBySid(uid,token,sid);
    }
}
