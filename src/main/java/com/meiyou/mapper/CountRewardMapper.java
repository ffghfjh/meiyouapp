package com.meiyou.mapper;

import java.util.List;
import java.util.Map;

/**
 * @description: 悬赏金持久层
 * @author: Mr.Z
 * @create: 2019-08-29 09:10
 **/
public interface CountRewardMapper {

    List<List<Integer>> nowRewardNums();

    List<List<Integer>> yesterdayRewardNums();

    List<List<Integer>> nowWeekRewardNums();

    List<List<Integer>> nowMonthRewardNums();

    List<List<Integer>> lastMonthRewardNums();

    List<List<Integer>> nowYearRewardNums();

    List<List<Integer>> allRewardNums();
}
