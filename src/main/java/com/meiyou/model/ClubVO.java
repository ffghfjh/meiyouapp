package com.meiyou.model;

import java.util.Date;

/**
 * @description: 返回查询club的VO类
 * @author: Mr.Z
 * @create: 2019-08-24 10:35
 **/
public class ClubVO {

    //nums 为查询报名了这个club的人数
    private Integer id;
    private Date createTime;
    private Date updateTime;
    private Integer publishId;
    private String imgsUrl;
    private String projectName;
    private String projectDesc;
    private String projectAddress;
    private Integer projectPrice;
    private Integer marketPrice;
    private Date outTime;
    private Integer state;
    private Integer nums;
    //查附近的club的时候需要用到的 距离
    private Double distance;

    @Override
    public String toString() {
        return "ClubVO{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", publishId=" + publishId +
                ", imgsUrl='" + imgsUrl + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectDesc='" + projectDesc + '\'' +
                ", projectAddress='" + projectAddress + '\'' +
                ", projectPrice=" + projectPrice +
                ", marketPrice=" + marketPrice +
                ", outTime=" + outTime +
                ", state=" + state +
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

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
