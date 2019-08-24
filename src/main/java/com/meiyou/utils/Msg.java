package com.meiyou.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用的返回类
 *
 */
public class Msg implements Serializable {
	
	@Override
	public String toString() {
		return "Msg [code=" + code + ", msg=" + msg + ", extend=" + extend + ", getCode()=" + getCode() + ", getMsg()="
				+ getMsg() + ", getExtend()=" + getExtend() + "]";
	}

	public static int NOLOGIN = 300;  //没有登录
	public static int NULLPARAM = 400;//参数报空



	//状态码
	private int code;
	//提示信息
	private String msg;
	
	//用户要返回给浏览器的数据
	private Map<String,Object> extend = new HashMap<String,Object>();

	public static Msg success() {
		Msg result = new Msg();
		result.setCode(100);
		result.setMsg("成功");
		return result;
		
	}
	
	public static Msg fail() {
		Msg result = new Msg();
		result.setCode(200);
		result.setMsg("失败");
		return result;
	}

	public static Msg noLogin(){
		Msg result = new Msg();
		result.setCode(Msg.NOLOGIN);
		result.setMsg("未登录");
		return result;
	}
	
	public static Msg nullParam() {
		Msg result = new Msg();
		result.setCode(Msg.NULLPARAM);
		result.setMsg("参数报空");
		return result;
	}

	
	public Msg add(String key,Object value) {
		this.getExtend().put(key, value);
		return this;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getExtend() {
		return extend;
	}

	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}
	
	
}
