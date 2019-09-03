package com.meiyou.model;

import org.springframework.context.annotation.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*huang zhaoyang
 * layui数据表格的json返回格式
 * 
 */
/*
 * 默认规定的数据格式:
 * 
  {
	  "code": 0,
	  "msg": "",
	  "count": 1000,
	  "data": [{}, {}]
  } 
 */
/*
 * 必须注意：调用时，务必先调用addCount()再调用addData()，否则调用失败
 */
public class LayuiTableJson implements Serializable {
	
	//状态码
	private int code;
	
	//提示信息
	private String msg;
	
	private int count;
	
	//用户要返回给浏览器的数据
	private Object data;
	
	public static LayuiTableJson success(){
		LayuiTableJson ltj = new LayuiTableJson();
		ltj.setCode(100);
		ltj.setMsg("处理成功!");
		return ltj;
	}

	public static LayuiTableJson fail(){
		LayuiTableJson ltj = new LayuiTableJson();
		ltj.setCode(200);
		ltj.setMsg("没有数据");
		return ltj;
	}
	
	public static LayuiTableJson addCount(int c){
		LayuiTableJson ltj = new LayuiTableJson();
		ltj.setCount(c);
		return ltj;
	}
	
	public LayuiTableJson addData(Object value){
		this.setData(value);
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

	public int getCount() {
	    return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
