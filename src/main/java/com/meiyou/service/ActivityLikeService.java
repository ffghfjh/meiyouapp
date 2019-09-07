package com.meiyou.service;

import com.meiyou.utils.Msg;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/21 21:16
 * @description：动态点赞接口
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
public interface ActivityLikeService {

    /**
     * 删除点赞接口，只是屏蔽这条记录
     * @param likeId
     * @param token
     * @return
     */
    Msg remove(String uid, String token, int likeId);


    //获得我所有动态下所有未读的点赞数
    Msg getNotSeenLikeNumForMyActvity(int uid);

    //获取用户对我动态的点赞列表
    Msg listUserLikeForMyActivity(int uid);

    //点赞
    Msg like(int aid, int uid, int type);

    //根据aid和uid查找我是否点赞过这个动态
    boolean getBoolLikeByAidUid(int aid, int uid);

}
