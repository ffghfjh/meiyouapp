package com.meiyou.service.impl;

import com.meiyou.mapper.CountSincerityMapper;
import com.meiyou.mapper.CountVideoMapper;
import com.meiyou.service.CountSincerityService;
import com.meiyou.service.CountVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 视频聊天收益业务层接口实现类
 * @author: Mr.Z
 * @create: 2019-08-29 15:54
 **/
@Service
public class CountVideoServiceImpl extends BaseServiceImpl implements CountVideoService {

    @Autowired
    CountVideoMapper mapper;

    @Override
    public Integer nowVideoNums() {
        List<Integer> list = mapper.nowVideoNums();
        return listSum(list);
    }

    @Override
    public Integer yesterdayVideoNums() {
        List<Integer> list = mapper.yesterdayVideoNums();
        return listSum(list);
    }

    @Override
    public Integer nowWeekVideoNums() {
        List<Integer> list = mapper.nowWeekVideoNums();
        return listSum(list);
    }

    @Override
    public Integer nowMonthVideoNums() {
        List<Integer> list = mapper.nowMonthVideoNums();
        return listSum(list);
    }

    @Override
    public Integer lastMonthVideoNums() {
        List<Integer> list = mapper.lastMonthVideoNums();
        return listSum(list);
    }

    @Override
    public Integer nowYearVideoNums() {
        List<Integer> list = mapper.nowYearVideoNums();
        return listSum(list);
    }

    @Override
    public Integer allVideoNums() {
        List<Integer> list = mapper.allVideoNums();
        return listSum(list);
    }
}
