package com.meiyou.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 约会VO类
 * @author: Mr.Z
 * @create: 2019-08-30 14:05
 **/
public class AppointmentVO implements Serializable {

    private Integer id;
    private String publishNickName;
    private String publishHeader;
    private String publishBirthday;
    private String publishSignature;
    private Integer publishId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @Override
    public String toString() {
        return "AppointmentVO{" +
                "id=" + id +
                ", publishNickName='" + publishNickName + '\'' +
                ", publishHeader='" + publishHeader + '\'' +
                ", publishBirthday='" + publishBirthday + '\'' +
                ", publishSignature='" + publishSignature + '\'' +
                ", publishId=" + publishId +
                ", createTime=" + createTime +
                ", nums=" + nums +
                ", appointId=" + appointId +
                ", appointContext='" + appointContext + '\'' +
                ", appointTime='" + appointTime + '\'' +
                ", appointAddress='" + appointAddress + '\'' +
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

    private Integer nums;
    private Integer appointId;

    public Integer getAppointId() {
        return appointId;
    }

    public void setAppointId(Integer appointId) {
        this.appointId = appointId;
    }

    private String appointContext;
    private String appointTime;
    private String appointAddress;
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

    public String getAppointContext() {
        return appointContext;
    }

    public void setAppointContext(String appointContext) {
        this.appointContext = appointContext;
    }

    public String getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(String appointTime) {
        this.appointTime = appointTime;
    }

    public String getAppointAddress() {
        return appointAddress;
    }

    public void setAppointAddress(String appointAddress) {
        this.appointAddress = appointAddress;
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
