package com.meiyou.service;

/**
 * @description:  视频聊天收益业务层接口
 * @author: Mr.Z
 * @create: 2019-08-29 09:37
 **/
public interface CountVideoService {

    Integer nowVideoNums();

    Integer yesterdayVideoNums();

    Integer nowWeekVideoNums();

    Integer nowMonthVideoNums();

    Integer lastMonthVideoNums();

    Integer nowYearVideoNums();

    Integer allVideoNums();

}
