package com.meiyou.mapper;

import java.util.List;

/**
 * @description: 充值金持久层
 * @author: Mr.Z
 * @create: 2019-08-29 09:10
 **/
public interface CountRechargeMapper {

    List<Integer> nowRechargeNums();

    List<Integer> yesterdayRechargeNums();

    List<Integer> nowWeekRechargeNums();

    List<Integer> nowMonthRechargeNums();

    List<Integer> lastMonthRechargeNums();

    List<Integer> nowYearRechargeNums();

    List<Integer> allRechargeNums();
}
