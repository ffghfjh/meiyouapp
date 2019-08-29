package com.meiyou.service;

import com.meiyou.utils.Msg;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/29 14:06
 * @description：管理员动态服务层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
public interface AdminActivityService {

    /**
     * 管理员获得举报信息
     * @return
     */
    Msg listActivityReport();

}
