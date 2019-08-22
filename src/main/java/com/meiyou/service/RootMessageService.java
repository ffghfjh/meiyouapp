package com.meiyou.service;

import com.meiyou.pojo.RootMessage;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/22 16:41
 * @description：系统参数接口
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
public interface RootMessageService {

    //添加参数
    int saveMessage(String name, String value);

    //删除参数
    int removeMessage(int mid);

    //修改参数
    int updateMessage(int mid, String name, String value);

    //查找参数
    String getMessageByName(String name);
}
