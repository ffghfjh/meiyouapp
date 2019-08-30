package com.meiyou.service.impl;

import com.meiyou.mapper.CountAskMapper;
import com.meiyou.service.CountAskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 报名次数业务层接口实现类
 * @author: Mr.Z
 * @create: 2019-08-29 21:48
 **/
@Service
public class CountAskServiceImpl extends BaseServiceImpl implements CountAskService {

    @Autowired
    CountAskMapper mapper;

    @Override
    public List<Integer> countAskNums() {
        Integer nowNums = nowAskNums();
        Integer yesterdayNums = yesterdayAskNums();
        Integer nowWeekNums = nowWeekAskNums();
        Integer nowMonthNums = nowMonthAskNums();
        Integer lastMonthNums = lastMonthAskNums();
        Integer nowYearNums = nowYearAskNums();
        Integer allNums = allAskNums();

        List<Integer> askList = new ArrayList<>();
        askList.add(nowNums);
        askList.add(yesterdayNums);
        askList.add(nowWeekNums);
        askList.add(nowMonthNums);
        askList.add(lastMonthNums);
        askList.add(nowYearNums);
        askList.add(allNums);

        return askList;
    }

    @Override
    public Integer nowAskNums() {
        List<Integer> list = mapper.nowAskNums();
        Integer sum = listSum(list);
        return sum;
    }

    @Override
    public Integer yesterdayAskNums() {
        List<Integer> list = mapper.yesterdayAskNums();
        Integer sum = listSum(list);
        return sum;
    }

    @Override
    public Integer nowWeekAskNums() {
        List<Integer> list = mapper.nowWeekAskNums();
        Integer sum = listSum(list);
        return sum;
    }

    @Override
    public Integer nowMonthAskNums() {
        List<Integer> list = mapper.nowMonthAskNums();
        Integer sum = listSum(list);
        return sum;
    }

    @Override
    public Integer lastMonthAskNums() {
        List<Integer> list = mapper.lastMonthAskNums();
        Integer sum = listSum(list);
        return sum;
    }

    @Override
    public Integer nowYearAskNums() {
        List<Integer> list = mapper.nowYearAskNums();
        Integer sum = listSum(list);
        return sum;
    }

    @Override
    public Integer allAskNums() {
        List<Integer> list = mapper.allAskNums();
        Integer sum = listSum(list);
        return sum;
    }
}
