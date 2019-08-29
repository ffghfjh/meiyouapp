package com.meiyou.controller;

import com.meiyou.utils.Msg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/29 13:58
 * @description：管理员动态控制层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@RequestMapping("/AdminActivity")
@RestController
public class AdminActivityController {

    @RequestMapping("/listActivity")
    public Msg listActivityReport() {
        return null;
    }

}
