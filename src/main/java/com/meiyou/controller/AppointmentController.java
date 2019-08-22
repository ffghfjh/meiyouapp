package com.meiyou.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.meiyou.pojo.AppointAsk;
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
import java.text.SimpleDateFormat;
import java.util.*;

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

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd/");

    /**
    * @Description: 发布约会
    * @Author: JK
    * @Date: 2019/8/22
    */
    @ApiOperation(value = "发布约会", notes = "发布约会", httpMethod = "POST")
    @PostMapping(value = "/insertAppointment")
    public Msg insertAppointment(@RequestParam(value = "publisherId",required = false)Integer publisherId,
                                 @RequestParam(value = "appointAddress",required = false)String appointAddress,
                                 @RequestParam(value = "appointTime",required = false)String appointTime,
                                 @RequestParam(value = "appointContext",required = false)String appointContext,
                                 @RequestParam(value = "payType",required = false)int payType,
                                 @RequestParam(value = "reward",required = false)Integer reward,
                                 MultipartFile[] files,
                                 @RequestParam(value = "password",required = false)String password,
                                 @RequestParam(value = "token",required = false)String token,
                                 HttpServletRequest request
                                 ){

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
        appointment.setAppointImgs(array.toString());


        return appointmentService.insert(appointment,password,token);
    }

    /**
    * @Description: 查询所有我发布的约会
    * @Author: JK
    * @Date: 2019/8/22
    */
    @ApiOperation(value = "查询所有我发布的约会", notes = "查询所有我发布的约会", httpMethod = "POST")
    @PostMapping(value = "/selectAppointmentList")
    public Map<String, Object> selectAppointmentList(){
        List<Appointment> appointments = appointmentService.selectAppointmentList();
        Map<String, Object> map = new HashMap<>();
        if (appointments != null) {
            ArrayList<Appointment> lists = new ArrayList<>();
            for (Appointment appointment : appointments) {
                lists.add(appointment);
            }
            map.put("lists",lists);
            Msg success = Msg.success();
            map.put("success",success);
        }


        return map;
    }

    /**
    * @Description: 取消发布约会订单
    * @Author: JK
    * @Date: 2019/8/22
    */
    @ApiOperation(value = "取消发布约会订单", notes = "取消发布约会订单", httpMethod = "POST")
    @PostMapping(value = "/deletePublish")
    public Msg deletePublish(Integer uid,Integer id){
        int i = appointmentService.deletePublish(uid, id);
        if (i == 1){
            return Msg.success();
        }else {
            return Msg.fail();
        }
    }

    /**
     * @Description: 从多个约会订单中选择一个进行报名
     * @Author: JK
     * @Date: 2019/8/22
     */
    @ApiOperation(value = "从多个约会订单中选择一个进行报名", notes = "从多个约会订单中选择一个进行报名", httpMethod = "POST")
    @PostMapping(value = "/startEnrollment")
    public Msg startEnrollment(Integer uid,Integer id){
        int i = appointmentService.startEnrollment(uid,id);
        if (i == 1){
            return Msg.success();
        }else {
            return Msg.fail();
        }
    }

    /**
    * @Description: 查询所有报名某个约会的人员信息，并选择一个进行确认
    * @Author: JK
    * @Date: 2019/8/22
    */
    @ApiOperation(value = "查询所有报名某个约会的人员信息，并选择一个进行确认", notes = "查询所有报名某个约会的人员信息，并选择一个进行确认", httpMethod = "POST")
    @PostMapping(value = "/selectAppointAskList")
    public Map<String,Object> selectAppointAskList(Integer appointId){
        Map<String, Object> map = new HashMap<>();
        List<AppointAsk> appointAsks = appointmentService.selectAppointAskList(appointId);
        if (appointAsks != null) {
            map.put("appointAsks",appointAsks);
            Msg success = Msg.success();
            map.put("success",success);
        }
        return map;
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
        if (i == 1){
            return Msg.success();
        }else {
            return Msg.fail();
        }
    }
}
