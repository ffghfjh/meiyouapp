package com.meiyou.service;

/**
 * @description:  注册人数业务层接口
 * @author: Mr.Z
 * @create: 2019-08-29 09:37
 **/
public interface CountUserService {

    Integer nowNums();

    Integer yesterdayNums();

    Integer nowWeekNums();

    Integer nowMonthNums();

    Integer lastMonthNums();

    Integer nowYearNums();

    Integer allNums();

}
