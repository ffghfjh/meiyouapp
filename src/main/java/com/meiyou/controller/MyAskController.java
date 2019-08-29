package com.meiyou.controller;

import com.meiyou.model.ClubVO;
import com.meiyou.model.ShopVO;
import com.meiyou.service.MyAskService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: meiyou
 * @description: 我的报名控制层
 * @author: JK
 * @create: 2019-08-26 15:19
 **/
@Api(value = "我的报名控制层", tags = {"我的报名控制层"})
@RestController
public class MyAskController {
    @Autowired
    private MyAskService myAskService;

    /**
    * @Description: 查询我的约会报名
    * @Author: JK
    * @Date: 2019/8/26
    */
    @ApiOperation(value = "查询我的约会报名", notes = "查询我的约会报名", httpMethod = "GET")
    @PostMapping(value = "/selectMyAppointmentAsk")
    public Msg selectMyAppointmentAsk(String uid, String token) {
        if(!RedisUtil.authToken(uid,token)){
            return Msg.noLogin();
        }
        Msg msg = new Msg();
        myAskService.selectMyAppointmentAsk(uid, token);


        List<ClubVO> clubVOS = myAskService.selectMyClubAsk(Integer.valueOf(uid));
        if(clubVOS.isEmpty()){
            msg.add("clubVOS",null);
        }else {
            msg.add("clubVOS",clubVOS);
        }

        List<ShopVO> shopVOS = myAskService.selectMyShopAsk(Integer.valueOf(uid));
        if(clubVOS.isEmpty()){
            msg.add("shopVOS",null);
        }else {
            msg.add("shopVOS",shopVOS);
        }

        msg.setCode(100);
        msg.setMsg("成功");
        return msg;
    }
}
