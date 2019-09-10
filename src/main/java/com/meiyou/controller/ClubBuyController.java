package com.meiyou.controller;

import com.meiyou.pojo.ClubBuy;
import com.meiyou.service.ClubBuyService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 购买推拿会所控制器
 * @author: Mr.Z
 * @create: 2019-08-22 16:01
 **/
@RestController
@Api(value = "购买推拿会所控制器",tags = "购买推拿会所")
@RequestMapping("/clubBuy")
public class ClubBuyController {

    @Autowired
    ClubBuyService clubBuyService;

    @PostMapping("/add")
    @ApiOperation(value = "购买推拿会所",notes = "501-自己不允许聘请,1000-请设置支付密码!,1001-支付密码错误!,1002-发布失败,账户余额不足!")
    public Msg addClubBuy(@RequestParam("uid") Integer uid,
                          @RequestParam("token") String token,
                          @RequestParam("cid") Integer cid,
                          @RequestParam("password") Integer password){
        ClubBuy clubBuy = new ClubBuy();
        clubBuy.setBuyerId(uid);
        clubBuy.setClubId(cid);
        return clubBuyService.addBuyClub(clubBuy,token,password);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更改购买推拿会所的状态为取消购买",notes = "cid 为购买会所的这条记录的Id,状态更改")
    public Msg updateClubBuy(@RequestParam("uid") Integer uid,
                             @RequestParam("token") String token,
                             @RequestParam("cid") Integer cid){
        return clubBuyService.updateBuyClub(uid, cid, token);
    }

    @PostMapping("/updateComplete")
    @ApiOperation(value = "更改购买推拿会所的状态为已到店(已完成)",notes = "cid 为购买会所的这条记录的Id,修改状态为已到店(已完成)--->>1")
    public Msg updateClubBuyComplete(@RequestParam("uid") Integer uid,
                             @RequestParam("token") String token,
                             @RequestParam("cid") Integer cid){
        return clubBuyService.updateClubBuyComplete(uid, cid, token);
    }

    @PostMapping("/updateDelete")
    @ApiOperation(value = "购买者不想看了",notes = "clubBuyId 为购买会所的这条记录的Id,修改状态为--->>3或6")
    public Msg updateClubBuyDelete(@RequestParam("uid") Integer uid,
                             @RequestParam("token") String token,
                             @RequestParam("clubBuyId") Integer clubBuyId){
        return clubBuyService.deleteByClubBuyId(uid, token, clubBuyId);
    }

    @PostMapping("/findByCidAndUid")
    @ApiOperation(value = "通过会所id和用户id查找对应购买的会所记录",notes = "查找")
    public Msg findClubBuyByCidAndUid(@RequestParam("uid") Integer uid,
                                @RequestParam("cid") Integer cid,
                                @RequestParam("token") String  token){
        return clubBuyService.selectByCidAndUid(uid, cid, token);
    }

    @PostMapping("/findByCid")
    @ApiOperation(value = "通过会所id查找购买了此会所的所有记录",notes = "查找")
    public Msg findClubBuyByCid(@RequestParam("uid") Integer uid,
                                @RequestParam("cid") Integer cid,
                                @RequestParam("token") String  token){
        return clubBuyService.selectByCid(uid, cid, token);
    }

    @PostMapping("/addStar")
    @ApiOperation(value = "评星",notes = "对状态为已完成的会所记录进行评星")
    public Msg addClubStar(@RequestParam("uid") Integer uid,
                          @RequestParam("token") String token,
                          @RequestParam("cid") Integer cid,
                          @RequestParam("star") Integer star){
        return clubBuyService.addClubStar(uid,token,cid,star);
    }

}
