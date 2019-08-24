package com.meiyou.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.meiyou.pojo.Club;
import com.meiyou.service.ClubService;
import com.meiyou.utils.FileUploadUtil;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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

    @PostMapping("/add")
    @ApiOperation(value = "发布推拿会所",notes = "1000-请设置支付密码!,1001-支付密码错误!,1002-发布失败,账户余额不足!")
    public Msg addClub(@RequestParam("publish_id") Integer publishId,
                       @RequestParam("token") String token,
                       @RequestParam("project_name") String projectName,
                       @RequestParam("project_desc") String projectDesc,
                       @RequestParam("project_address") String projectAddress,
                       @RequestParam("project_price") Integer projectPrice,
                       @RequestParam("market_price") Integer marketPrice,
                       @RequestParam("time") Integer time,
                       @RequestParam("password") String password,
                       @RequestParam("files") MultipartFile[] files,
                       Double latitude, Double longitude, HttpServletRequest request){

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

        return clubService.addClub(club,token,time,password,latitude,longitude);
    }

    @PutMapping("/update")
    @ApiOperation(value = "取消发布推拿会所",notes = "取消即更发布状态，实际数据不删除")
    public Msg deleteClub(@RequestParam("uid") Integer uid,
                          @RequestParam("token") String token,
                          @RequestParam("cid") Integer cid){
        return clubService.updateClub(uid, token, cid);
    }

    @GetMapping("/get")
    @ApiOperation(value = "通过用户id查找指定用户id的全部推拿会所",notes = "返回为ShopVO类,nums为报名人数")
    public Msg getClubByUid(@RequestParam("uid") Integer uid,
                            @RequestParam("token") String token){
        return clubService.selectByUid(uid, token);
    }

    @GetMapping("/find")
    @ApiOperation(value = "通过会所id查找对应的会所",notes = "返回为ShopVO类,nums为报名人数")
    public Msg findClubByCid(@RequestParam("uid") Integer uid,
                             @RequestParam("token") String token,
                             @RequestParam("cid") Integer cid){
        return clubService.selectByCid(uid, token, cid);
    }
}
