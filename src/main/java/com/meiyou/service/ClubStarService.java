package com.meiyou.service;

import com.meiyou.pojo.ClubStar;

import java.util.List;

/**
 * @description: 按摩会所评论星级业务层接口
 * @author: Mr.Z
 * @create: 2019-08-21 20:42
 **/
public interface ClubStarService {

    /**
     * 添加按摩会所评星
     * @param clubStar
     */
    void addClubStar(ClubStar clubStar);

    /**
     * 通过按摩会所id查找全部评论的星级
     * @param id
     * @return
     */
    List<Integer> selectClubStar(Integer id);
}
