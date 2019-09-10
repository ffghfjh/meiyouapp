package com.meiyou.model;

import java.io.Serializable;

/**
 * @description: 报名者的VO类
 * @author: Mr.Z
 * @create: 2019-08-29 19:38
 **/
public class AskerVO implements Serializable {
    private Integer id;
    private String nickname;
    private String header;
    private Boolean sex;
    private String birthday;
    private String signature;
    private Integer askState;
    private Integer askId;

    @Override
    public String toString() {
        return "AskerVO{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", header='" + header + '\'' +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                ", signature='" + signature + '\'' +
                ", askState=" + askState +
                ", askId=" + askId +
                '}';
    }

    public Integer getAskId() {
        return askId;
    }

    public void setAskId(Integer askId) {
        this.askId = askId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getAskState() {
        return askState;
    }

    public void setAskState(Integer askState) {
        this.askState = askState;
    }
}
