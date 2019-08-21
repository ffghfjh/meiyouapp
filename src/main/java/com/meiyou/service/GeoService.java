package com.meiyou.service;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-21 17:57
 **/
public interface GeoService {

    public void addUserGeo(Double latitude,Double longitude,String member);
    public void getUserGeo(Double latitude,Double longitude);
    public void addActivityGeo(Double latitude,Double longitude,String member);
}