package com.meiyou.controller;

import com.meiyou.pojo.Tour;
import com.meiyou.service.TourService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @program: meiyou
 * @description: 同城旅游控制层
 * @author: JK
 * @create: 2019-08-22 19:53
 **/
@Api(value = "同城旅游控制层", tags = {"同城旅游控制层"})
@RestController
public class TourController {
    @Autowired
    private TourService tourService;

    @PostMapping(value = "/insertTour")
    public Msg insertTour(
                          @RequestParam(value = "publishId",required = false)Integer publishId,
                          @RequestParam(value = "startAddress",required = false)String startAddress,
                          @RequestParam(value = "endAddress",required = false)String endAddress,
                          @RequestParam(value = "goTime",required = false)String goTime,
                          @RequestParam(value = "goMessage",required = false)String goMessage,
                          @RequestParam(value = "reward",required = false)Integer reward,
                          @RequestParam(value = "payType",required = false)int payType,
                          @RequestParam(value = "password",required = false)String password,
                          @RequestParam(value = "token",required = false)String token){

        Tour tour = new Tour();
        tour.setCreateTime(new Date());
        tour.setUpdateTime(new Date());
        tour.setPublishId(publishId);
        tour.setStartAddress(startAddress);
        tour.setEndAddress(endAddress);
        tour.setGoTime(goTime);
        tour.setGoMessage(goMessage);
        tour.setNeedNum(1);
        tour.setReward(reward);
        tour.setPayType(payType);
        return tourService.insert(tour,password,token);

    }

}
