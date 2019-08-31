package com.meiyou.service;

import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.meiyou.utils.Msg;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-30 19:42
 **/
public interface AlipayService {
    /**
     * 获取订单号
     * @param total_amount
     * @param out_trade_no
     * @param uId
     * @return
     */
    public AlipayTradeAppPayResponse getOrderInfo(String total_amount, String out_trade_no, int uId);//获取支付订单信息

    /**
     * 提现
     * @param password
     * @param uId
     * @param money
     * @return
     */
    public Msg aliCash(String password, int uId, float money);


    /**
     * 查询是否绑定支付宝
     * @param uId
     * @return
     */
    public Msg isBindAlipay(int uId);

    public Msg addBindAlipay(Integer uid,String alipayAccount,String alipayName/*,String phone,String code*/);

    /**
     * 支付结果回调
     */
    public void payCallback(Map requestParams);

    /**
     * 获取充值比例
     * @return
     */
    public Msg getChargeRadio();
}