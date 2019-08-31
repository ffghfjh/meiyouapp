package com.meiyou.utils;

import com.alibaba.fastjson.JSONObject;
import com.meiyou.model.AliRtcAuthInfo;
import com.meiyou.model.MqttMessageModel;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-22 15:19
 **/

public class MqttMessageFactory {

    JSONObject jsonObject = new JSONObject();


     public MqttMessageFactory(int chatType, int msgType, String sender, String receiver, AliRtcAuthInfo authInfo){
         setChatType(chatType);
         setMessType(msgType);
         setSender(sender);
         setReceiver(receiver);
         setAuthInfo(authInfo);
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


    /**
     *
     * @param info
     */
    public void setAuthInfo(AliRtcAuthInfo info){
        if(info!=null){
            JSONObject authinfo = new JSONObject();
            authinfo.put("mConferenceId",info.getConferenceId());//会议id
            authinfo.put("mUserId",info.getUserId());//用户id
            authinfo.put("mAppid",info.getAppid());
            authinfo.put("mNonce",info.getNonce());
            authinfo.put("mTimestamp",info.getTimestamp());
            authinfo.put("mToken",info.getToken());
            authinfo.put("mGslb",info.getmGslb());
            jsonObject.put("authInfo",authinfo);
        }else {
            jsonObject.put("authInfo",null);
        }



    }



}