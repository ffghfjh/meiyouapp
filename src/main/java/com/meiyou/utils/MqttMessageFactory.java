package com.meiyou.utils;

import com.alibaba.fastjson.JSONObject;
import com.meiyou.service.MqttService;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-22 15:19
 **/

public class MqttMessageFactory {

    JSONObject jsonObject = new JSONObject();


     public MqttMessageFactory(int chatType,int msgType,String sender,String receiver){
         setChatType(chatType);
         setMessType(msgType);
         setSender(sender);
         setReceiver(receiver);
     }

    /**
     * 得到json消息对象
     * @return
     */
     public JSONObject getJsonObject(){
         return jsonObject;
     }

    /**
     * 聊天类型
     * @param type 聊天类型
     */
    public void setChatType(int type){
        jsonObject.put("chatType",type);
    }

    /**
     * @param type 消息类型
     */
    public void setMessType(int type){
        jsonObject.put("msgType",type);
    }

    /**
     *
     * @param sender 发送者
     */
    public void setSender(String sender){
        jsonObject.put("sender",sender);
    }

    /**
     *
     * @param receiver 接收者     */
    public void setReceiver(String receiver){
        jsonObject.put("receiver",receiver);
    }



}