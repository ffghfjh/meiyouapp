package com.meiyou.service;

import com.meiyou.utils.Msg;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/24 11:57
 * @description：动态阅读量接口
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
public interface ActivityReadService {

    /**
     * 动态添加阅读量
     * @param uid
     * @param aid
     * @return
     */
    Msg addReadNum(int uid, int aid);

    //谁看过我的动态
    Msg whoHasSeenMe(int uid);


}
