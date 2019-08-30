package com.meiyou.mapper;

import java.util.List;

/**
 * @description: 视频聊天金持久层
 * @author: Mr.Z
 * @create: 2019-08-29 09:10
 **/
public interface CountVideoMapper {

    List<Integer> nowVideoNums();

    List<Integer> yesterdayVideoNums();

    List<Integer> nowWeekVideoNums();

    List<Integer> nowMonthVideoNums();

    List<Integer> lastMonthVideoNums();

    List<Integer> nowYearVideoNums();

    List<Integer> allVideoNums();
}
