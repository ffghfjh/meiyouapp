package com.meiyou.service;



import com.meiyou.utils.Msg;

public interface AliyunVideoService {
	
	public Msg createChannel(String appID, String channelID);//创建频道
	
	public String createToken(String appId, String userId, String channelId, String channelKey, String nonce,
                              long timestamp);//为客户端创建令牌
}
