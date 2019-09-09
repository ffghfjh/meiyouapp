package com.meiyou.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @program: meiyouapp
 * @description:
 * @author: JK
 * @create: 2019-09-09 15:31
 **/
public class AppointmentVo1 {
    private Integer uid;
    private String nickname;
    private String header;
    private Boolean sex;
    private String birthday;
    private Integer aid;
    private Integer publisherId;
    private String appointAddress;
    private String appointTime;
    private String appointContext;
    private Integer needNumber;
    private Integer payType;
    private String appointImgs;
    private Integer reward;
    private Integer state;
    private Integer confirmId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    private Integer sincerityMoneyValue;


    @Override
    public String toString() {
        return "AppointmentVo1{" +
                "uid=" + uid +
                ", nickname='" + nickname + '\'' +
                ", header='" + header + '\'' +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                ", aid=" + aid +
                ", publisherId=" + publisherId +
                ", appointAddress='" + appointAddress + '\'' +
                ", appointTime='" + appointTime + '\'' +
                ", appointContext='" + appointContext + '\'' +
                ", needNumber=" + needNumber +
                ", payType=" + payType +
                ", appointImgs='" + appointImgs + '\'' +
                ", reward=" + reward +
                ", state=" + state +
                ", confirmId=" + confirmId +
                ", createTime=" + createTime +
                ", sincerityMoneyValue=" + sincerityMoneyValue +
                '}';
    }

    public Integer getSincerityMoneyValue() {
        return sincerityMoneyValue;
    }

    public void setSincerityMoneyValue(Integer sincerityMoneyValue) {
        this.sincerityMoneyValue = sincerityMoneyValue;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
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

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public String getAppointAddress() {
        return appointAddress;
    }

    public void setAppointAddress(String appointAddress) {
        this.appointAddress = appointAddress;
    }

    public String getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(String appointTime) {
        this.appointTime = appointTime;
    }

    public String getAppointContext() {
        return appointContext;
    }

    public void setAppointContext(String appointContext) {
        this.appointContext = appointContext;
    }

    public Integer getNeedNumber() {
        return needNumber;
    }

    public void setNeedNumber(Integer needNumber) {
        this.needNumber = needNumber;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getAppointImgs() {
        return appointImgs;
    }

    public void setAppointImgs(String appointImgs) {
        this.appointImgs = appointImgs;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(Integer confirmId) {
        this.confirmId = confirmId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
