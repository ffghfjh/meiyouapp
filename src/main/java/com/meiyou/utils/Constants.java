package com.meiyou.utils;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-21 14:55
 **/
public class Constants {
    public static String USER_BAC_DEFAULT = "http://www.meiyou8.cn/meiyou/uimg/imgs/default.jpg";//默认用户资料背景
    public static String USER_BIRTHDAY = "1996-11-12";//默认用户出生年月
    public static String SIGNATURE = "该用户很懒，什么都没有";//用户默认签名
    public static String AGE = "22";//默认用户年龄


    public static String ADDRESS = "http://www.meiyou8.cn/";

    /**
     * 音视频通讯
     */
    public static String VIDEOAPPID = "txz2zhcn";
    public static String VIDEOACCESSKEYID = "LTAIvKcmvWQ80zRM";
    public static String VIDEOACCESSKEYSECRET = "09bBSKCP1j6Aer2fjRo3w5uDBMEmO0";
    public static String GSLB = "https://rgslb.rtc.aliyuncs.com";

    /**
     * 腾讯云
     */
    public static String PRIKEY = "-----BEGIN PRIVATE KEY-----\r\n" +
            "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgUBrb65xkExEVD3gU\r\n" +
            "GEKsZ/Rr7InCiJKHqzkrXYsHMtOhRANCAASPWA7Nuen1mfTPeman3JRN/pdYUiPS\r\n" +
            "O0r0S9+UOtXKwTgUaq2Zc1MTm4xYRX4n4Ntor9tZHd9q0oVsFzYj3DCB\r\n" +
            "-----END PRIVATE KEY-----\r\n";
    public static long SDKAPPID = 1400207520;// 腾讯云SDKAPPID
    public static String IDENTIFIER = "administrator";// 腾讯云IM管理员账号

    public static int TOKEN_EXPIRES_SECOND = 864000; // token过期时间 单位/秒
    public static int REDIS_CODE_OUT_TIME = 1000;//验证码redis过期时间 单位/秒
    public static String GEO_USER_KEY = "user";
    public static String GEO_ACTIVITY = "activity";
    public static String GEO_APPOINTMENT = "appointment";
    public static String GEO_CLUB = "club";
    public static String GEO_SHOP = "shop";
    public static String GEO_TOUR = "tour";
    public static String ACCESSKEYID = "LTAILzv9HnGuCEPg";  //阿里云
    public static String ACCESSKEYSERECT = "9I8BN2KnkebQOS1O89utWPKtfPcRrl"; //阿里云

    /**
     * 支付宝
     */
    public static String PID = "2088531016673451";
    public static String FORMAT = "json";
    public static String SIGN_TYPE = "RSA2";
    public static String ALIURL = "https://openapi.alipay.com/gateway.do";
    public static String APP_ID = "2019041563930019";
    public static String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCVLXtm68/eSgM8\r\n" +
            "jnM7BbtKc3EmX1WnAxuBtYQQGFjEKQ9lK6OsX7izibf3ufSqX6eqDEl6c6Rnmil7\r\n" +
            "c++k7FD09RnIMhj2CTs9jTD4BR5JF9GSWX9ZqMaP+TE1EUq/vX7gCjiuPXaQ4Ryp\r\n" +
            "hZqMILBuITO1PY3SsaGEFO6pURSR/aZr9l+sNAotbUqbw4+CNKqa7y+6CNlUXiR0\r\n" +
            "oM13reAQlpJ5DJ/sspXy/0wqGNVILiSLAUkb465iZepTrWrfsG5gYcFxykT8AIqv\r\n" +
            "4JpX5dt2fyE5m0n1Mcv7YVfmTFXXb6a/iVeoxSu0XrPMbVMcJbq1KXLpr0RQdPCL\r\n" +
            "N/MIWD6nAgMBAAECggEARvfuPxZsO6omRAbY1vlxEog9Ci5Mhzm5OknqW+XZROjr\r\n" +
            "xAuwv+6B5hfch2PV+7+p+gICatjdG9SBa5PKLEGoegColU8QBOx7XFrxMuzsbYWB\r\n" +
            "VAhzx2m9nPzF5iMl1nYGB/uU33+qCl6tzhAbaBKdHKd6+/wenl+XIUj8CVzjVlgF\r\n" +
            "gSbzrxXyC+BucRxn+DgqSRSM2AFgylPwyUgrdMOpOJg+ApTVc7VhBhtH5DaDEHXy\r\n" +
            "+7NZVSxFuqEHCYB7GcZxUkXGT2Ma/RY02oki7UikWICZTJ3jnJR0NBor28JCQbLo\r\n" +
            "GV4iL+Yd1xB6AWc2Yfv9qF6lbx+c0K6ZSxRCTpqXsQKBgQDGOOYotkzzYcFetexn\r\n" +
            "nMYduZY+UK5P947+G9scDNzLvObPeCKaq8QLgNLtnWdJBU7xiSRQqlvlaWnwGLsC\r\n" +
            "8UTYGI81ofzJNaLdYPKha97Pu6RLHkv4FluDJljaYpfIjsVrrBkMw6u1FhMbMQxx\r\n" +
            "5MmNlidkiiQIlhQLRjSM1GsrEwKBgQDAqO9+2uRN8Qsyxzki1Um+rW5fxzPff1rO\r\n" +
            "8JFd9C0bhH8l7ATFe7Lfz5g6XCno0rgFdLsuqgIjAZES9QsIplUH5bnPYMeJJAKF\r\n" +
            "FcxP30Vzq7TFrMPD4idaSLSbncQJnSmEoIh8TtZRDUmy5jgbKLmix9ajb6UlwNIX\r\n" +
            "bKz55MxcnQKBgFoTPxufA8bRVELKnrvStQCK9igpF50FHPiCBgZwHeGDXMtIh0kL\r\n" +
            "5tFnX/JURbwb6my96j+xuoK8bUSKqGjE5IjiWCmCC9IPSowY4wjRcPWrChSMlu8M\r\n" +
            "kdc+FMBgbyLXJgUwtk1jmWJ3voTrvAEBe0mYwxvf3lH8iO06oOSiHnT7AoGAdU2m\r\n" +
            "3McsIOS128n/WN0ilI2GWZyxUdB6GuvQprMIT+vhetZ0pUVGyQzd9BKvlcnPL8s+\r\n" +
            "ORBcUrIegbtfZ3nW5gMSmlijxTV+r0dxaeRYpjAS1hT18SueqFMCXIm3ld7yK4WO\r\n" +
            "9JkP1rPPfrS7zpWCScGBR2uv0wfPI2IRA+oyH+UCgYBF0Wn7oVDoW4GYNG3rVkPK\r\n" +
            "FAHU/B8KX9vvFxlwDPTk/DzIGJuT/c5uC8WnN4R80aLkVr/0H2ePKp7bM8h8LMCx\r\n" +
            "FRn6lEhzx1cMCb8VKT7FjnpXvxiRP7pPeJQSvG7HIMyI58zpjyjNsRNYr9D9JxeC\r\n" +
            "ex1p0mgPGeGx8ZFHJY7C2A==";
    public static String CHARSET = "UTF-8";
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh+kfZKdfiumrW+LNUKY4Qke7iX7uIp7kK6ciiwW6HJEGDcIltzoiVCJfNedgbMcB/GkTHKVPxJEO70kOP/dTX40/2cj4x9Dh96JICXMpKaSo5O/ZtB4SkVSnPe5Nf515TKp5PnXXhqH95sMHYLUk1eJE/3ZWIGQi9V72igYb6cLqq2aEMhR95xEnwyyQn5GugNXI7yozyXHsUxPYuFmIVxhcukSsIYC/60ng3KT6Q5casvwn3a9OWi8RwpkkCXdSzuhaps0+ix+i6n9CLt/3R7lqY0Fp2KDHrDKlfLUcHQ6qVoDrw8XQiayQDqVI5ysyW7lx7QrFlV2hYNV1n1xL8wIDAQAB";




    //RabbitMq
    public static String DELAY_QUEUE_PER_MESSAGE_TTL_NAME = "delay_queue_per_message_ttl";//死信消息缓存队列
    public static String DELAY_QUEUE_PER_QUEUE_TTL_NAME = "delay_queue_per_queue_ttl";//死信队列缓存队列
    public static String DELAY_PROCESS_QUEUE_NAME = "delay_process_queue";//实际消费队列
    public static String DELAY_EXCHANGE_NAME = "dlx";//死信交换机
    public static int QUEUE_EXPIRATION = 1000;//过期时间

    /**
     * 微信开放平台
     */
    public static String APPSECRET = "718e5f07e14afa07e08ee93ac64dacd5";
    public static String WX_APP_ID = "wxa85b1b1ddcddffd5";

    public static String QQ_APP_ID = "101726019";
    public static String QQ_APP_KEY = "77ed8921d867b24c55fe3ee3e316b510";
};