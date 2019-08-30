package com.meiyou.mapper;

import java.util.List;
import java.util.Map;

/**
 * @description: 悬赏金持久层
 * @author: Mr.Z
 * @create: 2019-08-29 09:10
 **/
public interface CountRewardMapper {

    List<Integer> nowRewardNums();

    List<Integer> yesterdayRewardNums();

    List<Integer> nowWeekRewardNums();

    List<Integer> nowMonthRewardNums();

    List<Integer> lastMonthRewardNums();

    List<Integer> nowYearRewardNums();

    List<Integer> allRewardNums();
}
