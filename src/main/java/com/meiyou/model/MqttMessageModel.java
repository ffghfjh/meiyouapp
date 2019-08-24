package com.meiyou.model;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-22 15:49
 **/
public class MqttMessageModel {

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

    public class AliRtcAuthInfo{
        public String mConferenceId;
        public String mUserId;
        public String mAppid;
        public String mNonce;
        public long mTimestamp;
        public String mToken;
        public String[] mGslb;

        public AliRtcAuthInfo() {
        }

        public String getConferenceId() {
            return this.mConferenceId;
        }

        public void setConferenceId(String conferenceId) {
            this.mConferenceId = conferenceId;
        }

        public String getUserId() {
            return this.mUserId;
        }

        public void setUserId(String userId) {
            this.mUserId = userId;
        }

        public String getAppid() {
            return this.mAppid;
        }

        public void setAppid(String appid) {
            this.mAppid = appid;
        }

        public String getNonce() {
            return this.mNonce;
        }

        public void setNonce(String nonce) {
            this.mNonce = nonce;
        }

        public long getTimestamp() {
            return this.mTimestamp;
        }

        public void setTimestamp(long timestamp) {
            this.mTimestamp = timestamp;
        }

        public String getToken() {
            return this.mToken;
        }

        public void setToken(String token) {
            this.mToken = token;
        }

        public String[] getGslb() {
            return this.mGslb;
        }

        public void setGslb(String[] gslb) {
            this.mGslb = gslb;
        }
    }
}