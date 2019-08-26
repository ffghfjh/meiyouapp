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
    @ApiOperation(value = "购买推拿会所",notes = "1000-请设置支付密码!,1001-支付密码错误!,1002-发布失败,账户余额不足!")
    public Msg addClubBuy(@RequestParam("uid") Integer uid,
                          @RequestParam("token") String token,
                          @RequestParam("cid") Integer cid,
                          @RequestParam("password") Integer password){
        ClubBuy clubBuy = new ClubBuy();
        clubBuy.setBuyerId(uid);
        clubBuy.setClubId(cid);
        return clubBuyService.addBuyClub(clubBuy,token,password);
    }

    @PutMapping("/update")
    @ApiOperation(value = "更改购买推拿会所的状态",notes = "状态更改")
    public Msg updateClubBuy(@RequestParam("uid") Integer uid,
                             @RequestParam("token") String token,
                             @RequestParam("cid") Integer cid){
        return clubBuyService.updateBuyClub(uid, cid, token);
    }

    @GetMapping("/get")
    @ApiOperation(value = "通过用户id查找指定用户id的全部购买的推拿会所",notes = "查找")
    public Msg getClubBuyByUid(@RequestParam("uid") Integer uid,
                               @RequestParam("token") String token){
        Msg msg = new Msg();
        List<ClubBuy> result = clubBuyService.selectByUid(uid, token);
        if(result == null){
            msg.setMsg("没有找到对应的ClubBuy记录");
            msg.setCode(404);
            return msg;
        }
        msg.add("clubBuy",result);
        msg.setCode(100);
        msg.setMsg("成功");
        return msg;
    }

    @GetMapping("/find")
    @ApiOperation(value = "通过会所id查找对应购买的会所记录",notes = "查找")
    public Msg findClubBuyByCid(@RequestParam("uid") Integer uid,
                                @RequestParam("cid") Integer cid,
                                @RequestParam("token") String  token){
        return clubBuyService.selectByCid(uid, cid, token);
    }
}
