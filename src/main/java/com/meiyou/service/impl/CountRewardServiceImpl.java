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
        List<List<Integer>> lists = mapper.nowRewardNums();
        return null;
    }

    @Override
    public Integer yesterdayRewardNums() {
        return null;
    }

    @Override
    public Integer nowWeekRewardhNums() {
        return null;
    }

    @Override
    public Integer nowMonthRewardNums() {
        return null;
    }

    @Override
    public Integer lastMonthRewardNums() {
        return null;
    }

    @Override
    public Integer nowYearRewardNums() {
        return null;
    }

    @Override
    public Integer allRewardNums() {
        return null;
    }
}
