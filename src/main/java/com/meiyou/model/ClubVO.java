package com.meiyou.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 返回查询club的VO类
 * @author: Mr.Z
 * @create: 2019-08-24 10:35
 **/
public class ClubVO implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    //nums 为查询报名了这个club的人数
    private Integer id;
    private Integer publishId;
    private String publishHeader;

    private Integer clubId;
    private String imgsUrl;
    private String projectName;
    private String projectDesc;
    private String projectAddress;
    private Integer projectPrice;
    private Integer marketPrice;
    //会所状态
    private Integer state;
    //购买者状态
    private Integer askState;
    //购买人数
    private Integer nums;
    //查附近的club的时候需要用到的 距离
    private Double distance;
    //club的星级
    private Integer star;
    //报名者的头像
    private List<String> header;

    @Override
    public String toString() {
        return "ClubVO{" +
                "createTime=" + createTime +
                ", id=" + id +
                ", publishId=" + publishId +
                ", publishHeader='" + publishHeader + '\'' +
                ", clubId=" + clubId +
                ", imgsUrl='" + imgsUrl + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectDesc='" + projectDesc + '\'' +
                ", projectAddress='" + projectAddress + '\'' +
                ", projectPrice=" + projectPrice +
                ", marketPrice=" + marketPrice +
                ", state=" + state +
                ", askState=" + askState +
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

    public String getPublishHeader() {
        return publishHeader;
    }

    public void setPublishHeader(String publishHeader) {
        this.publishHeader = publishHeader;
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
