package com.meiyou.service;

import com.meiyou.pojo.ActivityLike;
import com.meiyou.utils.Msg;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/21 21:16
 * @description：动态点赞接口
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
public interface ActivityLikeService {

    //点赞
    Msg like(int aid, int uid, int type);

    //根据aid和uid查找我是否点赞过这个动态
    boolean getBoolLikeByAidUid(int aid, int uid);

}
