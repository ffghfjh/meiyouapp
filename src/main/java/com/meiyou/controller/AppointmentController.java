package com.meiyou.controller;

import com.meiyou.pojo.Appointment;
import com.meiyou.pojo.RootMessage;
import com.meiyou.service.AppointmentService;
import com.meiyou.service.RootMessageService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    private RootMessageService rootMessageService;

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd/");

    /**
    * @Description: 发布约会
    * @Author: JK
    * @Date: 2019/8/22
    */
    @ApiOperation(value = "发布约会", notes = "发布约会", httpMethod = "POST")
    @PostMapping(value = "/insertAppointment")
    public Msg insertAppointment(
                                 @RequestParam(value = "appointAddress",required = false)String appointAddress,
                                 @RequestParam(value = "appointTime",required = false)String appointTime,
                                 @RequestParam(value = "appointContext",required = false)String appointContext,
                                 @RequestParam(value = "needNumber",required = false)int needNumber,
                                 @RequestParam(value = "payType",required = false)int payType,
                                 @RequestParam(value = "appointImgs",required = false)String appointImgs,
                                 MultipartFile[] uploadFile,
                                 HttpServletRequest request
                                 ){
        //获取真实路径
        String realPath=request.getSession().getServletContext().getRealPath("/uploadFile/");
        //设置日期格式
        String format=sdf.format(new Date());
        //新建一个文件，文件名是真实路径加日期
        File folder=new File(realPath+format);
        //是否目录
        if(!folder.isDirectory()){
            folder.mkdirs();
        }
        HashMap<String,String> map=new HashMap<String,String>();
        String filePath="";
        for(MultipartFile multipartFile:uploadFile){
            String oldName=multipartFile.getOriginalFilename();
            String newName= UUID.randomUUID().toString()+oldName.substring(oldName.lastIndexOf("."),oldName.length());
            try {
                //用于图片上传时，把内存中图片写入磁盘
                multipartFile.transferTo(new File(folder,newName));
                //文件上传路径
                filePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/uploadFile/"+format+newName;
                map.put(newName, filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //如果选择线下付款
        if (payType == 1){
            List<RootMessage> list = rootMessageService.select();
            //通过诚意金名称拿到对应的金额
            RootMessage rootMessage = list.get(0);
            String sincerityMoney = rootMessage.getValue();
            System.out.println(sincerityMoney);
            request.setAttribute("sincerityMoney",sincerityMoney);
        }

        Appointment appointment = new Appointment();
        appointment.setCreateTime(new Date());
        appointment.setUpdateTime(new Date());
        appointment.setAppointAddress(appointAddress);
        appointment.setAppointTime(appointTime);
        appointment.setAppointContext(appointContext);
        appointment.setNeedNumber(needNumber);
        appointment.setPayType(payType);
        appointment.setAppointImgs(filePath);
        int insert = appointmentService.insert(appointment);
        if (insert == 1){
            return Msg.success();
        }else {
            return Msg.fail();
        }



    }

    /**
    * @Description: 取消发布约会订单
    * @Author: JK
    * @Date: 2019/8/22
    */
    public Msg deletePublish(Integer uid,Integer id){
        int i = appointmentService.deletePublish(uid, id);
        if (i == 1){
            return Msg.success();
        }else {
            return Msg.fail();
        }
    }
}
