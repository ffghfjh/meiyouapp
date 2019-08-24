package com.meiyou.model;

import java.io.Serializable;

/**
 * 支付宝信息实体类
 */
public class AliPayInfo implements Serializable {
	
		
	private String userId;//用户支付宝唯一id
	private String avatar;//支付宝头像地址
	private String nick_name;//支付宝昵称
	private String is_certied;//支付宝知否实名
	private String gender;//支付宝性别
	

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getIs_certied() {
		return is_certied;
	}

	public void setIs_certied(String is_certied) {
		this.is_certied = is_certied;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
