package com.meiyou.mapper;

import java.util.List;

/**
 * @description: 提现金持久层
 * @author: Mr.Z
 * @create: 2019-08-30 09:10
 **/
public interface CountCashMapper {

    List<Integer> nowCashNums();

    List<Integer> yesterdayCashNums();

    List<Integer> nowWeekCashNums();

    List<Integer> nowMonthCashNums();

    List<Integer> lastMonthCashNums();

    List<Integer> nowYearCashNums();

    List<Integer> allCashNums();
}
