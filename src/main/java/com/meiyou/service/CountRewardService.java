package com.meiyou.service;

/**
 * @description:  悬赏金业务层接口
 * @author: Mr.Z
 * @create: 2019-08-29 09:37
 **/
public interface CountRewardService {

    Integer nowRewardNums();

    Integer yesterdayRewardNums();

    Integer nowWeekRewardhNums();

    Integer nowMonthRewardNums();

    Integer lastMonthRewardNums();

    Integer nowYearRewardNums();

    Integer allRewardNums();

}
