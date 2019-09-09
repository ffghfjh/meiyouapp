package com.meiyou.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * @program: meiyouapp
 * @description:
 * @author: JK
 * @create: 2019-09-07 14:31
 **/
public class MyPublishVo {
    private Integer index;
    private Integer id;
    private Integer type;
    private String publishNickName;
    private String publishHeader;
    private String publishBirthday;
    private String publishSignature;
    private Integer publishId;
    private Integer nums;
    private Integer appointId;
    private String appointContext;
    private String appointTime;
    private String appointAddress;
    private Integer state;
    private Integer askState;
    private List<String> askerHeader;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    private Integer clubId;
    private String imgsUrl;
    private String projectName;
    private String projectDesc;
    private String projectAddress;
    private Integer projectPrice;
    private Integer marketPrice;
    //查附近的club的时候需要用到的 距离
    private Double distance;
    //club的星级
    private Integer star;
    //报名者的头像
    private List<String> header;


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
    private Boolean boolClose;

    @Override
    public String toString() {
        return "MyPublishVo{" +
                "id=" + id +
                ", type=" + type +
                ", publishNickName='" + publishNickName + '\'' +
                ", publishHeader='" + publishHeader + '\'' +
                ", publishBirthday='" + publishBirthday + '\'' +
                ", publishSignature='" + publishSignature + '\'' +
                ", publishId=" + publishId +
                ", nums=" + nums +
                ", appointId=" + appointId +
                ", appointContext='" + appointContext + '\'' +
                ", appointTime='" + appointTime + '\'' +
                ", appointAddress='" + appointAddress + '\'' +
                ", state=" + state +
                ", askState=" + askState +
                ", askerHeader=" + askerHeader +
                ", createTime=" + createTime +
                ", clubId=" + clubId +
                ", imgsUrl='" + imgsUrl + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectDesc='" + projectDesc + '\'' +
                ", projectAddress='" + projectAddress + '\'' +
                ", projectPrice=" + projectPrice +
                ", marketPrice=" + marketPrice +
                ", distance=" + distance +
                ", star=" + star +
                ", header=" + header +
                ", shopId=" + shopId +
                ", publishIdHeader='" + publishIdHeader + '\'' +
                ", publishIdNickname='" + publishIdNickname + '\'' +
                ", publishIdSex=" + publishIdSex +
                ", publishIdBirthday='" + publishIdBirthday + '\'' +
                ", publishIdSignature='" + publishIdSignature + '\'' +
                ", serviceArea='" + serviceArea + '\'' +
                ", travelTime='" + travelTime + '\'' +
                ", charge=" + charge +
                ", boolClose=" + boolClose +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getPublishId() {
        return publishId;
    }

    public void setPublishId(Integer publishId) {
        this.publishId = publishId;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public Integer getAppointId() {
        return appointId;
    }

    public void setAppointId(Integer appointId) {
        this.appointId = appointId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    public String getImgsUrl() {
        return imgsUrl;
    }

    public void setImgsUrl(String imgsUrl) {
        this.imgsUrl = imgsUrl;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public Integer getProjectPrice() {
        return projectPrice;
    }

    public void setProjectPrice(Integer projectPrice) {
        this.projectPrice = projectPrice;
    }

    public Integer getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Integer marketPrice) {
        this.marketPrice = marketPrice;
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

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
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

    public Boolean getBoolClose() {
        return boolClose;
    }

    public void setBoolClose(Boolean boolClose) {
        this.boolClose = boolClose;
    }
}
