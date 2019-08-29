package com.meiyou.service.impl;

import com.meiyou.mapper.CountUserMapper;
import com.meiyou.service.CountUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Integer nowNums() {
        return mapper.nowNums();
    }

    @Override
    public Integer yesterdayNums() {
        return mapper.yesterdayNums();
    }

    @Override
    public Integer nowWeekNums() {
        return mapper.nowWeekNums();
    }

    @Override
    public Integer nowMonthNums() {
        return mapper.nowMonthNums();
    }

    @Override
    public Integer lastMonthNums() {
        return mapper.lastMonthNums();
    }

    @Override
    public Integer nowYearNums() {
        return mapper.nowYearNums();
    }

    @Override
    public Integer allNums() {
        return mapper.allNums();
    }
}
