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
     * @param cid
     */
    Msg updateBuyClub(Integer uid,Integer cid,String token);

    /**
     * 查找指定用户uid下所有的会所购买记录
     * @param uid
     * @return
     */
    Msg selectByUid(Integer uid,String token);

    /**
     * 查找指定的会所购买记录
     * @param uid
     * @param cid
     * @param token
     * @return
     */
    Msg selectByCid(Integer uid,Integer cid,String token);

}
