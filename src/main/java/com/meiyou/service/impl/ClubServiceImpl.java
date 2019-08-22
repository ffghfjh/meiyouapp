package com.meiyou.service.impl;

import com.meiyou.mapper.ClubMapper;
import com.meiyou.pojo.Club;
import com.meiyou.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description: 会所业务层接口实现类
 * @author: Mr.Z
 * @create: 2019-08-21 14:31
 **/
@Service
public class ClubServiceImpl implements ClubService {

    @Autowired
    ClubMapper clubMapper;

    @Override
    public void addClub(Club club,Integer time) {
        Date now = new Date();
        club.setCreateTime(now);
        club.setUpdateTime(now);
        //添加过期时间
        Long millisecond = now.getTime()+24*60*60*1000*time;
        club.setOutTime(new Date(millisecond));
        clubMapper.insert(club);
    }

    @Override
    public void deleteClub(Integer id) {
    }

    @Override
    public List<Club> selectByUid(Integer id) {
        return null;
    }

    @Override
    public Club selectByCid(Integer id) {
        return null;
    }

    @Override
    @Cacheable(cacheNames = "Club")
    public List<Club> selectClub(float longitude, float latitude) {
        return null;
    }
}
