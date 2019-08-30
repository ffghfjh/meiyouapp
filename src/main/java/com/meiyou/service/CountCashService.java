package com.meiyou.service;

import java.util.List;

/**
 * @description:  提现金额统计业务层接口
 * @author: Mr.Z
 * @create: 2019-08-30 9:50
 **/
public interface CountCashService {

    List<Integer> countCashNums();

    Integer nowCashNums();

    Integer yesterdayCashNums();

    Integer nowWeekCashNums();

    Integer nowMonthCashNums();

    Integer lastMonthCashNums();

    Integer nowYearCashNums();

    Integer allCashNums();

}
