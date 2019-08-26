package com.meiyou.controller;

import com.meiyou.service.ActivityReadService;
import com.meiyou.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/24 11:58
 * @description：动态阅读量实现层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Api(value = "ReadController", tags ={"动态阅读控制层"})
@RequestMapping(value = "/Read")
@RestController
public class ActivityReadController {

    @Autowired
    ActivityReadService readService;

    @ApiOperation(value = "添加阅读量", notes = "uid为我自己的id，不是动态主人的id；aid为动态的id", httpMethod = "POST")
    @PostMapping(value = "/addReadNum")
    public Msg addReadNum(int uid, int aid) {
        return readService.addReadNum(uid, aid);
    }

    @ApiOperation(value = "谁看过我", notes = "uid为我自己的id", httpMethod = "POST")
    @PostMapping(value = "/whoHasSeenMe")
    public Msg whoHasSeenMe(int uid) {
        return readService.whoHasSeenMe(uid);
    }

}
