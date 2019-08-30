package com.meiyou.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.meiyou.mapper.RechargeMapper;
import com.meiyou.pojo.Recharge;
import com.meiyou.service.AlipayService;
import com.meiyou.service.RootMessageService;
import com.meiyou.utils.Constants;
import com.meiyou.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-30 19:43
 **/
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    RootMessageService rootMessageService;
    @Autowired
    RechargeMapper rechargeMapper;
    @Override
    public AlipayTradeAppPayResponse getOrderInfo(String total_amount, String subject, String out_trade_no,int uId) {
        Msg msg;
        // TODO Auto-generated method stub
        float ratio = Float.parseFloat(rootMessageService.getMessageByName("charge_ratio"));
        Recharge recharge = new Recharge();

        recharge.setChargeType(1);
        int money = Integer.parseInt(total_amount);
        recharge.setMoney((money*(1+ratio)));
        recharge.setOrderNumber(out_trade_no);
        recharge.setPersonId(uId);
        recharge.setState(0);
        Date date = new Date();
        recharge.setCreateTime(date);
        recharge.setUpdateTime(date);
        recharge.setChargeType(1);
        if(rechargeMapper.insertSelective(recharge)==1){
             AlipayClient alipayClient = new DefaultAlipayClient(Constants.ALIURL, Constants.APP_ID, Constants.APP_PRIVATE_KEY, "json", Constants.CHARSET, Constants.ALIPAY_PUBLIC_KEY, "RSA2"); // 获得初始化的AlipayClient
            AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();//app支付请求
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody("美游充值");
            model.setSubject("美游充值");
            model.setOutTradeNo(out_trade_no);
            //model.setTimeoutExpress("30m");
            model.setTotalAmount(total_amount);
            model.setProductCode("QUICK_MSECURITY_PAY");
            alipayRequest.setBizModel(model);
            alipayRequest.setNotifyUrl(Constants.ADDRESS + "/payCallback");// 异步通知
            AlipayTradeAppPayResponse response;
            try {
                response = alipayClient.sdkExecute(alipayRequest);
                return response;
            } catch (AlipayApiException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        }else {
            System.out.println("创建订单失败");
            return null;
        }

    }
}