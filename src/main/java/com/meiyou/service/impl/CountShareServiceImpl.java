package com.meiyou.service.impl;

//import com.meiyou.mapper.CountShareMapper;
import com.meiyou.mapper.CountShareMapper;
import com.meiyou.service.CountShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 分享金币业务层接口实现类
 * @author: Mr.Z
 * @create: 2019-08-29 09:55
 **/
@Service
public class CountShareServiceImpl extends BaseServiceImpl implements CountShareService {

    @Autowired
    CountShareMapper mapper;

    @Override
    public List<Integer> countShareMoney() {
        Integer nowMoney = nowShareMoney();
        Integer yesterdayMoney = yesterdayShareMoney();
        Integer nowWeekMoney = nowWeekShareMoney();
        Integer nowMonthMoney = nowMonthShareMoney();
        Integer lastMonthMoney = lastMonthShareMoney();
        Integer nowYearMoney = nowYearShareMoney();
        Integer allMoney = allShareMoney();

        List<Integer> videoList = new ArrayList<>();
        videoList.add(nowMoney);
        videoList.add(yesterdayMoney);
        videoList.add(nowWeekMoney);
        videoList.add(nowMonthMoney);
        videoList.add(lastMonthMoney);
        videoList.add(nowYearMoney);
        videoList.add(allMoney);

        return videoList;
    }

    @Override
    public Integer nowShareMoney() {
        List<Integer> list = mapper.nowShareMoney();
        return listSum(list);
    }

    @Override
    public Integer yesterdayShareMoney() {
        List<Integer> list = mapper.yesterdayShareMoney();
        return listSum(list);
    }

    @Override
    public Integer nowWeekShareMoney() {
        List<Integer> list = mapper.nowWeekShareMoney();
        return listSum(list);
    }

    @Override
    public Integer nowMonthShareMoney() {
        List<Integer> list = mapper.nowMonthShareMoney();
        return listSum(list);
    }

    @Override
    public Integer lastMonthShareMoney() {
        List<Integer> list = mapper.lastMonthShareMoney();
        return listSum(list);
    }

    @Override
    public Integer nowYearShareMoney() {
        List<Integer> list = mapper.nowYearShareMoney();
        return listSum(list);
    }

    @Override
    public Integer allShareMoney() {
        List<Integer> list = mapper.allShareMoney();
        return listSum(list);
    }

}
