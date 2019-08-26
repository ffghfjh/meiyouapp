package com.meiyou.model;

import java.io.Serializable;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-22 15:49
 **/
public class MqttMessageModel implements Serializable {

    /**
     * chatType : 1
     * msgType : 2
     * sender : fdsf
     * receiver : faf
     */

    private int chatType;
    private int msgType;
    private String sender;
    private String receiver;
    private AliRtcAuthInfo authInfo;

    public AliRtcAuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AliRtcAuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}