package com.meiyou.pojo;

import java.util.Date;

public class Shop {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shop.id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shop.create_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shop.update_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shop.publish_id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Integer publishId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shop.service_area
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private String serviceArea;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shop.travel_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private String travelTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shop.charge
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Integer charge;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shop.out_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Date outTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shop.imgs_url
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private String imgsUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shop.state
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Integer state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shop.bool_close
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Boolean boolClose;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shop.id
     *
     * @return the value of shop.id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shop.id
     *
     * @param id the value for shop.id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shop.create_time
     *
     * @return the value of shop.create_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shop.create_time
     *
     * @param createTime the value for shop.create_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shop.update_time
     *
     * @return the value of shop.update_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shop.update_time
     *
     * @param updateTime the value for shop.update_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shop.publish_id
     *
     * @return the value of shop.publish_id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Integer getPublishId() {
        return publishId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shop.publish_id
     *
     * @param publishId the value for shop.publish_id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setPublishId(Integer publishId) {
        this.publishId = publishId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shop.service_area
     *
     * @return the value of shop.service_area
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public String getServiceArea() {
        return serviceArea;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shop.service_area
     *
     * @param serviceArea the value for shop.service_area
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea == null ? null : serviceArea.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shop.travel_time
     *
     * @return the value of shop.travel_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public String getTravelTime() {
        return travelTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shop.travel_time
     *
     * @param travelTime the value for shop.travel_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime == null ? null : travelTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shop.charge
     *
     * @return the value of shop.charge
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Integer getCharge() {
        return charge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shop.charge
     *
     * @param charge the value for shop.charge
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shop.out_time
     *
     * @return the value of shop.out_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Date getOutTime() {
        return outTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shop.out_time
     *
     * @param outTime the value for shop.out_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shop.imgs_url
     *
     * @return the value of shop.imgs_url
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public String getImgsUrl() {
        return imgsUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shop.imgs_url
     *
     * @param imgsUrl the value for shop.imgs_url
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setImgsUrl(String imgsUrl) {
        this.imgsUrl = imgsUrl == null ? null : imgsUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shop.state
     *
     * @return the value of shop.state
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shop.state
     *
     * @param state the value for shop.state
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shop.bool_close
     *
     * @return the value of shop.bool_close
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Boolean getBoolClose() {
        return boolClose;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shop.bool_close
     *
     * @param boolClose the value for shop.bool_close
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setBoolClose(Boolean boolClose) {
        this.boolClose = boolClose;
    }
}