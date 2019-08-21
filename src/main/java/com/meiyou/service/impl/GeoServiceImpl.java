package com.meiyou.service.impl;

import com.meiyou.mapper.RootMessageMapper;
import com.meiyou.pojo.RootMessage;
import com.meiyou.pojo.RootMessageExample;
import com.meiyou.service.GeoService;
import com.meiyou.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-21 18:06
 **/
@Service
public class GeoServiceImpl implements GeoService {

    private RedisTemplate redisTemplate;
    @Autowired
    private RootMessageMapper rootMessageMapper;

    @Autowired
    public GeoServiceImpl(StringRedisTemplate redisTemplate){



        this.redisTemplate = redisTemplate;


    }

    /**
     * 添加附近的人
     */
    public void addUserGeo(Double latitude,Double longitude,String member){
        BoundGeoOperations<String,String> geoOperations = redisTemplate.boundGeoOps(Constants.GEO_USER_KEY);
        geoOperations.add(new Point(latitude,longitude),member);
    }


    public void getUserGeo(Double latitude,Double longitude){

        RootMessageExample example = new RootMessageExample();
        RootMessageExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo("range");
        int range = Integer.parseInt(rootMessageMapper.selectByExample(example).get(0).getValue());
        Circle circle = new Circle(new Point(latitude,longitude),range);
        BoundGeoOperations<String,String> geoOperations = redisTemplate.boundGeoOps(Constants.GEO_USER_KEY);
        //设置geo查询参数
        RedisGeoCommands.GeoRadiusCommandArgs geoRadiusArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs();
        //查询返回结果包括距离和坐标
        geoRadiusArgs = geoRadiusArgs.includeCoordinates().includeDistance();
        //按查询出的坐标距离中心坐标的距离进行排序
        geoRadiusArgs.sortAscending();
        //限制查询返回的数量
        geoRadiusArgs.limit(50);
        GeoResults<RedisGeoCommands.GeoLocation<String>> geoResults = geoOperations.geoRadius(circle,geoRadiusArgs);
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> geoResultList = geoResults.getContent();
        for (GeoResult geoResult : geoResultList) {
            System.out.println("geoRadius  " + geoResult.getContent());
            RedisGeoCommands.GeoLocation location = (RedisGeoCommands.GeoLocation)geoResult.getContent();
            System.out.println("附近的人"+geoResult.getDistance()+":"+location.getName()+":"+location.getPoint().getX()+":"+location.getPoint().getY()+"");
        }

    }

    /**
     * 添加附近的人
     */
    public void addActivityGeo(Double latitude,Double longitude,String member){
        BoundGeoOperations<String,String> geoOperations = redisTemplate.boundGeoOps(Constants.GEO_ACTIVITY);
        geoOperations.add(new Point(latitude,longitude),member);
    }




}