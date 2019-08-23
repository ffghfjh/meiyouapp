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
import java.util.Map;

/**
 * @program: meiyouapp
 * @description: 约会控制层
 * @author: JK
 * @create: 2019-08-21 13:59
 **/
@Api(value = "发布约会控制层", tags = {"发布约会控制层"})
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
                                 HttpServletRequest request
    ) {

        //使用Hutool进行json操作
        JSONArray array = JSONUtil.createArray();
        for (MultipartFile file : files) {
            Msg msg = FileUploadUtil.uploadUtil(file, "activity", request);
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


        return appointmentService.insert(appointment, password, token);
    }

    /**
     * @Description: 查询所有我发布的约会
     * @Author: JK
     * @Date: 2019/8/22
     */
    @ApiOperation(value = "查询所有我发布的约会", notes = "查询所有我发布的约会", httpMethod = "POST")
    @PostMapping(value = "/selectAppointmentList")
    public Map<String, Object> selectAppointmentList(String uid,String token) {
        Msg msg = appointmentService.selectAppointmentList(uid, token);
        Map<String, Object> extend = msg.getExtend();
        return extend;
    }

    /**
     * @Description: 取消发布约会订单
     * @Author: JK
     * @Date: 2019/8/22
     */
    @ApiOperation(value = "取消发布约会订单", notes = "取消发布约会订单", httpMethod = "POST")
    @PostMapping(value = "/deletePublish")
    public Msg deletePublish(@RequestParam(value = "id", required = false) Integer id,
                             @RequestParam(value = "token", required = false) String token) {
        return appointmentService.deletePublish(id, token);

    }

    /**
     * @Description: 查询所有报名某个约会的人员信息
     * @Author: JK
     * @Date: 2019/8/22
     */
    @ApiOperation(value = "查询所有报名某个约会的人员信息", notes = "查询所有报名某个约会的人员信息", httpMethod = "POST")
    @PostMapping(value = "/selectAppointAskList")
    public Map<String, Object> selectAppointAskList(Integer appointId) {
        Msg msg = appointmentService.selectAppointAskList(appointId);
        Map<String, Object> extend = msg.getExtend();
        return extend;
    }

    /**
     * @Description: 从多个约会订单中选择一个进行报名
     * @Author: JK
     * @Date: 2019/8/22
     */
    @ApiOperation(value = "从多个约会订单中选择一个进行报名", notes = "从多个约会订单中选择一个进行报名", httpMethod = "POST")
    @PostMapping(value = "/startEnrollment")
    public Msg startEnrollment(@RequestParam(value = "uid", required = false) String uid,
                               @RequestParam(value = "id", required = false) Integer id,
                               @RequestParam(value = "password", required = false) String password,
                               @RequestParam(value = "token", required = false) String token) {
        return appointmentService.startEnrollment(uid, password, id, token);
    }



    /**
     * @Description: 从所有报名某个约会的人员信息中选择一个进行确认
     * @Author: JK
     * @Date: 2019/8/22
     */
    @ApiOperation(value = "从所有报名某个约会的人员信息中选择一个进行确认", notes = "从所有报名某个约会的人员信息中选择一个进行确认", httpMethod = "POST")
    @PostMapping(value = "/confirmUserId")
    public Msg confirmUserId(Integer askerId, Integer appointId) {
        int i = appointmentService.confirmUserId(askerId, appointId);
        if (i == 1) {
            return Msg.success();
        } else {
            return Msg.fail();
        }
    }
}
