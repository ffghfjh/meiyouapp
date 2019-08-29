package com.meiyou.mapper;

import java.util.List;

/**
 * @description: 发布次数持久层
 * @author: Mr.Z
 * @create: 2019-08-29 09:10
 **/
public interface CountPublishMapper {

    List<Integer> nowPublishNums();

    List<Integer> yesterdayPublishNums();

    List<Integer> nowWeekPublishNums();

    List<Integer> nowMonthPublishNums();

    List<Integer> lastMonthPublishNums();

    List<Integer> nowYearPublishNums();

    List<Integer> allPublishNums();
}
