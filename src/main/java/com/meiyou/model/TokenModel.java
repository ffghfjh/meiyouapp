package com.meiyou.model;

import java.io.Serializable;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-21 14:50
 **/
public class TokenModel implements Serializable {
    //用户id
    private long userId;

    //随机生成的uuid
    private String token;

    public TokenModel(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}