package com.meiyou.controller;

import com.meiyou.pojo.RootMessage;
import com.meiyou.service.RootMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(value = "系统动态数据控制层", tags = {"系统动态数据控制层"})
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
    @PostMapping(value = "/selectRootMessage")
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
}
