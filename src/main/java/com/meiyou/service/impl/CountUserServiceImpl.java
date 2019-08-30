package com.meiyou.service.impl;

import com.meiyou.mapper.CountUserMapper;
import com.meiyou.service.CountUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 注册人数业务层接口实现类
 * @author: Mr.Z
 * @create: 2019-08-29 09:37
 **/
@Service
public class CountUserServiceImpl implements CountUserService {

    @Autowired
    CountUserMapper mapper;

    @Override
    public List<Integer> CountUserNums() {
        Integer nowNums = mapper.nowNums();
        Integer yesterdayNums = mapper.yesterdayNums();
        Integer nowWeekNums = mapper.nowWeekNums();
        Integer nowMonthNums = mapper.nowMonthNums();
        Integer lastMonthNums = mapper.lastMonthNums();
        Integer nowYearNums = mapper.nowYearNums();
        Integer allNums = mapper.allNums();

        List<Integer> userList = new ArrayList<>();
        userList.add(nowNums);
        userList.add(yesterdayNums);
        userList.add(nowWeekNums);
        userList.add(nowMonthNums);
        userList.add(lastMonthNums);
        userList.add(nowYearNums);
        userList.add(allNums);
        return userList;
    }

}
