package com.meiyou.controller;

import com.meiyou.pojo.User;
import com.meiyou.service.AdminService;
import com.meiyou.service.RootMessageService;
import com.meiyou.service.UserService;
import com.meiyou.utils.LayuiDataUtil;
import com.meiyou.utils.Msg;
import com.meiyou.utils.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-26 21:16
 **/
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    RootMessageService rootMessageService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "adminLogin", method = RequestMethod.POST)
    @ApiOperation("后台登录")
    @ResponseBody
    public Msg adminLogin(String adminAccount, String adminPassword, HttpServletRequest request) {
        Msg msg = adminService.adminLogin(adminAccount, adminPassword);
        if (msg.getCode() == 100) {
            request.getSession().setAttribute("admin", adminAccount);
        }
        return msg;
    }

    @RequestMapping(value = "authAdmin", method = RequestMethod.GET)
    @ApiOperation("登录校验")
    @ResponseBody
    public boolean authAdmin(HttpServletRequest request) {
        if (request.getSession().getAttribute("admin") != null) {
            return true;
        }
        return false;
    }


    @RequestMapping(value = "adminLoginOut", method = RequestMethod.GET)
    @ApiOperation("后台退出登录")
    public String adminLoginOut(HttpServletRequest request) {
            request.getSession().removeAttribute("admin");
        return "redirect:/admin/login.html";
    }

    @RequestMapping(value = "updSincerity_money", method = RequestMethod.GET)
    @ApiOperation("诚意金设置")
    @ResponseBody
    public boolean updSincerity_money(String money, HttpServletRequest req) {
        System.out.println("诚意金设置");
        if (authAdmin(req)) {
            if (rootMessageService.updateMessageByName("sincerity_money", money).getCode() == 100) {
                System.out.println("诚意金设置成功");
                return true;
            } else {
                System.out.println("诚意金设置失败");
                return false;
            }
        }
        return false;
    }

    @RequestMapping(value = "updRange", method = RequestMethod.GET)
    @ApiOperation("范围设置")
    @ResponseBody
    public boolean updRange(String money, HttpServletRequest req) {
        System.out.println("范围设置：参数：" + money);
        if (!authAdmin(req)) {
            System.out.println("管理员没有登录...");
            return false;
        }
        if (rootMessageService.updateMessageByName("range", money).getCode() == 100) {
            return true;
        }
        return false;
    }

    @RequestMapping(value = "updVideo_money", method = RequestMethod.GET)
    @ApiOperation("视频通话费")
    @ResponseBody
    public boolean updVideo_money(String money, HttpServletRequest req) {
        if (!authAdmin(req)) {
            System.out.println("管理员没有登录...");
            return false;
        }
        if (rootMessageService.updateMessageByName("video_money", money).getCode() == 100) {
            return true;
        }
        return false;
    }


    @RequestMapping(value = "updCash", method = RequestMethod.GET)
    @ApiOperation("提现费率")
    @ResponseBody
    public boolean updCash(String money, HttpServletRequest req) {
        if (!authAdmin(req)) {
            System.out.println("管理员没有登录...");
            return false;
        }
        if (rootMessageService.updateMessageByName("cash", money).getCode() == 100) {
            return true;
        }
        return false;
    }


    @RequestMapping(value = "updPublish_money", method = RequestMethod.GET)
    @ApiOperation("发布金设置")
    @ResponseBody
    public boolean updPublish_money(String money, HttpServletRequest req) {
        if (!authAdmin(req)) {
            System.out.println("管理员没有登录...");
            return false;
        }
        if (rootMessageService.updateMessageByName("publish_money", money).getCode() == 100) {
            return true;
        }
        return false;
    }


    @RequestMapping(value = "updShare_money", method = RequestMethod.GET)
    @ApiOperation("分享金设置")
    @ResponseBody
    public boolean updShare_money(String money, HttpServletRequest req) {
        if (!authAdmin(req)) {
            System.out.println("管理员没有登录...");
            return false;
        }
        if (rootMessageService.updateMessageByName("share_money", money).getCode() == 100) {
            return true;
        }
        return false;
    }


    @RequestMapping(value = "updOnday", method = RequestMethod.GET)
    @ApiOperation("一天置顶费设置")
    @ResponseBody
    public boolean updOnday(String money, HttpServletRequest req) {
        if (!authAdmin(req)) {
            System.out.println("管理员没有登录...");
            return false;
        }
        if (rootMessageService.updateMessageByName("一天", money).getCode() == 100) {
            return true;
        }
        return false;
    }


    @RequestMapping(value = "updWeek", method = RequestMethod.GET)
    @ApiOperation("一周置顶费")
    @ResponseBody
    public boolean updWeek(String money, HttpServletRequest req) {
        if (!authAdmin(req)) {
            System.out.println("管理员没有登录...");
            return false;
        }
        if (rootMessageService.updateMessageByName("一周", money).getCode() == 100) {
            return true;
        }
        return false;
    }


    @RequestMapping(value = "updMonth", method = RequestMethod.GET)
    @ApiOperation("一月置顶费")
    @ResponseBody
    public boolean updMonth(String money, HttpServletRequest req) {
        if (!authAdmin(req)) {
            System.out.println("管理员没有登录...");
            return false;
        }
        if (rootMessageService.updateMessageByName("一月", money).getCode() == 100) {
            return true;
        }
        return false;
    }


    @RequestMapping(value = "updSeasons", method = RequestMethod.GET)
    @ApiOperation("一月置顶费")
    @ResponseBody
    public boolean updSeasons(String money, HttpServletRequest req) {
        if (!authAdmin(req)) {
            return false;
        }
        if (rootMessageService.updateMessageByName("一季", money).getCode() == 100) {
            return true;
        }
        return false;
    }


    @RequestMapping(value = "updOnyear", method = RequestMethod.GET)
    @ApiOperation("一年置顶费")
    @ResponseBody
    public boolean updOnyear(String money, HttpServletRequest req) {
        if (!authAdmin(req)) {
            return false;
        }
        if (rootMessageService.updateMessageByName("一年", money).getCode() == 100) {
            return true;
        }
        return false;
    }


    @RequestMapping(value = "getAllValue", method = RequestMethod.GET)
    @ApiOperation("获取所有后台数据")
    @ResponseBody
    public Msg getAllValue(HttpServletRequest request) {
        if (authAdmin(request)) {
            return rootMessageService.listMessage();
        }
        return Msg.noLogin();

    }

    @RequestMapping(value="getUserInfo",method = RequestMethod.GET)
    @ApiOperation("分页用户数据")
    @ResponseBody
    public Map<String,Object> getUserInfo(int page,int limit, HttpServletRequest request){
        if (authAdmin(request)) {
            Page<Map<String,Object>> pages = new Page<Map<String, Object>>();
            int count = userService.selAllUser().size();
            pages.setCount(count);
            pages.setList(userService.selUserInfoByPage(page,limit));
            return LayuiDataUtil.getLayuiData(pages);
        }
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("huang zhaoyang", "美游app");
        return hashMap;
    }


    @RequestMapping(value = "getUserInfoByAccount",method = RequestMethod.GET)
    @ApiOperation(("账号获取用户信息"))
    @ResponseBody
    public Msg getUserInfoByAccount(String account,HttpServletRequest req){
        if(authAdmin(req)){
            Msg msg = Msg.success();
            User user = userService.selUserInfoByAdmin(account);
            if(user!=null){
                msg.add("user",user);
                return msg;
            }else{
                msg.setCode(1000);
                return msg;
            }
        }
        else{
            return Msg.noLogin();
        }
    }

    @RequestMapping(value="cashAudit",method = RequestMethod.POST)
    @ApiOperation(value = "提现审核",tags = "0 不通过 1 通过")
    @ResponseBody
    public Msg cashAudit(HttpServletRequest req,int cashId,int result){
        if(authAdmin(req)){
            return userService.cashAudit(cashId,result);
        }else{
            return Msg.noLogin();
        }
    }


    @RequestMapping(value = "hideUserById",method = RequestMethod.GET)
    @ApiOperation(value = "通过id对用户进行封号",notes = "uid为用户id, type为1时封号，为0时取消封号")
    @ResponseBody
    public Msg hideUserById(String uid, String type, HttpServletRequest request) {
        if (authAdmin(request)) {
            return userService.hideUserById(uid, type);
        }
        return Msg.noLogin();
    }

}