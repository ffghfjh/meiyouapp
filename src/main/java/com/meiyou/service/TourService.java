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
    Msg insert(Tour tour, String password, String token, double latitude,double longitude);

    /**
     * 查询我的发布
     */
    Msg selectTourList(String uid,String token);

    /**
     * 取消发布
     */
    Msg deletePublish(Integer id,String token);

    /**
     * 开始报名
     */
    Msg startEnrollment(String uid,String password,Integer id,String token);

    /**
     * 取消报名
     */
    Msg endEnrollment(String uid,Integer id,String token);

    /**
     * 查询所有报名某个旅游的人员信息
     */
    Msg selectTourAskList(String uid,Integer appointId,String token);

    /**
     * 从所有报名某个旅游的人员信息中选择一个进行确认，
     * 没有被选中的人退还报名金
     */
    Msg confirmUserId(String uid,Integer askerId,Integer appointId,String token);

    /**
     * 对方取消赴约，重新发布，不退还报名金
     */
    Msg endTour(String uid,Integer id,String token);

    /**
     * 自己重新发布，退还报名金
     */
    Msg againRelease(String uid,Integer id,String token);

    /**
     * 报名人确认赴约
     */
    Msg confirmTour(String uid,Integer id,String token);

    /**
     * 确认报名人已到达
     */
    Msg confirmArrive(String uid,Integer id,String token);

    /**
     * 查看热门约会
     */
    Msg selectHotTour(String uid,String token,double latitude, double longitude);
}
