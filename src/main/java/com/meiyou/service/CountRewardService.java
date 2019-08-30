package com.meiyou.service;

import java.util.List;

/**
 * @description:  悬赏金业务层接口
 * @author: Mr.Z
 * @create: 2019-08-29 09:37
 **/
public interface CountRewardService {

    List<Integer> countRewardNums();

    Integer nowRewardNums();

    Integer yesterdayRewardNums();

    Integer nowWeekRewardNums();

    Integer nowMonthRewardNums();

    Integer lastMonthRewardNums();

    Integer nowYearRewardNums();

    Integer allRewardNums();

}
