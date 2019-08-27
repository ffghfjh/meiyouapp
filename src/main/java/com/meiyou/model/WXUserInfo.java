package com.meiyou.model;

public class WXUserInfo {

	private String openid;//用户标识
	private String nickname;//昵称
	private boolean sex;//性别
	private String province;//所在省份
	private String city;//所在城市
	private String country;//所在国家
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public boolean isSex() {
		return sex;
	}

	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	private String headimgurl;//用户头像
	private String unionid;//用户统一标识
}
