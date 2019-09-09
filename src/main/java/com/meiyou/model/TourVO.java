package com.meiyou.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 旅游VO类
 * @author: Mr.Z
 * @create: 2019-08-30 14:05
 **/
public class TourVO implements Serializable {

    private Integer id;
    private Integer publishId;
    private String publishNickName;
    private String publishHeader;
    private String publishBirthday;
    private String publishSignature;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    private Integer nums;
    private Integer appointId;

    @Override
    public String toString() {
        return "TourVO{" +
                "id=" + id +
                ", publishId=" + publishId +
                ", publishNickName='" + publishNickName + '\'' +
                ", publishHeader='" + publishHeader + '\'' +
                ", publishBirthday='" + publishBirthday + '\'' +
                ", publishSignature='" + publishSignature + '\'' +
                ", createTime=" + createTime +
                ", nums=" + nums +
                ", appointId=" + appointId +
                ", goMessage='" + goMessage + '\'' +
                ", startAddress='" + startAddress + '\'' +
                ", endAddress='" + endAddress + '\'' +
                ", goTime='" + goTime + '\'' +
                ", state=" + state +
                ", askState=" + askState +
                ", askerHeader=" + askerHeader +
                '}';
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPublishId() {
        return publishId;
    }

    public void setPublishId(Integer publishId) {
        this.publishId = publishId;
    }

    public Integer getAppointId() {
        return appointId;
    }

    public void setAppointId(Integer appointId) {
        this.appointId = appointId;
    }

    private String goMessage;
    private String startAddress;
    private String endAddress;
    private String goTime;
    private Integer state;
    private Integer askState;
    private List<String> askerHeader;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPublishNickName() {
        return publishNickName;
    }

    public void setPublishNickName(String publishNickName) {
        this.publishNickName = publishNickName;
    }

    public String getPublishHeader() {
        return publishHeader;
    }

    public void setPublishHeader(String publishHeader) {
        this.publishHeader = publishHeader;
    }

    public String getPublishBirthday() {
        return publishBirthday;
    }

    public void setPublishBirthday(String publishBirthday) {
        this.publishBirthday = publishBirthday;
    }

    public String getPublishSignature() {
        return publishSignature;
    }

    public void setPublishSignature(String publishSignature) {
        this.publishSignature = publishSignature;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public String getGoMessage() {
        return goMessage;
    }

    public void setGoMessage(String goMessage) {
        this.goMessage = goMessage;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getAskState() {
        return askState;
    }

    public void setAskState(Integer askState) {
        this.askState = askState;
    }

    public List<String> getAskerHeader() {
        return askerHeader;
    }

    public void setAskerHeader(List<String> askerHeader) {
        this.askerHeader = askerHeader;
    }
}
