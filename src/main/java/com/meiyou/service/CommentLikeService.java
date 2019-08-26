package com.meiyou.service;

import com.meiyou.utils.Msg;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/23 16:04
 * @description：评论点赞服务层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
public interface CommentLikeService {


    //评论点赞接口
    Msg like(int uid, int cid, int type);

    //判断我自己是否点赞过这条评论
    boolean boolLike(int uid, int cid);

}
