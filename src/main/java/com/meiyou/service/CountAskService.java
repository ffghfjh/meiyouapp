package com.meiyou.service;

/**
 * @description:  报名次数业务层接口
 * @author: Mr.Z
 * @create: 2019-08-29 09:37
 **/
public interface CountAskService {

    Integer nowAskNums();

    Integer yesterdayAskNums();

    Integer nowWeekAskNums();

    Integer nowMonthAskNums();

    Integer lastMonthAskNums();

    Integer nowYearAskNums();

    Integer allAskNums();

}
