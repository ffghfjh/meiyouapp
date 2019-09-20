package com.meiyou.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.meiyou.mapper.CashMapper;
import com.meiyou.mapper.RechargeMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.pojo.*;
import com.meiyou.service.AlipayService;
import com.meiyou.service.RootMessageService;
import com.meiyou.service.UserService;
import com.meiyou.utils.Constants;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-30 19:43
 **/
@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    RootMessageService rootMessageService;
    @Autowired
    RechargeMapper rechargeMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CashMapper cashMapper;
    @Autowired
    UserService userService;

    AlipayClient alipayClient = new DefaultAlipayClient(Constants.ALIURL, Constants.APP_ID,
            Constants.APP_PRIVATE_KEY, Constants.FORMAT, Constants.CHARSET, Constants.ALIPAY_PUBLIC_KEY,
            Constants.SIGN_TYPE); // 调用接口之前的初始化
    @Override
    public AlipayTradeAppPayResponse getOrderInfo(String total_amount, String out_trade_no,int uId) {
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

    @Override
    @Transactional
    public Msg aliCash(String password, int uId, float money) {
        Msg msg;
        User user = userMapper.selectByPrimaryKey(uId);
        if(user.getBindAlipay()){
            if(password.equals(user.getPayWord())){
                if(user.getMoney()>=money){
                    user.setMoney(user.getMoney()-money);//扣钱
                    if(userMapper.updateByPrimaryKey(user)==1){
                        float cashRadio = Float.parseFloat(rootMessageService.getMessageByName("cash"));
                        float moneyEnd = money*(1-cashRadio);//真实到账的钱
                        String cashNumber = RandomUtil.randomString(11);
                        Cash cash = new Cash();
                        cash.setCashMoney((int)Math.ceil(moneyEnd));
                        cash.setCashType(1);
                        if(money<10000){
                            cash.setState(1);
                        }else {
                            cash.setState(0);

                        }
                        Date date = new Date();
                        cash.setCreateTime(date);
                        cash.setUpdateTime(date);
                        cash.setCashId(uId);
                        cash.setCashNumber(cashNumber);

                        if(cashMapper.insertSelective(cash)==1){
                            if(money<10000){
                                AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
                                request.setBizContent("{" + "\"out_biz_no\":\"" + cashNumber + "\","
                                        + "\"payee_type\":\"ALIPAY_LOGONID\"," + "\"payee_account\":\"" + user.getAlipayAccount() + "\","
                                        + "\"amount\":\"" + moneyEnd + "\"," + "\"payer_show_name\":\"美游提现\","
                                        + "\"payee_real_name\":\"" + user.getAlipayName() + "\"," + "\"remark\":\"提现\"" + "  }");

                                AlipayFundTransToaccountTransferResponse response = null;
                                try {
                                    response = alipayClient.execute(request);
                                    if (response.isSuccess()) {
                                        msg = Msg.success();
                                        return msg;
                                    } else {
                                        System.out.println("调用失败");
                                        msg = Msg.fail();
                                        return msg;
                                    }
                                } catch (AlipayApiException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                return Msg.success();
                            }
                        }
                    }
                }else {
                    msg = Msg.fail();
                    msg.setCode(1001);
                    msg.setMsg("余额不足");
                    return msg;
                }
            }
            else {
                msg = new Msg();
                msg.setCode(1000);
                msg.setMsg("支付密码错误");
                return msg;
            }
        }else{
            msg = Msg.fail();
            msg.setCode(1002);
            msg.setMsg("未绑定支付宝");
            return msg;
        }
       return Msg.fail();
    }

    @Override
    public Msg isBindAlipay(int uId) {
        User user = userMapper.selectByPrimaryKey(uId);
        if(user.getBindAlipay()){
            Msg msg = Msg.success();
            msg.add("bind",true);
            return msg;
        }else {
            Msg msg = Msg.success();
            msg.add("bind",false);
            return msg;
        }
    }

    @Override
    public Msg addBindAlipay(Integer uid, String alipayAccount, String alipayName/*,String phone, String code*/) {
        Msg msg = new Msg();

//        if(!RedisUtil.authCode(phone, code)){
//            msg.setCode(500);
//            msg.setMsg("验证码错误");
//            return msg;
//        }

        User user = new User();
        user.setAlipayAccount(alipayAccount);
        user.setAlipayName(alipayName);
        user.setId(uid);
        user.setBindAlipay(true);
        user.setUpdateTime(new Date());
        int rows = userMapper.updateByPrimaryKeySelective(user);
        if(rows == 0){
            return Msg.fail();
        }

        msg.setMsg("绑定成功");
        msg.setCode(100);
        return msg;
    }

    @Override
    public void payCallback(Map<String,String[]> requestParams) {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
//            try {
//                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            params.put(name, valueStr);
        }
            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean flag = false;
        try {
            flag = AlipaySignature.rsaCheckV1(params, Constants.ALIPAY_PUBLIC_KEY, Constants.CHARSET, "RSA2");
         } catch (AlipayApiException e) {
            e.printStackTrace();
        }
           if (flag) {
                    // 商户订单号
                    String out_trade_no = params.get("out_trade_no");
                    // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
                    RechargeExample example = new RechargeExample();
                    RechargeExample.Criteria criteria = example.createCriteria();
                    criteria.andOrderNumberEqualTo(out_trade_no);
                    Recharge recharge = rechargeMapper.selectByExample(example).get(0);
                    if (recharge.getState() == 0) {
                        float money = recharge.getMoney();
                        if (userService.addMoney(recharge.getPersonId(), recharge.getMoney())) {//加钱
                            recharge.setState(1);
                            recharge.setUpdateTime(new Date());
                            rechargeMapper.updateByPrimaryKey(recharge);
                        }
                    }
                    System.out.println("********************** 支付成功(支付宝异步通知) **********************");
                } else {
                    System.out.println("验证失败");
                }

        }


    @Override
    public Msg getChargeRadio() {
        String radio = rootMessageService.getMessageByName("charge_ratio");
        return Msg.success().add("ratio",radio);
    }

}