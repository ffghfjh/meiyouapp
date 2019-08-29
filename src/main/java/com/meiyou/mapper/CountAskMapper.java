package com.meiyou.mapper;

import java.util.List;

/**
 * @description: 报名次数持久层
 * @author: Mr.Z
 * @create: 2019-08-29 09:10
 **/
public interface CountAskMapper {

    List<Integer> nowAskNums();

    List<Integer> yesterdayAskNums();

    List<Integer> nowWeekAskNums();

    List<Integer> nowMonthAskNums();

    List<Integer> lastMonthAskNums();

    List<Integer> nowYearAskNums();

    List<Integer> allAskNums();
}
