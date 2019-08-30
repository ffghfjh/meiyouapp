package com.meiyou.service;

import java.util.List;

/**
 * @description:  分享金币业务层接口
 * @author: Mr.Z
 * @create: 2019-08-29 09:37
 **/
public interface CountShareService {

    List<Integer> countShareMoney();

    Integer nowShareMoney();

    Integer yesterdayShareMoney();

    Integer nowWeekShareMoney();

    Integer nowMonthShareMoney();

    Integer lastMonthShareMoney();

    Integer nowYearShareMoney();

    Integer allShareMoney();

}
