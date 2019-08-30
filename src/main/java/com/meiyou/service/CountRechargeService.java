package com.meiyou.service;

/**
 * @description:  充值业务层接口
 * @author: Mr.Z
 * @create: 2019-08-29 09:37
 **/
public interface CountRechargeService {

    Integer nowRechargeNums();

    Integer yesterdayRechargeNums();

    Integer nowWeekRechargeNums();

    Integer nowMonthRechargeNums();

    Integer lastMonthRechargeNums();

    Integer nowYearRechargeNums();

    Integer allRechargeNums();

}
