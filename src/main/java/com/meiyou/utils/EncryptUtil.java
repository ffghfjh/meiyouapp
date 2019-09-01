package com.meiyou.utils;

import cn.hutool.crypto.digest.*;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/9/1 10:00
 * @description：加密工具
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
public class EncryptUtil {

    /**
     * 对明文进行加密，一共有四层加密规则
     * @param originalStr 要加密的字符串
     * @return
     */
    public String encrypt(String originalStr) {

        //第一层加密：md5
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        originalStr = md5.digestHex(originalStr);

        //第二层加密：拼接SHA256加密后的字符串
        Digester SHA256 = new Digester(DigestAlgorithm.SHA256);
        originalStr += SHA256.digestHex(originalStr);

        //第三层加密:HmacMD5
        byte[] key = "theSecretKeyOfHzy".getBytes();//我的密钥
        HMac mac = new HMac(HmacAlgorithm.HmacMD5, key);
        originalStr = mac.digestHex(originalStr);

        //第四层加密：SM3
        Digester digester = DigestUtil.digester("sm3");
        originalStr = digester.digestHex(originalStr);

        return originalStr;
    }

}
