package com.meiyou.service;

import com.meiyou.pojo.Club;

import java.util.List;
import java.util.Map;

/**
 * @description: 按摩会所管理员服务层接口
 * @author: Mr.Z
 * @create: 2019-08-28 10:52
 **/
public interface AdminClubManagementService {

    List<Club> selectAll();

    Club selectByCid(Integer cid);

    Integer countClubNums();

    /**
     * 分页查询所有的推拿会所
     */
    Map<String,Object> selectAllClubByPage(Integer pageNo, Integer pageSize, Integer publisherId, Integer state);
}
