package com.meiyou.service;

import com.meiyou.pojo.RootMessage;
import com.meiyou.utils.Msg;

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
     *
     * @return
     */
    List<RootMessage> select();

    /**
     * @author ：huangzhaoyang
     * @date ：Created in 2019/8/22 16:41
     * @description：系统参数接口
     * @modified By：huangzhaoyang
     * @version: 1.0.0
     */

    //添加参数
    Msg saveMessage(String name, String value);


    //删除参数
    Msg removeMessage(int mid);

    //根据主键修改参数或参数值
    Msg updateMessageById(int mid, String name, String value);

    //根据参数名修改参数值
    Msg updateMessageByName(String name, String value);

    //根据参数名查找值
    String getMessageByName(String name);

    //拉取所有系统参数
    Msg listMessage();

}
