package com.meiyou.service;

/**
 * @description:  发布次数业务层接口
 * @author: Mr.Z
 * @create: 2019-08-29 09:37
 **/
public interface CountPublishMoneyService {

    Integer nowPublishNums();

    Integer yesterdayPublishNums();

    Integer nowWeekPublishNums();

    Integer nowMonthPublishNums();

    Integer lastMonthPublishNums();

    Integer nowYearPublishNums();

    Integer allPublishNums();

}
