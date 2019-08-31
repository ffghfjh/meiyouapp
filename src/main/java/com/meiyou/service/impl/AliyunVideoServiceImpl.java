package com.meiyou.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.xml.bind.DatatypeConverter;

import com.aliyuncs.rtc.model.v20180111.CreateChannelRequest;
import com.aliyuncs.rtc.model.v20180111.CreateChannelResponse;
import com.meiyou.model.ChannelAuth;
import com.meiyou.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.meiyou.service.AliyunVideoService;
import com.meiyou.utils.Msg;

@Service
public class AliyunVideoServiceImpl implements AliyunVideoService{
    
	
	private String regionID = "cn-hangzhou";
	private String endpoint = "rtc.aliyuncs.com";
	Msg msg;

	
	/**
	 * 创建频道生成鉴权私钥
	 */
	public Msg createChannel(String appID, String channelID) {
		// TODO Auto-generated method stub
		try {
	        DefaultProfile profile = DefaultProfile.getProfile(regionID, Constants.VIDEOACCESSKEYID, Constants.VIDEOACCESSKEYSECRET);
	        DefaultAcsClient client = new DefaultAcsClient(profile);
	        CreateChannelRequest request = new CreateChannelRequest();
	        request.setAppId(appID);
	        request.setChannelId(channelID);
	        DefaultProfile.addEndpoint(regionID, regionID, request.getProduct(), endpoint);
	        // Use HTTP, x3 times faster than HTTPS.
	        request.setProtocol(ProtocolType.HTTP);
	        client.setAutoRetry(true);
	        client.setMaxRetryNumber(3);
	        request.setConnectTimeout(2000);
	        request.setReadTimeout(3000);
	        CreateChannelResponse response = client.getAcsResponse(request);
			ChannelAuth auth = new ChannelAuth();
			auth.appID = appID;
			auth.channelID = channelID;
			auth.channelKey = response.getChannelKey();
			auth.nonce = response.getNonce();
			auth.recovered = false;
			auth.timestamp = (long)response.getTimestamp();
			auth.requestID = response.getRequestId();
	        msg = Msg.success();
	        msg.add("auth",auth);
	        return msg;
	    } catch (ClientException ex) {
	    	boolean fatal = false;
	 	    if (ex != null) {
	 	        String code = ex.getErrCode();
	 	        if (code.equalsIgnoreCase("IllegalOperationApp")) {
	 	            fatal = true;
	 	        } else if (code.startsWith("InvalidAccessKeyId")) {
	 	            fatal = true;
	 	        } else if (code.equalsIgnoreCase("SignatureDoesNotMatch")) {
	 	            fatal = true;
	 	        }
	 	    }
	 	    if (fatal) {
	 	        try {
					throw ex;
				} catch (ClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 	    }
	 	    String recover = String.format("RCV-%s", UUID.randomUUID().toString());

	 	    System.out.printf("Recover from error:\n");
	 	    ex.printStackTrace();
	 	    msg = Msg.fail();
	 	    msg.setExtend(null);
	 	    return msg;
	  }
		
	}
	
	
	/**
	 * 为客户端创建Token
	 */
	public String createToken( String appId, String userId, String channelId, String channelKey, String nonce,long timestamp) {
	    try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(channelId.getBytes());
			digest.update(channelKey.getBytes());
			digest.update(appId.getBytes());
			digest.update(userId.getBytes());
			digest.update(nonce.getBytes());
			digest.update(Long.toString(timestamp).getBytes());
			String token = DatatypeConverter.printHexBinary(digest.digest()).toLowerCase();
			return token;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
