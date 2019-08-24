package com.meiyou.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 返回查询shop的VO类
 * @author: Mr.Z
 * @create: 2019-08-24 11:09
 **/
public class ShopVO implements Serializable {
    private Integer id;
    private Date createTime;
    private Date updateTime;
    private Integer publishId;
    private String serviceArea;
    private String travelTime;
    private Integer charge;
    private Date outTime;
    private String imgsUrl;
    private Integer state;
    private Boolean boolClose;
    private Integer nums;
    //查附近的club的时候需要用到的 距离
    private Double distance;
    private String header;

    @Override
    public String toString() {
        return "ShopVO{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", publishId=" + publishId +
                ", serviceArea='" + serviceArea + '\'' +
                ", travelTime='" + travelTime + '\'' +
                ", charge=" + charge +
                ", outTime=" + outTime +
                ", imgsUrl='" + imgsUrl + '\'' +
                ", state=" + state +
                ", boolClose=" + boolClose +
                ", nums=" + nums +
                ", distance=" + distance +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPublishId() {
        return publishId;
    }

    public void setPublishId(Integer publishId) {
        this.publishId = publishId;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public Integer getCharge() {
        return charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String getImgsUrl() {
        return imgsUrl;
    }

    public void setImgsUrl(String imgsUrl) {
        this.imgsUrl = imgsUrl;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Boolean getBoolClose() {
        return boolClose;
    }

    public void setBoolClose(Boolean boolClose) {
        this.boolClose = boolClose;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
