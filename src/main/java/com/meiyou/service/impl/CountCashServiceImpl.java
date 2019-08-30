package com.meiyou.service.impl;

import com.meiyou.mapper.CountCashMapper;
import com.meiyou.service.CountCashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 提现金额统计业务层接口实现类
 * @author: Mr.Z
 * @create: 2019-08-30 09:50
 **/
@Service
public class CountCashServiceImpl extends BaseServiceImpl implements CountCashService {

    @Autowired
    CountCashMapper mapper;

    @Override
    public List<Integer> countCashNums() {
        Integer nowNums = nowCashNums();
        Integer yesterdayNums = yesterdayCashNums();
        Integer nowWeekNums = nowWeekCashNums();
        Integer nowMonthNums = nowMonthCashNums();
        Integer lastMonthNums = lastMonthCashNums();
        Integer nowYearNums = nowYearCashNums();
        Integer allNums = allCashNums();

        List<Integer> cashList = new ArrayList<>();
        cashList.add(nowNums);
        cashList.add(yesterdayNums);
        cashList.add(nowWeekNums);
        cashList.add(nowMonthNums);
        cashList.add(lastMonthNums);
        cashList.add(nowYearNums);
        cashList.add(allNums);

        return cashList;
    }

    @Override
    public Integer nowCashNums() {
        List<Integer> list = mapper.nowCashNums();
        return listSum(list);
    }

    @Override
    public Integer yesterdayCashNums() {
        List<Integer> list = mapper.yesterdayCashNums();
        return listSum(list);
    }

    @Override
    public Integer nowWeekCashNums() {
        List<Integer> list = mapper.nowWeekCashNums();
        return listSum(list);
    }

    @Override
    public Integer nowMonthCashNums() {
        List<Integer> list = mapper.nowMonthCashNums();
        return listSum(list);
    }

    @Override
    public Integer lastMonthCashNums() {
        List<Integer> list = mapper.lastMonthCashNums();
        return listSum(list);
    }

    @Override
    public Integer nowYearCashNums() {
        List<Integer> list = mapper.nowYearCashNums();
        return listSum(list);
    }

    @Override
    public Integer allCashNums() {
        List<Integer> list = mapper.allCashNums();
        return listSum(list);
    }
}
