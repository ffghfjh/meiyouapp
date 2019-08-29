package com.meiyou.service;

import com.meiyou.model.StarVo;

import java.util.List;

/**
 * @program: meiyou
 * @description: 我的评星接口
 * @author: JK
 * @create: 2019-08-26 15:26
 **/
public interface MyStarService {

    List<StarVo> selectClubStar(Integer uid);

    List<StarVo> selectShopStar(Integer uid);
}
