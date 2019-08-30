package com.meiyou.controller;

import com.meiyou.model.StarVo;
import com.meiyou.service.MyStarService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: meiyou
 * @description: 我的评星控制层
 * @author: JK
 * @create: 2019-08-26 15:28
 **/
@Api(value = "我的评星控制层", tags = {"我的评星控制层"})
@RestController
@RequestMapping("/mystar")
public class MyStarController {
    @Autowired
    MyStarService starService;

    @PostMapping("/get")
    @ApiOperation(value = "通过用户id查找指定用户id的全部评论",notes = "返回为StarVo类,")
    public Msg getClubStar(@RequestParam("uid") Integer uid,
                           @RequestParam("token") String token){
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }

        Msg msg = new Msg();
        List<StarVo> clubStarVos = starService.selectClubStar(uid);
        if(clubStarVos.isEmpty()){
            msg.add("starVos",null);
        }else {
            msg.add("clubStarVos",clubStarVos);
        }

        List<StarVo> shopStarVos = starService.selectShopStar(uid);
        if(shopStarVos.isEmpty()){
            msg.add("shopStarVos",null);
        }else {
            msg.add("shopStarVos",shopStarVos);
        }


        msg.setMsg("成功");
        msg.setCode(100);
        return msg;
    }
}
