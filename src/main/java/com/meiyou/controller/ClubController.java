package com.meiyou.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.meiyou.model.ClubVO;
import com.meiyou.pojo.Club;
import com.meiyou.service.ClubBuyService;
import com.meiyou.service.ClubService;
import com.meiyou.utils.FileUploadUtil;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 推拿会所控制器
 * @author: Mr.Z
 * @create: 2019-08-21 14:34
 **/
@RestController
@Api(value = "推拿会所控制器",tags = "发布推拿会所")
@RequestMapping("/club")
public class ClubController {

    @Autowired
    ClubService clubService;

    @Autowired
    ClubBuyService clubBuyService;

    @PostMapping("/add")
    @ApiOperation(value = "发布推拿会所",notes = "1000-请设置支付密码!,1001-支付密码错误!,1002-发布失败,账户余额不足,505-获取地理位置失败,506-传入的时间类型有错!")
    public Msg addClub(@RequestParam("publish_id") Integer publishId,
                       @RequestParam("token") String token,
                       @RequestParam("project_name") String projectName,
                       @RequestParam("project_desc") String projectDesc,
                       @RequestParam("project_address") String projectAddress,
                       @RequestParam("project_price") Integer projectPrice,
                       @RequestParam("market_price") Integer marketPrice,
                       @RequestParam("timeType") String timeType,
                       @RequestParam("password") String password,
                       @RequestParam("files") MultipartFile[] files,
                       Double longitude, Double latitude, HttpServletRequest request){

        //使用Hutool进行json操作
        JSONArray array = JSONUtil.createArray();
        for (MultipartFile file : files) {
            Msg msg = FileUploadUtil.uploadUtil(file, "club", request);
            if (msg.getCode() == 100) {
                array.add(msg.getExtend().get("path"));
            }
        }
        if (array.size() == 0) {
            return Msg.fail();
        }

        Club club = new Club();
        club.setPublishId(publishId);
        club.setImgsUrl(array.toString());//以json数组的形式存图片
        club.setProjectName(projectName);
        club.setProjectDesc(projectDesc);
        club.setProjectAddress(projectAddress);
        club.setProjectAddress(projectAddress);
        club.setProjectPrice(projectPrice);
        club.setMarketPrice(marketPrice);

        return clubService.addClub(club,token,timeType,password,longitude,latitude);
    }


    @PostMapping("/addClubStar")
    @ApiOperation(value = "添加推拿会所的评星",notes = "添加评星")
    public Msg addClubStar(@RequestParam("uid") Integer uid,
                           @RequestParam("token") String token,
                           @RequestParam("cid") Integer cid,
                           @RequestParam("star") Integer star){
        return clubBuyService.addClubStar(uid,token,cid,star);
    }

    @PostMapping("/update")
    @ApiOperation(value = "取消发布推拿会所",notes = "取消即更发布状态，实际数据不删除")
    public Msg deleteClub(@RequestParam("uid") Integer uid,
                          @RequestParam("token") String token,
                          @RequestParam("cid") Integer cid){
        return clubService.updateClub(uid, token, cid);
    }

    @PostMapping("/updateClubIgnore")
    @ApiOperation(value = "发布者不想看自己发布的会所了",notes = "更改状态为5")
    public Msg updateClubIgnore(@RequestParam("uid") Integer uid,
                          @RequestParam("token") String token,
                          @RequestParam("clubId") Integer clubId){
        return clubService.updateClubIgnore(uid, token, clubId);
    }

    @PostMapping("/updateClubBuy")
    @ApiOperation(value = "发布者不想看了",notes = "更改状态4或者6")
    public Msg deleteClubBuy(@RequestParam("uid") Integer uid,
                          @RequestParam("token") String token,
                          @RequestParam("clubBuyId") Integer clubBuyId){
        return clubService.updateClubBuyDelete(uid, token, clubBuyId);
    }

    @PostMapping("/find")
    @ApiOperation(value = "通过会所id查找对应的会所",notes = "返回为ClubVO类,nums为报名人数")
    public Msg findClubByCid(@RequestParam("uid") Integer uid,
                             @RequestParam("token") String token,
                             @RequestParam("cid") Integer cid){
        return clubService.selectByCid(uid, token, cid);
    }

    @PostMapping("/getByPosition")
    @ApiOperation(value = "查找附近的club",notes = "查找用户所在位置附近的club,返回为ClubVO类")
    public Msg getByPosition(@RequestParam("uid") Integer uid,
                             @RequestParam("token") String token,
                             @RequestParam("longitude") Double longitude,
                             @RequestParam("latitude") Double latitude){
        return clubService.selectClubByPosition(uid,token,longitude,latitude);
    }
}
