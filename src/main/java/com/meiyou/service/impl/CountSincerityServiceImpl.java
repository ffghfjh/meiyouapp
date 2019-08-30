package com.meiyou.service.impl;

import com.meiyou.mapper.CountRewardMapper;
import com.meiyou.mapper.CountSincerityMapper;
import com.meiyou.service.CountRewardService;
import com.meiyou.service.CountSincerityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 诚意金业务层接口实现类
 * @author: Mr.Z
 * @create: 2019-08-29 15:54
 **/
@Service
public class CountSincerityServiceImpl extends BaseServiceImpl implements CountSincerityService {

    @Autowired
    CountSincerityMapper mapper;

    @Override
    public List<Integer> countSincerityNums() {
        Integer nowNums = nowSincerityNums();
        Integer yesterdayNums = yesterdaySincerityNums();
        Integer nowWeekNums = nowWeekSincerityNums();
        Integer nowMonthNums = nowMonthSincerityNums();
        Integer lastMonthNums = lastMonthSincerityNums();
        Integer nowYearNums = nowYearSincerityNums();
        Integer allNums = allSincerityNums();

        List<Integer> sincerityList = new ArrayList<>();
        sincerityList.add(nowNums);
        sincerityList.add(yesterdayNums);
        sincerityList.add(nowWeekNums);
        sincerityList.add(nowMonthNums);
        sincerityList.add(lastMonthNums);
        sincerityList.add(nowYearNums);
        sincerityList.add(allNums);

        return sincerityList;
    }

    //本日
    @Override
    public Integer nowSincerityNums() {
        String sincerity_money = getRootMessage("sincerity_money");
        List<Integer> list = mapper.nowSincerityNums();
        Integer nums = listSum(list);
        Integer sincerityMoney = Integer.parseInt(sincerity_money)*nums;
        return sincerityMoney;
    }

    //昨天
    @Override
    public Integer yesterdaySincerityNums() {
        String sincerity_money = getRootMessage("sincerity_money");
        List<Integer> list = mapper.yesterdaySincerityNums();
        Integer nums = listSum(list);
        Integer sincerityMoney = Integer.parseInt(sincerity_money)*nums;
        return sincerityMoney;
    }

    //本周
    @Override
    public Integer nowWeekSincerityNums() {
        String sincerity_money = getRootMessage("sincerity_money");
        List<Integer> list = mapper.nowWeekSincerityNums();
        Integer nums = listSum(list);
        Integer sincerityMoney = Integer.parseInt(sincerity_money)*nums;
        return sincerityMoney;
    }

    //本月
    @Override
    public Integer nowMonthSincerityNums() {
        String sincerity_money = getRootMessage("sincerity_money");
        List<Integer> list = mapper.nowMonthSincerityNums();
        Integer nums = listSum(list);
        Integer sincerityMoney = Integer.parseInt(sincerity_money)*nums;
        return sincerityMoney;
    }

    //上月
    @Override
    public Integer lastMonthSincerityNums() {
        String sincerity_money = getRootMessage("sincerity_money");
        List<Integer> list = mapper.lastMonthSincerityNums();
        Integer nums = listSum(list);
        Integer sincerityMoney = Integer.parseInt(sincerity_money)*nums;
        return sincerityMoney;
    }

    //本年
    @Override
    public Integer nowYearSincerityNums() {
        String sincerity_money = getRootMessage("sincerity_money");
        List<Integer> list = mapper.nowYearSincerityNums();
        Integer nums = listSum(list);
        Integer sincerityMoney = Integer.parseInt(sincerity_money)*nums;
        return sincerityMoney;
    }

    //全部
    @Override
    public Integer allSincerityNums() {
        String sincerity_money = getRootMessage("sincerity_money");
        List<Integer> list = mapper.allSincerityNums();
        Integer nums = listSum(list);
        Integer sincerityMoney = Integer.parseInt(sincerity_money)*nums;
        return sincerityMoney;
    }
}
