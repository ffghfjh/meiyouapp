package com.meiyou.service;

/**
 * @description:  诚意金业务层接口
 * @author: Mr.Z
 * @create: 2019-08-29 09:37
 **/
public interface CountSincerityService {

    Integer nowSincerityNums();

    Integer yesterdaySincerityNums();

    Integer nowWeekSincerityNums();

    Integer nowMonthSincerityNums();

    Integer lastMonthSincerityNums();

    Integer nowYearSincerityNums();

    Integer allSincerityNums();

}
