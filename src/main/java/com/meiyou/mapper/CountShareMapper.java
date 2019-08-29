package com.meiyou.mapper;

import java.util.List;

/**
 * @description: 分享金币持久层
 * @author: Mr.Z
 * @create: 2019-08-29 09:10
 **/
public interface CountShareMapper {

    List<Integer> nowShareMoney();

    List<Integer> yesterdayShareMoney();

    List<Integer> nowWeekShareMoney();

    List<Integer> nowMonthShareMoney();

    List<Integer> lastMonthShareMoney();

    List<Integer> nowYearShareMoney();

    List<Integer> allShareMoney();
}
