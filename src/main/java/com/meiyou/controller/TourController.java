package com.meiyou.controller;

import com.meiyou.pojo.Tour;
import com.meiyou.service.TourService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @program: meiyou
 * @description: 同城旅游控制层
 * @author: JK
 * @create: 2019-08-22 19:53
 **/
@Api(value = "同城旅游控制层", tags = {"同城旅游控制层"})
@RestController
public class TourController {
    @Autowired
    private TourService tourService;


    @ApiOperation(value = "发布旅游", notes = "发布旅游", httpMethod = "POST")
    @PostMapping(value = "/insertTour")
    public Msg insertTour(
            @RequestParam(value = "publishId",required = false)Integer publishId,
            @RequestParam(value = "startAddress",required = false)String startAddress,
            @RequestParam(value = "endAddress",required = false)String endAddress,
            @RequestParam(value = "goTime",required = false)String goTime,
            @RequestParam(value = "goMessage",required = false)String goMessage,
            @RequestParam(value = "reward",required = false)Integer reward,
            @RequestParam(value = "payType",required = false)int payType,
            @RequestParam(value = "password",required = false)String password,
            @RequestParam(value = "token",required = false)String token,
            double latitude,double longitude){

        Tour tour = new Tour();
        tour.setCreateTime(new Date());
        tour.setUpdateTime(new Date());
        tour.setPublishId(publishId);
        tour.setStartAddress(startAddress);
        tour.setEndAddress(endAddress);
        tour.setGoTime(goTime);
        tour.setGoMessage(goMessage);
        tour.setNeedNum(1);
        tour.setState(1);
        tour.setReward(reward);
        tour.setPayType(payType);
        return tourService.insert(tour,password,token,latitude,longitude);
    }

    /**
     * @Description: 取消发布旅游订单
     * @Author: JK
     * @Date: 2019/8/22
     */
    @ApiOperation(value = "取消发布旅游订单", notes = "取消发布旅游订单", httpMethod = "POST")
    @PostMapping(value = "/deleteTourPublish")
    public Msg deleteTourPublish(@RequestParam(value = "id", required = false) Integer id,
                             @RequestParam(value = "token", required = false) String token) {
        return tourService.deleteTourPublish(id, token);

    }

    /**
     * @Description: 查询所有报名某个旅游的人员信息
     * @Author: JK
     * @Date: 2019/8/22
     */
    @ApiOperation(value = "查询所有报名某个旅游的人员信息", notes = "查询所有报名某个旅游的人员信息", httpMethod = "POST")
    @PostMapping(value = "/selectTourAskList")
    public Msg selectTourAskList(String uid,Integer appointId,String token) {
        Msg msg = tourService.selectTourAskList(uid,appointId,token);
        return msg;
    }

    /**
     * @Description: 从多个旅游订单中选择一个进行报名
     * @Author: JK
     * @Date: 2019/8/22
     */
    @ApiOperation(value = "从多个旅游订单中选择一个进行报名", notes = "从多个旅游订单中选择一个进行报名", httpMethod = "POST")
    @PostMapping(value = "/tourAsk")
    public Msg tourAsk(@RequestParam(value = "uid", required = false) String uid,
                               @RequestParam(value = "id", required = false) Integer id,
                               @RequestParam(value = "password", required = false) String password,
                               @RequestParam(value = "token", required = false) String token) {
        return tourService.tourAsk(uid, password, id, token);
    }

    /**
     * @Description: 取消报名, 退还美金
     * @Author: JK
     * @Date: 2019/8/23
     */
    @ApiOperation(value = "取消报名", notes = "取消报名, 退还美金", httpMethod = "POST")
    @PostMapping(value = "/endTourAsk")
    public Msg endTourAsk(String uid, Integer id, String token){
        return tourService.endTourAsk(uid,id,token);
    }

    /**
     * @Description: 从所有报名某个旅游的人员信息中选择一个进行确认，
     *               没有被选中的人退还报名金
     * @Author: JK
     * @Date: 2019/8/22
     */
    @ApiOperation(value = "确定旅游人选", notes = "从所有报名某个旅游的人员信息中选择一个进行确认，没有被选中的人退还报名金", httpMethod = "POST")
    @PostMapping(value = "/confirmTourist")
    public Msg confirmTourist(String uid,Integer askerId,Integer appointId,String token) {
        return tourService.confirmTourist(uid,askerId, appointId,token);
    }


    /**
     * @Description: 对方取消赴约，重新发布，不退还报名金
     * @Author: JK
     * @Date: 2019/8/24
     */
    @ApiOperation(value = "取消赴约", notes = "对方取消赴约，重新发布，不退还报名金", httpMethod = "POST")
    @PostMapping(value = "/endTour")
    public Msg endTour(String uid, Integer id, String token) {
        return tourService.endTour(uid,id,token);
    }

    /**
     * @Description: 由于发布者自己原因重新发布，退还报名金
     * @Author: JK
     * @Date: 2019/8/24
     */
    @ApiOperation(value = "重新发布", notes = "由于发布者自己原因重新发布，退还报名金", httpMethod = "POST")
    @PostMapping(value = "/againReleaseTour")
    public Msg againReleaseTour(String uid, Integer id, String token) {
        return tourService.againReleaseTour(uid,id,token);
    }

    /**
     * @Description: 报名人确认赴约
     * @Author: JK
     * @Date: 2019/8/24
     */
    @ApiOperation(value = "报名人确认赴约", notes = "报名人确认赴约", httpMethod = "POST")
    @PostMapping(value = "/confirmTour")
    public Msg confirmTour(String uid, Integer id, String token) {
        return tourService.confirmTour(uid,id,token);
    }

    /**
     * @Description: 确认报名人已到达
     * @Author: JK
     * @Date: 2019/8/24
     */
    @ApiOperation(value = "确认报名人已到达", notes = "确认报名人已到达", httpMethod = "POST")
    @PostMapping(value = "/confirmTourArrive")
    public Msg confirmTourArrive(String uid, Integer id, String token) {
        return tourService.confirmTourArrive(uid,id,token);
    }

    /**
     * @Description: 查看热门旅游
     * @Author: JK
     * @Date: 2019/8/24
     */
    @ApiOperation(value = "查看热门旅游", notes = "查看热门旅游", httpMethod = "POST")
    @PostMapping(value = "/selectHotTour")
    public Msg selectHotTour(String uid, String token,double latitude, double longitude) {
        Msg msg = tourService.selectHotTour(uid, token, latitude, longitude);
        return msg;
    }

    /**
    * @Description: 查询报名旅游的全部人员
    * @Author: JK
    * @Date: 2019/8/29
    */
    @ApiOperation(value = "查询报名旅游的全部人员", notes = "查询报名旅游的全部人员", httpMethod = "POST")
    @PostMapping(value = "/selectAllTourById")
    public Msg selectAllTourById(Integer uid, String token, Integer id) {
        return tourService.selectAllTourById(uid, token,id);
    }

    /**
    * @Description: 删除我的发布中旅游已完成和已取消的记录
    * @Author: JK
    * @Date: 2019/9/10
    */
    @ApiOperation(value = "删除我的发布中旅游已完成和已取消的记录", notes = "删除我的发布中旅游已完成和已取消的记录", httpMethod = "POST")
    @PostMapping(value = "/delectMyPublishTourRecord")
    public Msg delectMyPublishTourRecord(Integer uid, String token, Integer id) {
        return tourService.delectMyPublishTourRecord(uid, token,id);
    }

    /**
    * @Description: 删除我的报名中旅游已完成和已取消的记录
    * @Author: JK
    * @Date: 2019/9/10
    */
    @ApiOperation(value = "删除我的报名中旅游已完成和已取消的记录", notes = "删除我的报名中旅游已完成和已取消的记录", httpMethod = "POST")
    @PostMapping(value = "/delectMyAskTourRecord")
    public Msg delectMyAskTourRecord(Integer uid, String token, Integer id) {
        return tourService.delectMyAskTourRecord(uid, token,id);
    }

}
