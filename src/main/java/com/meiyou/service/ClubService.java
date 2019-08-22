package com.meiyou.service;

import com.meiyou.pojo.Activity;
import com.meiyou.pojo.Club;

import java.util.List;

/**
 * @description: 会所接口
 * @author: Mr.Z
 * @create: 2019-08-21 14:29
 **/
public interface ClubService {

    /**
     * 添加会所
     * @param club
     */
    void addClub(Club club,Integer time);

    /**
     * 通过会所id删除指定的会所
     * @param id
     */
    void deleteClub(Integer id);

    /**
     * 通过用户id查找全部发布的会所
     * @param id
     * @return
     */
    List<Club> selectByUid(Integer id);

    /**
     * 通过会所id查找会所信息
     * @param id
     * @return
     */
    Club selectByCid(Integer id);

    /**
     * 根据经纬度查询附近的会所
     * @param longitude
     * @param latitude
     * @return
     */
    List<Club> selectClub(float longitude, float latitude);
}
