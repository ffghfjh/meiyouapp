package com.meiyou.controller;

import com.meiyou.pojo.Club;
import com.meiyou.service.AdminClubManagementService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Mr.Z
 * @create: 2019-08-28 11:10
 **/
@RestController
@Api(value = "管理员按摩会所控制器",tags = "用来查看整个按摩会所情况")
@RequestMapping(value = "/AdminClubManagement")
public class AdminClubManagementController {

    @Autowired
    AdminClubManagementService service;

    @Autowired
    AdminController adminController;

    @PostMapping("/findAllClub")
    @ApiOperation(value = "查找全部推拿会所",notes = "查找")
    public Msg findAllClub(HttpServletRequest request){
        Msg msg = new Msg();
        if (adminController.authAdmin(request)) {
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
        System.out.println("管理员没有登录...");
        msg.setCode(404);
        msg.setMsg("管理员未登录");
        return msg;
    }

    @GetMapping("/findByCid")
    @ApiOperation(value = "查找指定的推拿会所",notes = "查找")
    public Msg findByCid(@RequestParam("cid") Integer cid, HttpServletRequest request){
        Msg msg = new Msg();
        if (adminController.authAdmin(request)) {
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
        System.out.println("管理员没有登录...");
        msg.setCode(404);
        msg.setMsg("管理员未登录");
        return msg;
    }

    /**
     * @Description: 分页查询所有的推拿会所
     * @Author: JK
     * @Date: 2019/8/29
     */
    @ApiOperation(value = "分页查询所有的推拿会所", notes = "分页查询所有的推拿会所", httpMethod = "POST")
    @RequestMapping(value = "/selectAllClubByPage")
    public Map<String,Object> selectAllClubByPage(Integer page, Integer limit
            , Integer publisherId, Integer state, HttpServletRequest request){
        if (adminController.authAdmin(request)) {
            return service.selectAllClubByPage(page,limit,publisherId,state);
        }
        System.out.println("管理员没有登录...");
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("Huangzhaoyang", "管理员不存在");
        return hashMap;
    }

}
