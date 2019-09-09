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
    @ApiOperation(value = "购买景点商家",notes = "501-自己不允许购买,1000-请设置支付密码!,1001-支付密码错误!,1002-发布失败,账户余额不足!")
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

    @PostMapping("/addStar")
    @ApiOperation(value = "评星",notes = "对状态为已完成的导游记录进行评星")
    public Msg addShopStar(@RequestParam("uid") Integer uid,
                           @RequestParam("token") String token,
                           @RequestParam("sid") Integer cid,
                           @RequestParam("star") Integer star){
        return shopBuyService.addShopStar(uid,token,cid,star);
    }

    @PostMapping("/update")
    @ApiOperation(value = "取消聘请的同城导游",notes = "sid 为聘请经典商家的这条记录的Id,取消即更发布状态，实际数据不删除")
    public Msg updateShop(@RequestParam("uid") Integer uid,
                          @RequestParam("token") String token,
                          @RequestParam("sid") Integer sid){
        return shopBuyService.updateShopBuy(uid, token, sid);
    }

    @PostMapping("/updateComplete")
    @ApiOperation(value = "更改购买景点商家的状态为已到店(已完成)",notes = "sid 为聘请经典商家的这条记录的Id,修改状态为已赴约(已完成)--->>1")
    public Msg updateShopBuyComplete(@RequestParam("uid") Integer uid,
                                     @RequestParam("token") String token,
                                     @RequestParam("sid") Integer sid){
        return shopBuyService.updateShopBuyComplete(uid, sid, token);
    }

    @PostMapping("/updateDelete")
    @ApiOperation(value = "购买者不想看了",notes = "shopBuyId 为购买商家的这条记录的Id，修改状态为--->>3或6")
    public Msg updateShopBuyDelete(@RequestParam("uid") Integer uid,
                                   @RequestParam("token") String token,
                                   @RequestParam("shopBuyId") Integer shopBuyId){
        return shopBuyService.deleteByShopBuyId(uid, token, shopBuyId);
    }

    @PostMapping("/findBySidAndUid")
    @ApiOperation(value = "通过同城导游记录id(shopBuy_id)查找对应的同城导游信息",notes = "查找")
    public Msg findShopBySidAndUid(@RequestParam("uid") Integer uid,
                             @RequestParam("token") String token,
                             @RequestParam("sid") Integer sid){
        return shopBuyService.selectBySidAndUid(uid,token,sid);
    }

    @PostMapping("/findBySid")
    @ApiOperation(value = "通过导游id查找聘请了此同城导游的所有记录",notes = "查找")
    public Msg findShopBuyBySid(@RequestParam("uid") Integer uid,
                                @RequestParam("sid") Integer sid,
                                @RequestParam("token") String  token){
        return shopBuyService.selectBySid(uid, sid, token);
    }

}
