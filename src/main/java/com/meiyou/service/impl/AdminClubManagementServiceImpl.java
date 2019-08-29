package com.meiyou.service.impl;

import com.meiyou.mapper.ClubMapper;
import com.meiyou.pojo.Club;
import com.meiyou.pojo.ClubExample;
import com.meiyou.service.AdminClubManagementService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Mr.Z
 * @create: 2019-08-28 10:55
 **/
@Service
public class AdminClubManagementServiceImpl implements AdminClubManagementService {
    @Autowired
    ClubMapper clubMapper;

    @Override
    public List<Club> selectAll() {
        return findAll();
    }

    @Override
    public Club selectByCid(Integer cid) {
        return clubMapper.selectByPrimaryKey(cid);
    }

    @Override
    public Integer countClubNums() {
        return null;
    }

    /**
     * 查找全部Club
     * @return
     */
    public List<Club> findAll(){
        ClubExample clubExample = new ClubExample();
        clubExample.createCriteria();
        List<Club> clubs = clubMapper.selectByExample(clubExample);
        return clubs;
    }
}
