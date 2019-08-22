package com.meiyou.service;

import com.meiyou.pojo.RootMessage;

import java.util.List;

/**
 * @program: meiyouapp
 * @description:
 * @author: JK
 * @create: 2019-08-21 15:27
 **/
public interface RootMessageService {
    /**
     * 查询所有系统动态数据
     * @return
     */
    List<RootMessage> select();
}
