package com.meiyou.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.meiyou.pojo.Appointment;
import com.meiyou.service.AppointmentService;
import com.meiyou.utils.FileUploadUtil;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @program: meiyouapp
 * @description: 约会控制层
 * @author: JK
 * @create: 2019-08-21 13:59
 **/
@Api(value = "约会控制层", tags = {"约会控制层"})
@RestController
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    /**
     * @Description: 发布约会
     * @Author: JK
     * @Date: 2019/8/22
     */
    @ApiOperation(value = "发布约会", notes = "发布约会", httpMethod = "POST")
    @PostMapping(value = "/insertAppointment")
    public Msg insertAppointment(@RequestParam(value = "publisherId", required = false) Integer publisherId,
                                 @RequestParam(value = "appointAddress", required = false) String appointAddress,
                                 @RequestParam(value = "appointTime", required = false) String appointTime,
                                 @RequestParam(value = "appointContext", required = false) String appointContext,
                                 @RequestParam(value = "payType", required = false) int payType,
                                 @RequestParam(value = "reward", required = false) Integer reward,
                                 MultipartFile[] files,
                                 @RequestParam(value = "password", required = false) String password,
                                 @RequestParam(value = "token", required = false) String token,
                                 double latitude,double longitude,
                                 HttpServletRequest request
    ) {

        System.out.println("发布约会："+files.length);
        //使用Hutool进行json操作
        JSONArray array = JSONUtil.createArray();
        for (MultipartFile file : files) {
            Msg msg = FileUploadUtil.uploadUtil(file, "appointment", request);
            if (msg.getCode() == 100) {
                array.add(msg.getExtend().get("path"));
            }
        }
        if (array.size() == 0) {
            return Msg.fail();
        }
        Appointment appointment = new Appointment();
        appointment.setCreateTime(new Date());
        appointment.setUpdateTime(new Date());
        appointment.setPublisherId(publisherId);
        appointment.setAppointAddress(appointAddress);
        appointment.setAppointTime(appointTime);
        appointment.setAppointContext(appointContext);
        appointment.setNeedNumber(1);
        appointment.setReward(reward);
        appointment.setPayType(payType);
        appointment.setState(1);
        appointment.setAppointImgs(array.toString());


        return appointmentService.insert(appointment, password, token, latitude,longitude);
    }


    /**
     * @Description: 取消发布约会订单
     * @Author: JK
     * @Date: 2019/8/22
     */
    @ApiOperation(value = "取消发布约会订单", notes = "取消发布约会订单", httpMethod = "POST")
    @PostMapping(value = "/deleteAppointmentPublish")
    public Msg deleteAppointmentPublish(@RequestParam(value = "id", required = false) Integer id,
                             @RequestParam(value = "token", required = false) String token) {
        return appointmentService.deleteAppointmentPublish(id, token);
    }

    /**
     * @Description: 查询所有报名某个约会的人员信息
     * @Author: JK
     * @Date: 2019/8/22
     */
    @ApiOperation(value = "查询所有报名某个约会的人员信息", notes = "查询所有报名某个约会的人员信息", httpMethod = "POST")
    @PostMapping(value = "/selectAppointAskList")
    public Msg selectAppointAskList(String uid,Integer appointId,String token) {
        Msg msg = appointmentService.selectAppointAskList(uid,appointId,token);
        return msg;
    }

    /**
     * @Description: 从多个约会订单中选择一个进行报名
     * @Author: JK
     * @Date: 2019/8/22
     */
    @ApiOperation(value = "从多个约会订单中选择一个进行报名", notes = "从多个约会订单中选择一个进行报名", httpMethod = "POST")
    @PostMapping(value = "/appointmentAsk")
    public Msg appointmentAsk(@RequestParam(value = "uid", required = false) String uid,
                               @RequestParam(value = "id", required = false) Integer id,
                               @RequestParam(value = "password", required = false) String password,
                               @RequestParam(value = "token", required = false) String token) {
        return appointmentService.appointmentAsk(uid, password, id, token);
    }

    /**
     * @Description: 取消报名, 退还美金
     * @Author: JK
     * @Date: 2019/8/23
     */
    @ApiOperation(value = "取消报名", notes = "取消报名, 退还美金", httpMethod = "POST")
    @PostMapping(value = "/endAppointmentAsk")
    public Msg endAppointmentAsk(String uid, Integer id, String token){
        return appointmentService.endAppointmentAsk(uid,id,token);
    }

    /**
     * @Description: 从所有报名某个约会的人员信息中选择一个进行确认，
     *               没有被选中的人退还报名金
     * @Author: JK
     * @Date: 2019/8/22
     */
    @ApiOperation(value = "确定约会人选", notes = "从所有报名某个约会的人员信息中选择一个进行确认，没有被选中的人退还报名金", httpMethod = "POST")
    @PostMapping(value = "/confirmAppointmentUserId")
    public Msg confirmAppointmentUserId(String uid,Integer askerId,Integer appointId,String token) {
        return appointmentService.confirmAppointmentUserId(uid,askerId, appointId,token);
    }


    /**
     * @Description: 对方取消赴约，重新发布，不退还报名金
     * @Author: JK
     * @Date: 2019/8/24
     */
    @ApiOperation(value = "取消赴约", notes = "对方取消赴约，重新发布，不退还报名金", httpMethod = "POST")
    @PostMapping(value = "/endAppointment")
    public Msg endAppointment(String uid, Integer id, String token) {
        return appointmentService.endAppointment(uid,id,token);
    }

    /**
     * @Description: 由于发布者自己原因重新发布，退还报名金
     * @Author: JK
     * @Date: 2019/8/24
     */
    @ApiOperation(value = "重新发布", notes = "由于发布者自己原因重新发布，退还报名金", httpMethod = "POST")
    @PostMapping(value = "/againReleaseAppointment")
    public Msg againReleaseAppointment(String uid, Integer id, String token) {
        return appointmentService.againReleaseAppointment(uid,id,token);
    }

    /**
     * @Description: 报名人确认赴约
     * @Author: JK
     * @Date: 2019/8/24
     */
    @ApiOperation(value = "报名人确认赴约", notes = "报名人确认赴约", httpMethod = "POST")
    @PostMapping(value = "/confirmAppointment")
    public Msg confirmAppointment(String uid, Integer id, String token) {
        return appointmentService.confirmAppointment(uid,id,token);
    }

    /**
     * @Description: 确认报名人已到达
     * @Author: JK
     * @Date: 2019/8/24
     */
    @ApiOperation(value = "确认报名人已到达", notes = "确认报名人已到达", httpMethod = "POST")
    @PostMapping(value = "/confirmAppointmentArrive")
    public Msg confirmAppointmentArrive(String uid, Integer id, String token) {
        return appointmentService.confirmAppointmentArrive(uid,id,token);
    }

    /**
     * @Description: 查看热门约会
     * @Author: JK
     * @Date: 2019/8/24
     */
    @ApiOperation(value = "查看热门约会", notes = "查看热门约会", httpMethod = "POST")
    @PostMapping(value = "/selectHotAppointment")
    public Msg selectHotAppointment(String uid, String token,double latitude, double longitude) {
        Msg msg = appointmentService.selectHotAppointment(uid, token, latitude, longitude);
        return msg;
    }


    /**
     * @Description: 查询报名约会的全部人员
     * @Author: JK
     * @Date: 2019/8/29
     */
    @ApiOperation(value = "查询报名约会的全部人员", notes = "查询报名约会的全部人员", httpMethod = "POST")
    @PostMapping(value = "/selectAllAppointmentById")
    public Msg selectAllAppointmentById(Integer uid, String token, Integer id) {
        return appointmentService.selectAllAppointmentById(uid, token, id);
    }

    /**
    * @Description: 删除我的发布中约会已完成和已取消的记录
    * @Author: JK
    * @Date: 2019/9/10
    */
    @ApiOperation(value = "删除我的发布中约会已完成和已取消的记录", notes = "删除我的发布中约会已完成和已取消的记录", httpMethod = "POST")
    @PostMapping(value = "/delectMyPublishAppointmentRecord")
    public Msg delectMyPublishAppointmentRecord(Integer uid, String token, Integer id) {
        return appointmentService.delectMyPublishAppointmentRecord(uid, token, id);
    }

    /**
    * @Description: 删除我的报名中约会已完成和已取消的记录
    * @Author: JK
    * @Date: 2019/9/10
    */
    @ApiOperation(value = "删除我的报名中约会已完成和已取消的记录", notes = "删除我的报名中约会已完成和已取消的记录", httpMethod = "POST")
    @PostMapping(value = "/delectMyAskAppointmentRecord")
    public Msg delectMyAskAppointmentRecord(Integer uid, String token, Integer id) {
        return appointmentService.delectMyAskAppointmentRecord(uid, token, id);
    }
}
