package com.meiyou.service.impl;

import com.meiyou.mapper.CountRewardMapper;
import com.meiyou.service.CountRewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 悬赏金业务层接口实现类
 * @author: Mr.Z
 * @create: 2019-08-29 15:54
 **/
@Service
public class CountRewardServiceImpl extends BaseServiceImpl implements CountRewardService {

    @Autowired
    CountRewardMapper mapper;

    @Override
    public Integer nowRewardNums() {
        List<Integer> list = mapper.nowRewardNums();
        return listSum(list);
    }

    @Override
    public Integer yesterdayRewardNums() {
        List<Integer> list = mapper.yesterdayRewardNums();
        return listSum(list);
    }

    @Override
    public Integer nowWeekRewardhNums() {
        List<Integer> list = mapper.nowWeekRewardNums();
        return listSum(list);
    }

    @Override
    public Integer nowMonthRewardNums() {
        List<Integer> list = mapper.nowMonthRewardNums();
        return listSum(list);
    }

    @Override
    public Integer lastMonthRewardNums() {
        List<Integer> list = mapper.lastMonthRewardNums();
        return listSum(list);
    }

    @Override
    public Integer nowYearRewardNums() {
        List<Integer> list = mapper.nowYearRewardNums();
        return listSum(list);
    }

    @Override
    public Integer allRewardNums() {
        List<Integer> list = mapper.allRewardNums();
        return listSum(list);
    }
}
