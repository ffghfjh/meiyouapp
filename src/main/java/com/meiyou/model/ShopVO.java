package com.meiyou.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 返回查询shop的VO类
 * @author: Mr.Z
 * @create: 2019-08-24 11:09
 **/
public class ShopVO implements Serializable {
    private Integer id;
    private Integer publishId;
    private String serviceArea;
    private String travelTime;
    private Integer charge;
    private String imgsUrl;
    private Integer state;
    private Boolean boolClose;
    //Todo 导游头像 导游昵称 导游性别 导游年龄 导游个性签名
    //聘请该导游的人数
    private Integer nums;
    //查附近的club的时候需要用到的 距离
    private Double distance;
    //club的星级
    private Integer star;
    //聘请导游的人的头像
    private List<String> header;

    @Override
    public String toString() {
        return "ShopVO{" +
                "id=" + id +
                ", publishId=" + publishId +
                ", serviceArea='" + serviceArea + '\'' +
                ", travelTime='" + travelTime + '\'' +
                ", charge=" + charge +
                ", imgsUrl='" + imgsUrl + '\'' +
                ", state=" + state +
                ", boolClose=" + boolClose +
                ", nums=" + nums +
                ", distance=" + distance +
                ", star=" + star +
                ", header=" + header +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public List<String> getHeader() {
        return header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }
}
