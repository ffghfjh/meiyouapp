package com.meiyou.service.impl;

import com.meiyou.mapper.CountRechargeMapper;
import com.meiyou.service.CountRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 充值业务层接口实现类
 * @author: Mr.Z
 * @create: 2019-08-30 08:44
 **/
@Service
public class CountRechargeServiceImpl extends BaseServiceImpl implements CountRechargeService {

    @Autowired
    CountRechargeMapper mapper;

    @Override
    public List<Integer> countRechargeNums() {
        Integer nowNums = nowRechargeNums();
        Integer yesterdayNums = yesterdayRechargeNums();
        Integer nowWeekNums = nowWeekRechargeNums();
        Integer nowMonthNums = nowMonthRechargeNums();
        Integer lastMonthNums = lastMonthRechargeNums();
        Integer nowYearNums = nowYearRechargeNums();
        Integer allNums = allRechargeNums();

        List<Integer> rechargeList = new ArrayList<>();
        rechargeList.add(nowNums);
        rechargeList.add(yesterdayNums);
        rechargeList.add(nowWeekNums);
        rechargeList.add(nowMonthNums);
        rechargeList.add(lastMonthNums);
        rechargeList.add(nowYearNums);
        rechargeList.add(allNums);

        return rechargeList;
    }

    @Override
    public Integer nowRechargeNums() {
        List<Integer> list = mapper.nowRechargeNums();
        return listSum(list);
    }

    @Override
    public Integer yesterdayRechargeNums() {
        List<Integer> list = mapper.yesterdayRechargeNums();
        return listSum(list);
    }

    @Override
    public Integer nowWeekRechargeNums() {
        List<Integer> list = mapper.nowWeekRechargeNums();
        return listSum(list);
    }

    @Override
    public Integer nowMonthRechargeNums() {
        List<Integer> list = mapper.nowMonthRechargeNums();
        return listSum(list);
    }

    @Override
    public Integer lastMonthRechargeNums() {
        List<Integer> list = mapper.lastMonthRechargeNums();
        return listSum(list);
    }

    @Override
    public Integer nowYearRechargeNums() {
        List<Integer> list = mapper.nowYearRechargeNums();
        return listSum(list);
    }

    @Override
    public Integer allRechargeNums() {
        List<Integer> list = mapper.allRechargeNums();
        return listSum(list);
    }
}
