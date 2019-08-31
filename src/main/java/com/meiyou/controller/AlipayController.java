package com.meiyou.controller;

import cn.hutool.core.util.RandomUtil;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.meiyou.service.AlipayService;
import com.meiyou.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
    public Msg alipay(String total_amount,int uId,String token) {
        System.out.println("获取支付订单");
        Msg msg;
        if(RedisUtil.authToken(String.valueOf(uId),token)){
            String out_trade_no = RandomUtil.randomString(11);
            System.out.println("订单号" + out_trade_no);
            AlipayTradeAppPayResponse response = alipayService.getOrderInfo(total_amount, out_trade_no, uId);
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
    @ApiOperation("支付宝提现")
    public Msg aliCash(int uId,String token,float money,String password){
        if(RedisUtil.authToken(String.valueOf(uId),token)){
            return alipayService.aliCash(password,uId,money);
        }else {
            return Msg.noLogin();
        }
    }

    @RequestMapping(value="isBindAlipay",method = RequestMethod.GET)
    @ApiOperation("查询是否绑定支付宝")
    public Msg isBindAlipay(int uId){
        return alipayService.isBindAlipay(uId);
    }

    @PostMapping("addBingAlipay")
    @ApiOperation("绑定支付宝")
    public Msg addBingAlipay(Integer uid,String token,String alipay_account,String alipay_name
            /*,String phone,String code*/){
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }
        return alipayService.addBindAlipay(uid,alipay_account,alipay_name/*,phone,code*/);
    }

    /**
     * 支付结果回调
     * @param request
     */
    @RequestMapping(value="payCallback",method = RequestMethod.POST)
    public void payCallback(HttpServletRequest request){
        Map requestParams = request.getParameterMap();
        alipayService.payCallback(requestParams);
    }

    /**
     * 获取充值比例
     * @return
     */
    @RequestMapping(value = "getChargeRadio",method = RequestMethod.GET)
    @ApiOperation("获取充值比例")
    public Msg getChargeRadio(){
        return alipayService.getChargeRadio();
    }




}