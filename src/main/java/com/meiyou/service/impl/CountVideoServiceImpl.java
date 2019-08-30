package com.meiyou.service.impl;

import com.meiyou.mapper.CountSincerityMapper;
import com.meiyou.mapper.CountVideoMapper;
import com.meiyou.service.CountSincerityService;
import com.meiyou.service.CountVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 视频聊天收益业务层接口实现类
 * @author: Mr.Z
 * @create: 2019-08-29 15:54
 **/
@Service
public class CountVideoServiceImpl extends BaseServiceImpl implements CountVideoService {

    @Autowired
    CountVideoMapper mapper;

    @Override
    public List<Integer> CountVideoNums() {
        Integer nowNums = nowVideoNums();
        Integer yesterdayNums = yesterdayVideoNums();
        Integer nowWeekNums = nowWeekVideoNums();
        Integer nowMonthNums = nowMonthVideoNums();
        Integer lastMonthNums = lastMonthVideoNums();
        Integer nowYearNums = nowYearVideoNums();
        Integer allNums = allVideoNums();

        List<Integer> videoList = new ArrayList<>();
        videoList.add(nowNums);
        videoList.add(yesterdayNums);
        videoList.add(nowWeekNums);
        videoList.add(nowMonthNums);
        videoList.add(lastMonthNums);
        videoList.add(nowYearNums);
        videoList.add(allNums);

        return videoList;
}

    public Integer nowVideoNums() {
        List<Integer> list = mapper.nowVideoNums();
        return listSum(list);
    }

    public Integer yesterdayVideoNums() {
        List<Integer> list = mapper.yesterdayVideoNums();
        return listSum(list);
    }


    public Integer nowWeekVideoNums() {
        List<Integer> list = mapper.nowWeekVideoNums();
        return listSum(list);
    }


    public Integer nowMonthVideoNums() {
        List<Integer> list = mapper.nowMonthVideoNums();
        return listSum(list);
    }


    public Integer lastMonthVideoNums() {
        List<Integer> list = mapper.lastMonthVideoNums();
        return listSum(list);
    }


    public Integer nowYearVideoNums() {
        List<Integer> list = mapper.nowYearVideoNums();
        return listSum(list);
    }


    public Integer allVideoNums() {
        List<Integer> list = mapper.allVideoNums();
        return listSum(list);
    }
}
