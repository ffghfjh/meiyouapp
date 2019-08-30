package com.meiyou.service.impl;

import com.meiyou.service.CountPublishMoneyService;
import com.meiyou.service.CountPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 我的发布金
 * @author: Mr.Z
 * @create: 2019-08-29 22:10
 **/
@Service
public class CountPublishMoneyServiceImpl extends BaseServiceImpl implements CountPublishMoneyService {

    @Autowired
    CountPublishService service;

    @Override
    public List<Integer> countPublishNums() {
        Integer nowNums = nowPublishNums();
        Integer yesterdayNums = yesterdayPublishNums();
        Integer nowWeekNums = nowWeekPublishNums();
        Integer nowMonthNums = nowMonthPublishNums();
        Integer lastMonthNums = lastMonthPublishNums();
        Integer nowYearNums = nowYearPublishNums();
        Integer allNums = allPublishNums();

        List<Integer> publishMoneyList = new ArrayList<>();
        publishMoneyList.add(nowNums);
        publishMoneyList.add(yesterdayNums);
        publishMoneyList.add(nowWeekNums);
        publishMoneyList.add(nowMonthNums);
        publishMoneyList.add(lastMonthNums);
        publishMoneyList.add(nowYearNums);
        publishMoneyList.add(allNums);

        return publishMoneyList;
    }

    @Override
    public Integer nowPublishNums() {
        String publish_money = getRootMessage("publish_money");
        Integer publishMoney = Integer.parseInt(publish_money) * service.nowPublishNums();
        return publishMoney;
    }

    @Override
    public Integer yesterdayPublishNums() {
        String publish_money = getRootMessage("publish_money");
        Integer publishMoney = Integer.parseInt(publish_money) * service.yesterdayPublishNums();
        return publishMoney;
    }

    @Override
    public Integer nowWeekPublishNums() {
        String publish_money = getRootMessage("publish_money");
        Integer publishMoney = Integer.parseInt(publish_money) * service.nowWeekPublishNums();
        return publishMoney;
    }

    @Override
    public Integer nowMonthPublishNums() {
        String publish_money = getRootMessage("publish_money");
        Integer publishMoney = Integer.parseInt(publish_money) * service.nowMonthPublishNums();
        return publishMoney;
    }

    @Override
    public Integer lastMonthPublishNums() {
        String publish_money = getRootMessage("publish_money");
        Integer publishMoney = Integer.parseInt(publish_money) * service.lastMonthPublishNums();
        return publishMoney;
    }

    @Override
    public Integer nowYearPublishNums() {
        String publish_money = getRootMessage("publish_money");
        Integer publishMoney = Integer.parseInt(publish_money) * service.nowYearPublishNums();
        return publishMoney;
    }

    @Override
    public Integer allPublishNums() {
        String publish_money = getRootMessage("publish_money");
        Integer publishMoney = Integer.parseInt(publish_money) * service.allPublishNums();
        return publishMoney;
    }
}
