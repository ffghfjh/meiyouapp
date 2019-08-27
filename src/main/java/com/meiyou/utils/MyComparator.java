package com.meiyou.utils;

import java.util.Comparator;
import java.util.HashMap;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/26 21:34
 * @description：HashMap数组排序问题的工具类
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
public class MyComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        // TODO Auto-generated method stub
        int result = 0;
        try {
            HashMap<String, Object> map1 = (HashMap<String, Object>) o1, map2 = (HashMap<String, Object>) o2;
            int map1_aid = Integer.parseInt(String.valueOf(map1.get("aid")));
            int map2_aid = Integer.parseInt(String.valueOf(map2.get("aid")));
            if(map1_aid > map2_aid){
                result = -1;
            }else if(map1_aid < map2_aid){
                result = 1;
            }else if(map1_aid == map2_aid){
                result = 0;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }
}
