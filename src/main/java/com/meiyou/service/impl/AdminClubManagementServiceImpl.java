package com.meiyou.service.impl;

import com.meiyou.mapper.ClubMapper;
import com.meiyou.pojo.Club;
import com.meiyou.pojo.ClubExample;
import com.meiyou.service.AdminClubManagementService;
import com.meiyou.utils.LayuiDataUtil;
import com.meiyou.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * @Description: 分页查询所有的推拿会所
     * @Author: JK
     * @Date: 2019/8/29
     */
    @Override
    public Map<String, Object> selectAllClubByPage(Integer pageNo, Integer pageSize, Integer publisherId, Integer state) {
        Page<Club> page = new Page<>();
        Integer offset = pageSize * (pageNo - 1);
        ClubExample clubExample = new ClubExample();

        if (publisherId != null){
            clubExample.createCriteria().andPublishIdEqualTo(publisherId);
            List<Club> list = clubMapper.selectByExample(clubExample);
            if (list.size() == 0){
                Map<String, Object> map = new HashMap<>();
                map.put("code",200);
                map.put("msg","没有数据");
                return map;
            }
            clubExample.setPageNo(offset);
            clubExample.setPageSize(pageSize);
            List<Club> tours = clubMapper.selectByExample(clubExample);
            page.setCount(list.size());
            page.setList(tours);
            return LayuiDataUtil.getLayuiData(page);
        }

        if (state != null){
            clubExample.createCriteria().andStateEqualTo(state);
            List<Club> list = clubMapper.selectByExample(clubExample);
            if (list.size() == 0){
                Map<String, Object> map = new HashMap<>();
                map.put("code",200);
                map.put("msg","没有数据");
                return map;
            }
            clubExample.setPageNo(offset);
            clubExample.setPageSize(pageSize);
            List<Club> appointments = clubMapper.selectByExample(clubExample);
            page.setCount(list.size());
            page.setList(appointments);
            return LayuiDataUtil.getLayuiData(page);
        }


        List<Club> list = clubMapper.selectByExample(clubExample);
        if (list.size() == 0){
            Map<String, Object> map = new HashMap<>();
            map.put("code",200);
            map.put("msg","没有数据");
            return map;
        }

        clubExample.setPageNo(offset);
        clubExample.setPageSize(pageSize);
        List<Club> appointments = clubMapper.selectByExample(clubExample);
        page.setCount(list.size());
        page.setList(appointments);
        return LayuiDataUtil.getLayuiData(page);
    }
}
