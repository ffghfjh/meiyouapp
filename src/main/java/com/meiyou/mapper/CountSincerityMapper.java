package com.meiyou.mapper;

import java.util.List;

/**
 * @description: 悬赏金持久层
 * @author: Mr.Z
 * @create: 2019-08-29 09:10
 **/
public interface CountSincerityMapper {

    List<Integer> nowSincerityNums();

    List<Integer> yesterdaySincerityNums();

    List<Integer> nowWeekSincerityNums();

    List<Integer> nowMonthSincerityNums();

    List<Integer> lastMonthSincerityNums();

    List<Integer> nowYearSincerityNums();

    List<Integer> allSincerityNums();
}
