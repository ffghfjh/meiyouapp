package com.meiyou.service;

import java.util.List;

/**
 * @description:  报名次数业务层接口
 * @author: Mr.Z
 * @create: 2019-08-29 09:37
 **/
public interface CountAskService {

    List<Integer> countAskNums();

    Integer nowAskNums();

    Integer yesterdayAskNums();

    Integer nowWeekAskNums();

    Integer nowMonthAskNums();

    Integer lastMonthAskNums();

    Integer nowYearAskNums();

    Integer allAskNums();

}
