package com.meiyou.utils;

import java.util.HashMap;
import java.util.Map;

public class LayuiDataUtil {

	/**
	 * 获取layui data分页数据
	 * @param page
	 * @return map对象
	 */
	public static <T> Map<String, Object> getLayuiData(Page page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", page.getCount());  
		map.put("data", page.getList());  
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}
	
}
