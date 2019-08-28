package com.meiyou.controller;

import com.meiyou.pojo.Club;
import com.meiyou.service.AdminClubManagementService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: Mr.Z
 * @create: 2019-08-28 11:10
 **/
@RestController
@Api(value = "管理员按摩会所控制器",tags = "用来查看整个按摩会所情况")
@RequestMapping("/admin")
public class AdminClubManagementController {

    @Autowired
    AdminClubManagementService service;

    @PostMapping("/findAllClub")
    @ApiOperation(value = "查找全部推拿会所",notes = "查找")
    public Msg findAllClub(){
        Msg msg = new Msg();
        List<Club> clubs = service.selectAll();
        if(clubs == null && clubs.size() == 0){
            msg.setCode(404);
            msg.setMsg("没找到");
            return msg;
        }
        msg.setCode(100);
        msg.setMsg("已找到");
        msg.add("clubs",clubs);
        return msg;
    }

    @GetMapping("/findByCid")
    @ApiOperation(value = "查找指定的推拿会所",notes = "查找")
    public Msg findByCid(@RequestParam("cid") Integer cid){
        System.out.println("cid:"+cid);
        Msg msg = new Msg();
        Club club = service.selectByCid(cid);
        if(club == null){
            msg.setMsg("没有找到");
            msg.setCode(404);
            return msg;
        }
        System.out.println("club2:"+club);
        msg.setCode(100);
        msg.setMsg("成功");
        msg.add("club",club);
        return msg;
    }
}
