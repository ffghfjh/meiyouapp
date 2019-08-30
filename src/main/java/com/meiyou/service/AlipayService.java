package com.meiyou.service;

import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.meiyou.utils.Msg;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-30 19:42
 **/
public interface AlipayService {
    public AlipayTradeAppPayResponse getOrderInfo(String total_amount, String subject, String out_trade_no, int uId);//获取支付订单信息

    public Msg aliCash(String password, int uId, float money);
}