package com.meiyou.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 返回查询shop的VO类
 * @author: Mr.Z
 * @create: 2019-08-24 11:09
 **/
public class ShopVO implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    private Integer id;
    private Integer publishId;
    private Integer shopId;
    // 导游头像 导游昵称 导游性别 导游年龄 导游个性签名
    private String publishIdHeader;
    private String publishIdNickname;
    private Boolean publishIdSex;
    private String publishIdBirthday;
    private String publishIdSignature;

    private String serviceArea;
    private String travelTime;
    private Integer charge;
    private String imgsUrl;
    private Integer state;
    //聘用者状态
    private Integer askState;
    private Boolean boolClose;
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
                "createTime=" + createTime +
                ", id=" + id +
                ", publishId=" + publishId +
                ", shopId=" + shopId +
                ", publishIdHeader='" + publishIdHeader + '\'' +
                ", publishIdNickname='" + publishIdNickname + '\'' +
                ", publishIdSex=" + publishIdSex +
                ", publishIdBirthday='" + publishIdBirthday + '\'' +
                ", publishIdSignature='" + publishIdSignature + '\'' +
                ", serviceArea='" + serviceArea + '\'' +
                ", travelTime='" + travelTime + '\'' +
                ", charge=" + charge +
                ", imgsUrl='" + imgsUrl + '\'' +
                ", state=" + state +
                ", askState=" + askState +
                ", boolClose=" + boolClose +
                ", nums=" + nums +
                ", distance=" + distance +
                ", star=" + star +
                ", header=" + header +
                '}';
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getPublishIdHeader() {
        return publishIdHeader;
    }

    public void setPublishIdHeader(String publishIdHeader) {
        this.publishIdHeader = publishIdHeader;
    }

    public String getPublishIdNickname() {
        return publishIdNickname;
    }

    public void setPublishIdNickname(String publishIdNickname) {
        this.publishIdNickname = publishIdNickname;
    }

    public Boolean getPublishIdSex() {
        return publishIdSex;
    }

    public void setPublishIdSex(Boolean publishIdSex) {
        this.publishIdSex = publishIdSex;
    }

    public String getPublishIdBirthday() {
        return publishIdBirthday;
    }

    public void setPublishIdBirthday(String publishIdBirthday) {
        this.publishIdBirthday = publishIdBirthday;
    }

    public String getPublishIdSignature() {
        return publishIdSignature;
    }

    public void setPublishIdSignature(String publishIdSignature) {
        this.publishIdSignature = publishIdSignature;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
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

    public Integer getAskState() {
        return askState;
    }

    public void setAskState(Integer askState) {
        this.askState = askState;
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
