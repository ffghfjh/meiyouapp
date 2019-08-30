package com.meiyou.controller;

import com.meiyou.service.*;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 数据统计控制器层
 * @author: Mr.Z
 * @create: 2019-08-30 12:46
 **/
@RestController
@Api(value = "数据统计控制层", tags = {"数据统计控制层"})
@RequestMapping("/countNums")
public class CountNumsController {
    @Autowired
    CountUserService userService;
    @Autowired
    CountShareService shareService;
    @Autowired
    CountPublishService publishService;
    @Autowired
    CountRewardService rewardService;
    @Autowired
    CountSincerityService sincerityService;
    @Autowired
    CountPublishMoneyService publishMoneyService;
    @Autowired
    CountRechargeService rechargeService;
    @Autowired
    CountCashService cashService;
    @Autowired
    CountVideoService videoService;
    @Autowired
    CountAskService askService;

    @PostMapping("/get")
    @ApiOperation(value = "数据统计", notes = "查询今天、昨天、本周、本月、上月、今年、全部的数据")
    public Msg getCountNums(){
        Msg msg = new Msg();
        List<Integer> userNums = userService.CountUserNums();
        List<Integer> shareMoney = shareService.countShareMoney();
        List<Integer> publishNums = publishService.countPublishNums();
        List<Integer> rewardMoney = rewardService.countRewardNums();
        List<Integer> sincerityMoney = sincerityService.countSincerityNums();
        List<Integer> publishMoney = publishMoneyService.countPublishNums();
        List<Integer> rechargeMoney = rechargeService.countRechargeNums();
        List<Integer> cashMoney = cashService.countCashNums();
        List<Integer> videoMoney = videoService.CountVideoNums();
        List<Integer> askNums = askService.countAskNums();

        msg.add("userNums",userNums);
        msg.add("shareMoney",shareMoney);
        msg.add("publishNums",publishNums);
        msg.add("rewardMoney",rewardMoney);
        msg.add("sincerityMoney",sincerityMoney);
        msg.add("publishMoney",publishMoney);
        msg.add("rechargeMoney",rechargeMoney);
        msg.add("cashMoney",cashMoney);
        msg.add("videoMoney",videoMoney);
        msg.add("askNums",askNums);

        msg.setCode(100);
        msg.setMsg("成功");

        return msg;
    }
}
