package com.meiyou.service.impl;

import com.meiyou.mapper.UserMapper;
import com.meiyou.model.Coordinate;
import com.meiyou.pojo.User;
import com.meiyou.service.PeopleService;
import com.meiyou.service.TencentImService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.GeoRadiusResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-22 21:27
 **/
@Service
public class PeopleServiceImpl implements PeopleService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    TencentImService tencentImService;
    @Override
    public Msg selPeoples(Coordinate coordinate, double radius) {
        Msg msg;
        List<GeoRadiusResponse> responseList = RedisUtil.geoQueryUser(coordinate,radius);
        if(responseList!=null||responseList.size()!=0){
            msg = Msg.success();
            List<Map<String,Object>> list = new ArrayList<>();
            System.out.println("获取附近的人："+responseList.size());
            for(GeoRadiusResponse response : responseList){
                int uid = Integer.parseInt(response.getMemberByString());
                if(uid!=0){
                    User user = userMapper.selectByPrimaryKey(uid);
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("qianming",user.getSignature());
                    map.put("img",user.getHeader());
                    map.put("uname",user.getNickname());
                    map.put("bgImg",user.getBgPicture());
                    if(response.getDistance()<0.05){
                        map.put("distance",0.1);
                    }else{
                        map.put("distance",(double)Math.round(response.getDistance()*100)/100);
                    }
                    map.put("ucount",user.getAccount());
                    map.put("sex",user.getSex());
                    map.put("age",Integer.parseInt(user.getBirthday()));
                    map.put("bg",user.getBgPicture());
                    map.put("id",user.getId());
                    int i = tencentImService.getUserState(user.getAccount());
                    if(i==1){
                        map.put("state","在线");
                    }
                    else {
                        map.put("state","离线");
                    }
                    list.add(map);
                }
            }
            return Msg.success().add("persons",list);
        }else{
            System.out.println("无附近人");
            msg = Msg.fail();
            msg.setMsg("暂无附近的人");
            return msg;
        }
    }
}