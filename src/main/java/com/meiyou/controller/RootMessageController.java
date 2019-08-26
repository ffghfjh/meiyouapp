package com.meiyou.controller;

import com.meiyou.pojo.RootMessage;
import com.meiyou.service.RootMessageService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: meiyou
 * @description: 系统动态数据控制层
 * @author: JK
 * @create: 2019-08-22 19:11
 **/
@Api(value = "RootMessageController", tags = "系统参数控制层")
@RestController
public class RootMessageController {
    @Autowired
    private RootMessageService rootMessageService;

    /**
    * @Description: 获取诚意金
    * @Author: JK
    * @Date: 2019/8/22
    */
    @ApiOperation(value = "获取诚意金", notes = "获取诚意金", httpMethod = "POST")
    @GetMapping(value = "/selectRootMessage")
    public Map<String,Object> selectRootMessage(){
        HashMap<String, Object> map = new HashMap<>();
        List<RootMessage> list = rootMessageService.select();
        RootMessage rootMessage = list.get(0);
        if (rootMessage != null) {
            String sincerityMoney = rootMessage.getValue();
            map.put("sincerityMoney",sincerityMoney);
        }
        return map;
    }

    @GetMapping("/getRootMessage")
    @ApiOperation(value = "获取系统数据",notes = "诚意金-sincerity_money、范围半径-range、置顶金-top_money、视频通话金-video_money、报名金-ask_money、发布金-publish_money、分享金额-share_money")
    public Msg getRootMessageByName(String name){

        Msg msg = new Msg();
        String result = rootMessageService.getMessageByName(name);
        if( null == result){
            msg.setCode(404);
            msg.setMsg("找不到对应的值，请查清后再操作");
            return msg;
        }
        msg.add("value",result);
        msg.setCode(100);
        msg.setMsg("成功");
        return msg;
    }


    @ApiOperation(value = "添加系统参数", notes = "name：系统参数名, value:系统参数值", httpMethod = "POST")
    @PostMapping(value = "/saveMessage")
    public Msg saveMessage(String name, String value) {
        return rootMessageService.saveMessage(name, value);
    }

    @ApiOperation(value = "删除系统参数", notes = "mid: 参数主键", httpMethod = "POST")
    @PostMapping(value = "/removeMessage")
    public Msg removeMessage(int mid) {
        return rootMessageService.removeMessage(mid);
    }

    @ApiOperation(value = "根据主键修改参数或参数值", notes = "mid: 参数主键，name：系统参数名, value:系统参数值", httpMethod = "POST")
    @PostMapping(value = "/updateMessageById")
    public Msg updateMessageById(int mid, String name, String value){
        return rootMessageService.updateMessageById(mid, name, value);
    }

    @ApiOperation(value = "根据参数名修改参数值", notes = "name：系统参数名, value:系统参数值", httpMethod = "POST")
    @PostMapping(value = "/updateMessageByName")
    public Msg updateMessageByName(String name, String value){
        return rootMessageService.updateMessageByName(name, value);
    }

    @ApiOperation(value = "根据参数名获取参数值", notes = "name：系统参数名, value:系统参数值", httpMethod = "POST")
    @PostMapping(value = "/getMessageByName")
    public String getMessageByName(String name) {
        return rootMessageService.getMessageByName(name);
    }

    @ApiOperation(value = "拉取所有系统参数", notes = "无参数，直接try out!", httpMethod = "POST")
    @PostMapping(value = "/listMessage")
    public Msg listMessage() {
        return rootMessageService.listMessage();
    }

}
