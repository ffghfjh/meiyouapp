package com.meiyou.service;

import com.meiyou.pojo.Club;
import com.meiyou.utils.Msg;

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
     * @param time 置顶天数
     * @param password 支付密码
     */
    Msg addClub(Club club,String token, Integer time, String password);

    /**
     * 通过会所id删除指定的会所
     * @param uid
     * @param cid
     * @return
     */
    //Todo
    Msg updateClub(Integer uid,Integer cid);

    /**
     * 通过用户id查找全部发布的会所
     * @param uid
     * @return
     */
    //Todo
    Msg selectByUid(Integer uid);

    /**
     * 通过会所id查找会所信息
     * @param cid
     * @return
     */
    //Todo
    Msg selectByCid(Integer cid);

    /**
     * 根据经纬度查询附近的会所
     * @param longitude
     * @param latitude
     * @return
     */
    List<Club> selectClub(float longitude, float latitude);
}
