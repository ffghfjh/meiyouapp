package com.meiyou.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.meiyou.pojo.Shop;
import com.meiyou.service.ShopBuyService;
import com.meiyou.service.ShopService;
import com.meiyou.utils.FileUploadUtil;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 同城导游控制器
 * @author: Mr.Z
 * @create: 2019-08-23 11:30
 **/
@RestController
@Api(value = "同城导游控制器",tags = "发布景点商家")
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    ShopService shopService;

    @Autowired
    ShopBuyService shopBuyService;

    @PostMapping("/add")
    @ApiOperation(value = "发布同城导游",notes = "1000-请设置支付密码!,1001-支付密码错误!,1002-发布失败,账户余额不足!")
    public Msg addShop(@RequestParam("publish_id") Integer publishId,
                       @RequestParam("token") String token,
                       @RequestParam("service_area") String service_area,
                       @RequestParam("travel_time") String travel_time,
                       @RequestParam("charge") Integer charge,
                       @RequestParam("timeType") String timeType,
                       @RequestParam("password") String password,
                       @RequestParam("files") MultipartFile[] files,
                       Double longitude, Double latitude, HttpServletRequest request){

        //使用Hutool进行json操作
        JSONArray array = JSONUtil.createArray();
        for (MultipartFile file : files) {
            Msg msg = FileUploadUtil.uploadUtil(file, "shop", request);
            if (msg.getCode() == 100) {
                array.add(msg.getExtend().get("path"));
            }
        }
        if (array.size() == 0) {
            return Msg.fail();
        }

        Shop shop = new Shop();
        shop.setPublishId(publishId);
        shop.setImgsUrl(array.toString());////以json数组的形式存图片
        shop.setServiceArea(service_area);
        shop.setTravelTime(travel_time);
        shop.setCharge(charge);
        return shopService.addShop(shop,token,timeType, password, longitude, latitude);
    }

    @PostMapping("/addShopStar")
    @ApiOperation(value = "添加同城导游的评星",notes = "添加评星")
    public Msg addClubStar(@RequestParam("uid") Integer uid,
                           @RequestParam("token") String token,
                           @RequestParam("sid") Integer sid,
                           @RequestParam("star") Integer star){
        return shopBuyService.addShopStar(uid,token,sid,star);
    }

    @PostMapping("/update")
    @ApiOperation(value = "取消发布聘请同城导游",notes = "取消即更发布状态，实际数据不删除")
    public Msg updateShop(@RequestParam("uid") Integer uid,
                          @RequestParam("token") String token,
                          @RequestParam("sid") Integer sid){
        return shopService.updateShop(uid,token,sid);
    }

    @PostMapping("/updateShopIgnore")
    @ApiOperation(value = "发布者不想看自己发布的商家了",notes = "更改状态为5")
    public Msg updateShopIgnore(@RequestParam("uid") Integer uid,
                                @RequestParam("token") String token,
                                @RequestParam("shopId") Integer shopId){
        return shopService.updateShopIgnore(uid, token, shopId);
    }

    @PostMapping("/updateDelete")
    @ApiOperation(value = "发布者不想看了",notes = "shopBuyId 为购买商家的Id,修改状态--->>4或6")
    public Msg updateShopBuyDelete(@RequestParam("uid") Integer uid,
                                   @RequestParam("token") String token,
                                   @RequestParam("shopBuyId") Integer shopBuyId){
        return shopService.updateShopBuyDelete(uid, token, shopBuyId);
    }

    @PostMapping("/find")
    @ApiOperation(value = "通过同城导游id查找对应的同城导游信息",notes = "返回为ClubVO类,nums为报名人数")
    public Msg findClubByCid(@RequestParam("uid") Integer uid,
                             @RequestParam("token") String token,
                             @RequestParam("sid") Integer sid){
        return shopService.selectBySid(uid,token,sid);
    }

    @PostMapping("/getByPosition")
    @ApiOperation(value = "查找附近的shop",notes = "查找用户所在位置附近的shop,返回为ShopVO类")
    public Msg getByPosition(@RequestParam("uid") Integer uid,
                             @RequestParam("token") String token,
                             @RequestParam("longitude") Double longitude,
                             @RequestParam("latitude") Double latitude){
        return shopService.selectShopByPosition(uid,token,longitude,latitude);
    }
}
