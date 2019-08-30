package com.meiyou.service.impl;

import com.meiyou.mapper.CountRewardMapper;
import com.meiyou.service.CountRewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<Integer> countRewardNums() {
        Integer nowNums = nowRewardNums();
        Integer yesterdayNums = yesterdayRewardNums();
        Integer nowWeekNums = nowWeekRewardNums();
        Integer nowMonthNums = nowMonthRewardNums();
        Integer lastMonthNums = lastMonthRewardNums();
        Integer nowYearNums = nowYearRewardNums();
        Integer allNums = allRewardNums();

        List<Integer> rewardList = new ArrayList<>();
        rewardList.add(nowNums);
        rewardList.add(yesterdayNums);
        rewardList.add(nowWeekNums);
        rewardList.add(nowMonthNums);
        rewardList.add(lastMonthNums);
        rewardList.add(nowYearNums);
        rewardList.add(allNums);

        return rewardList;
    }

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
    public Integer nowWeekRewardNums() {
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
