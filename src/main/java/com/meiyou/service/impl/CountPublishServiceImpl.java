package com.meiyou.service.impl;

import com.meiyou.mapper.CountPublishMapper;
import com.meiyou.service.CountPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 发布次数业务层接口
 * @author: Mr.Z
 * @create: 2019-08-29 11:01
 **/
@Service
public class CountPublishServiceImpl extends BaseServiceImpl implements CountPublishService {

    @Autowired
    CountPublishMapper publishMapper;

    @Override
    public List<Integer> countPublishNums() {
        Integer nowNums = nowPublishNums();
        Integer yesterdayNums = yesterdayPublishNums();
        Integer nowWeekNums = nowWeekPublishNums();
        Integer nowMonthNums = nowMonthPublishNums();
        Integer lastMonthNums = lastMonthPublishNums();
        Integer nowYearNums = nowYearPublishNums();
        Integer allNums = allPublishNums();

        List<Integer> publishList = new ArrayList<>();
        publishList.add(nowNums);
        publishList.add(yesterdayNums);
        publishList.add(nowWeekNums);
        publishList.add(nowMonthNums);
        publishList.add(lastMonthNums);
        publishList.add(nowYearNums);
        publishList.add(allNums);

        return publishList;
    }

    @Override
    public Integer nowPublishNums() {
        List<Integer> list = publishMapper.nowPublishNums();
        return listSum(list);
    }

    @Override
    public Integer yesterdayPublishNums() {
        List<Integer> list = publishMapper.yesterdayPublishNums();
        return listSum(list);
    }

    @Override
    public Integer nowWeekPublishNums() {
        List<Integer> list = publishMapper.nowWeekPublishNums();
        return listSum(list);
    }

    @Override
    public Integer nowMonthPublishNums() {
        List<Integer> list = publishMapper.nowMonthPublishNums();
        return listSum(list);
    }

    @Override
    public Integer lastMonthPublishNums() {
        List<Integer> list = publishMapper.lastMonthPublishNums();
        return listSum(list);
    }

    @Override
    public Integer nowYearPublishNums() {
        List<Integer> list = publishMapper.nowYearPublishNums();
        return listSum(list);
    }

    @Override
    public Integer allPublishNums() {
        List<Integer> list = publishMapper.allPublishNums();
        return listSum(list);
    }
}
