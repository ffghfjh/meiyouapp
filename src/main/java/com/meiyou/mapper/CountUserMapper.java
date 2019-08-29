package com.meiyou.mapper;

/**
 * @description: 注册人数持久层
 * @author: Mr.Z
 * @create: 2019-08-29 09:10
 **/
public interface CountUserMapper {

    Integer nowNums();

    Integer yesterdayNums();

    Integer nowWeekNums();

    Integer nowMonthNums();

    Integer lastMonthNums();

    Integer nowYearNums();

    Integer allNums();
}
