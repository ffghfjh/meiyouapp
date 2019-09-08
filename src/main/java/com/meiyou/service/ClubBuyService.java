package com.meiyou.service;

import com.meiyou.pojo.ClubBuy;
import com.meiyou.utils.Msg;

/**
 * @description: 会所购买业务层接口
 * @author: Mr.Z
 * @create: 2019-08-21 15:37
 **/
public interface ClubBuyService {

    /**
     * 添加会所购买者记录
     * @param clubBuy
     */
    Msg addBuyClub(ClubBuy clubBuy,String token,Integer password);

    /**
     * 取消购买
     * @param clubBuyId
     */
    Msg updateBuyClub(Integer uid,Integer clubBuyId,String token);

    /**
     * 修改为已完成状态
     * @param uid
     * @param clubBuyId 购买按摩会所这条记录的ID
     * @param token
     * @return
     */
    Msg updateClubBuyComplete(Integer uid,Integer clubBuyId,String token);

    /**
     * 查找指定的会所购买记录
     * @param uid
     * @param cid
     * @param token
     * @return
     */
    Msg selectByCidAndUid(Integer uid,Integer cid,String token);

    /**
     * 查询购买了此会所的所有记录
     * @param uid
     * @param cid
     * @param token
     * @return
     */
    Msg selectByCid(Integer uid,Integer cid,String token);

    /**
     * 给购买过的club进行评星
     * @param uid
     * @param token
     * @param clubBuyId clubBuy的Id
     * @return
     */
    Msg addClubStar(Integer uid,String token,Integer clubBuyId,Integer star);

    /**
     * 删除购买的club这条记录
     * @param uid
     * @param token
     * @param clubBuyId
     * @return
     */
    Msg deleteByClubBuyId(Integer uid,String token,Integer clubBuyId);
}
