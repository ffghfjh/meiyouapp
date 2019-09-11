package com.meiyou.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @program: meiyouapp
 * @description:
 * @author: JK
 * @create: 2019-09-09 17:18
 **/
public class TourVo1 {
    private Integer uid;
    private String nickname;
    private String header;
    private Boolean sex;
    private String birthday;
    private Integer tid;
    private Integer publisherId;
    private String startAddress;
    private String endAddress;
    private String goTime;
    private String goMessage;
    private Integer needNum;
    private Integer payType;
    private Integer reward;
    private Integer state;
    private Integer confirmId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    private Integer sincerityMoneyValue;


    @Override
    public String toString() {
        return "TourVo1{" +
                "uid=" + uid +
                ", nickname='" + nickname + '\'' +
                ", header='" + header + '\'' +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                ", tid=" + tid +
                ", publisherId=" + publisherId +
                ", startAddress='" + startAddress + '\'' +
                ", endAddress='" + endAddress + '\'' +
                ", goTime='" + goTime + '\'' +
                ", goMessage='" + goMessage + '\'' +
                ", needNum=" + needNum +
                ", payType=" + payType +
                ", reward=" + reward +
                ", state=" + state +
                ", confirmId=" + confirmId +
                ", createTime=" + createTime +
                ", sincerityMoneyValue=" + sincerityMoneyValue +
                '}';
    }

    public Integer getNeedNum() {
        return needNum;
    }

    public void setNeedNum(Integer needNum) {
        this.needNum = needNum;
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

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public String getGoTime() {
        return goTime;
    }

    public void setGoTime(String goTime) {
        this.goTime = goTime;
    }

    public String getGoMessage() {
        return goMessage;
    }

    public void setGoMessage(String goMessage) {
        this.goMessage = goMessage;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
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

    public Integer getSincerityMoneyValue() {
        return sincerityMoneyValue;
    }

    public void setSincerityMoneyValue(Integer sincerityMoneyValue) {
        this.sincerityMoneyValue = sincerityMoneyValue;
    }
}
