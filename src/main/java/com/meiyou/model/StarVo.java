package com.meiyou.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 我的评价VO类
 * @author: Mr.Z
 * @create: 2019-08-29 17:22
 **/
public class StarVo implements Serializable {
    private Integer id;
    private Integer publishId;
    private String publishHeader;

    private String PublishDesc;
    private Integer star;
    private Integer addId;

    private String addNickname;
    private String addHeader;
    private Date addTime;

    @Override
    public String toString() {
        return "StarVo{" +
                "id=" + id +
                ", publishId=" + publishId +
                ", publishHeader='" + publishHeader + '\'' +
                ", PublishDesc='" + PublishDesc + '\'' +
                ", star=" + star +
                ", addId=" + addId +
                ", addNickname='" + addNickname + '\'' +
                ", addHeader='" + addHeader + '\'' +
                ", addTime=" + addTime +
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

    public String getPublishHeader() {
        return publishHeader;
    }

    public void setPublishHeader(String publishHeader) {
        this.publishHeader = publishHeader;
    }

    public String getPublishDesc() {
        return PublishDesc;
    }

    public void setPublishDesc(String publishDesc) {
        PublishDesc = publishDesc;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getAddId() {
        return addId;
    }

    public void setAddId(Integer addId) {
        this.addId = addId;
    }

    public String getAddNickname() {
        return addNickname;
    }

    public void setAddNickname(String addNickname) {
        this.addNickname = addNickname;
    }

    public String getAddHeader() {
        return addHeader;
    }

    public void setAddHeader(String addHeader) {
        this.addHeader = addHeader;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
