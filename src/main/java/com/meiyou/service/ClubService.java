package com.meiyou.service;

import com.meiyou.pojo.Club;
import com.meiyou.utils.Msg;

/**
 * @description: 会所接口
 * @author: Mr.Z
 * @create: 2019-08-21 14:29
 **/
public interface ClubService {

    /**
     * 添加会所
     * @param club
     * @param timeType 置顶天数类型
     * @param password 支付密码
     */
    Msg addClub(Club club,String token, String timeType, String password, Double longitude, Double latitude);

    /**
     * 通过会所id取消发布指定的会所
     * @param uid
     * @param cid
     * @return
     */
    Msg updateClub(Integer uid,String token,Integer cid);

    /**
     * 通过会所id修改clubBuy状态
     * @param uid
     * @param token
     * @param cid
     * @return
     */
    Msg updateClubBuyDelete(Integer uid,String token,Integer cid);

    /**
     * 发布者不想看这个发布的了
     * @param uid
     * @param token
     * @param clubId
     * @return
     */
    Msg updateClubIgnore(Integer uid,String token,Integer clubId);

    /**
     * 通过会所id查找会所信息
     * @param cid
     * @return
     */
    Msg selectByCid(Integer uid,String token,Integer cid);

    /**
     * 根据经纬度查询附近的会所
     * @param longitude
     * @param latitude
     * @return
     */
    Msg selectClubByPosition(Integer uid,String token,Double longitude, Double latitude);
}
