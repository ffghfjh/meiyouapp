package com.meiyou.service;

import com.meiyou.pojo.Tour;
import com.meiyou.utils.Msg;

/**
 * @program: meiyou
 * @description: 同城旅游接口
 * @author: JK
 * @create: 2019-08-22 19:39
 **/
public interface TourService {
    /**
     * 发布约会旅游
     */
    Msg insert(Tour tour, String password, String token);
}
