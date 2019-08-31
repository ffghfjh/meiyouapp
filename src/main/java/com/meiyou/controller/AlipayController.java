package com.meiyou.controller;

import cn.hutool.core.util.RandomUtil;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.meiyou.service.AlipayService;
import com.meiyou.utils.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-30 19:33
 **/
@RestController
public class AlipayController {

    @Autowired
    AlipayService alipayService;
    /**
     * 支付宝支付
     *
     * @return
     */
    @RequestMapping(value = "alipayOrder", method = RequestMethod.GET)
    @ApiOperation(value = "支付宝获取支付订单信息接口")
    public Msg alipay(String total_amount, String subject,int uId,String token) {
        Msg msg;
        if(RedisUtil.authToken(String.valueOf(uId),token)){
            System.out.println("支付参数" + total_amount + "," + subject);
            String out_trade_no = RandomUtil.randomString(11);
            System.out.println("订单号" + out_trade_no);
            AlipayTradeAppPayResponse response = alipayService.getOrderInfo(total_amount, subject, out_trade_no, uId);
            if (response != null) {
                if (response.isSuccess()) {
                    msg = Msg.success();
                    msg.add("alipay", response.getBody());
                    return msg;
                }
            }
            msg = Msg.fail();
            return msg;
        }else {
            return Msg.noLogin();
        }
    }

    @RequestMapping(value = "aliCash",method = RequestMethod.POST)
    public Msg aliCash(int uId,String token,float money,String password){
        if(RedisUtil.authToken(String.valueOf(uId),token)){
            return alipayService.aliCash(password,uId,money);
        }else {
            return Msg.noLogin();
        }

    }
}