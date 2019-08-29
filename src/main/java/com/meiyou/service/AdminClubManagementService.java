package com.meiyou.service;

import com.meiyou.pojo.Club;

import java.util.List;

/**
 * @description: 按摩会所管理员服务层接口
 * @author: Mr.Z
 * @create: 2019-08-28 10:52
 **/
public interface AdminClubManagementService {

    List<Club> selectAll();

    Club selectByCid(Integer cid);

    Integer countClubNums();
}
