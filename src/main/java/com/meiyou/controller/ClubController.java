package com.meiyou.controller;

import com.meiyou.pojo.Club;
import com.meiyou.service.ClubService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 推拿会所控制器
 * @author: Mr.Z
 * @create: 2019-08-21 14:34
 **/
@RestController
@Api(value = "推拿会所控制器",tags = "推拿会所")
@RequestMapping("/club")
public class ClubController {

    @Autowired
    ClubService clubService;

    @PostMapping("/add")
    @ApiOperation(value = "发布",notes = "发布会所推拿会所")
    public Msg addClub(@RequestParam("imgs_url") String imgsUrl,
                       @RequestParam("project_name") String projectName,
                       @RequestParam("project_desc") String projectDesc,
                       @RequestParam("project_address") String projectAddress,
                       @RequestParam("project_price") Integer projectPrice,
                       @RequestParam("market_price") Integer marketPrice,
                       @RequestParam("time") Integer time){
        Club club = new Club();
        club.setImgsUrl(imgsUrl);
        club.setProjectName(projectName);
        club.setProjectDesc(projectDesc);
        club.setProjectAddress(projectAddress);
        club.setProjectAddress(projectAddress);
        club.setProjectPrice(projectPrice);
        club.setMarketPrice(marketPrice);
        clubService.addClub(club,time);

        Msg msg = new Msg();
        msg.setCode(100);
        msg.setMsg("成功？");
        return msg;
    }
}
