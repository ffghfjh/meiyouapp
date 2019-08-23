package com.meiyou.controller;

import com.meiyou.model.Coordinate;
import com.meiyou.service.PeopleService;
import com.meiyou.service.RootMessageService;
import com.meiyou.utils.Constants;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.GeoRadiusResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-22 21:11
 **/
@RestController
public class PeopleController {


    @Autowired
    RootMessageService rootMessageService;
    @Autowired
    PeopleService peopleService;
    @RequestMapping(value="nearPersons",method = RequestMethod.POST )
    public Msg nearPersons(Double latitude,Double longtitude,int id,String token){
        System.out.println("请求附近的人");
       if(RedisUtil.authToken(String.valueOf(id),token)){
           System.out.println("登录鉴权成功");
           Coordinate coordinate = new Coordinate();
           coordinate.setLatitude(latitude);
           coordinate.setLongitude(longtitude);
           coordinate.setKey(String.valueOf(id));
           RedisUtil.addReo(coordinate, Constants.GEO_USER_KEY);
           String radius = rootMessageService.getMessageByName("range");
           return peopleService.selPeoples(coordinate,Double.parseDouble(radius));
       }else {
           System.out.println("登录鉴权失败");
           return Msg.noLogin();
       }

    }

}