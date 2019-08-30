package com.meiyou.service.impl;

import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.meiyou.pojo.Recharge;
import com.meiyou.service.AlipayService;
import com.meiyou.service.RootMessageService;
import com.meiyou.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-30 19:43
 **/
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    RootMessageService rootMessageService;
    @Override
    public AlipayTradeAppPayResponse getOrderInfo(String total_amount, String subject, String out_trade_no, HttpServletRequest req) {
        Msg msg;
        // TODO Auto-generated method stub

        float ratio = Float.parseFloat(rootMessageService.getMessageByName("charge_ratio"));
        Recharge recharge = new Recharge();

        recharge.setChargeType(1);
        //recharge.setMoney(1);

//        MeiyouChongzhi chongzhi = new MeiyouChongzhi();
//        chongzhi.setcCzcode(out_trade_no);
//        chongzhi.setcUCount(req.getSession().getAttribute("user").toString());
//        Double bili = Double.parseDouble(settingMapper.selChongzhiBili());
//        Double cz = Double.parseDouble(total_amount);
//        chongzhi.setcMeijin((int)(cz+cz*bili));
//        chongzhi.setcTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        chongzhi.setcPaytype("支付宝");
//        chongzhi.setcState("未支付");
//        int zhuangtai = chongzhiMapper.insertSelective(chongzhi);
//        if(zhuangtai>=1) {
//
//            AlipayClient alipayClient = new DefaultAlipayClient(Context.ALIURL, Context.APP_ID,
//                    Context.APP_PRIVATE_KEY, "json", Context.CHARSET, Context.ALIPAY_PUBLIC_KEY, "RSA2"); // 获得初始化的AlipayClient
//            AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();//app支付请求
//            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
//            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//            model.setBody("美游充值");
//            model.setSubject("美游充值");
//            model.setOutTradeNo(out_trade_no);
//            //model.setTimeoutExpress("30m");
//            model.setTotalAmount(total_amount);
//            model.setProductCode("QUICK_MSECURITY_PAY");
//            alipayRequest.setBizModel(model);
//            alipayRequest.setNotifyUrl(Context.fwqaddress + "/payCallback");// 异步通知
//            AlipayTradeAppPayResponse response;
//            try {
//                response = alipayClient.sdkExecute(alipayRequest);
//                return response;
//            } catch (AlipayApiException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                return null;
//            }
//        }else {
//            System.out.println("创建订单失败");
//            return null;
//        }
        return null;
    }
}