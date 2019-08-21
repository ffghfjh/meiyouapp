package com.meiyou.service;

import com.meiyou.pojo.ActivityLike;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/21 21:16
 * @description：动态点赞接口
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
public interface ActivityLikeService {

    //点赞
    int addLike(int aid, int uid);

    //取消点赞
    int removeLike(int aid, int uid);

}
