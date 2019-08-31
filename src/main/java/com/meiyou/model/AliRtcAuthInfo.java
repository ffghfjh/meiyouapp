package com.meiyou.model;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-24 22:54
 **/
public class AliRtcAuthInfo {

        public String mConferenceId;
        public String mUserId;
        public String mAppid;
        public String mNonce;
        public long mTimestamp;
        public String mToken;
        public String mGslb;

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

      public String getmGslb() {
        return mGslb;
      }

      public void setmGslb(String mGslb) {
        this.mGslb = mGslb;
      }
}