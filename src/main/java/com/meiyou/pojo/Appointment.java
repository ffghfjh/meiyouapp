package com.meiyou.pojo;

import java.util.Date;

public class Appointment {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment.id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment.publisher_id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Integer publisherId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment.appoint_address
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private String appointAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment.appoint_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private String appointTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment.appoint_context
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private String appointContext;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment.need_number
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Integer needNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment.pay_type
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Integer payType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment.appoint_imgs
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private String appointImgs;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment.reward
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Integer reward;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment.state
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Integer state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment.confirm_id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Integer confirmId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment.create_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment.update_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment.id
     *
     * @return the value of appointment.id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appointment.id
     *
     * @param id the value for appointment.id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment.publisher_id
     *
     * @return the value of appointment.publisher_id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Integer getPublisherId() {
        return publisherId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appointment.publisher_id
     *
     * @param publisherId the value for appointment.publisher_id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment.appoint_address
     *
     * @return the value of appointment.appoint_address
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public String getAppointAddress() {
        return appointAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appointment.appoint_address
     *
     * @param appointAddress the value for appointment.appoint_address
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setAppointAddress(String appointAddress) {
        this.appointAddress = appointAddress == null ? null : appointAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment.appoint_time
     *
     * @return the value of appointment.appoint_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public String getAppointTime() {
        return appointTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appointment.appoint_time
     *
     * @param appointTime the value for appointment.appoint_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setAppointTime(String appointTime) {
        this.appointTime = appointTime == null ? null : appointTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment.appoint_context
     *
     * @return the value of appointment.appoint_context
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public String getAppointContext() {
        return appointContext;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appointment.appoint_context
     *
     * @param appointContext the value for appointment.appoint_context
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setAppointContext(String appointContext) {
        this.appointContext = appointContext == null ? null : appointContext.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment.need_number
     *
     * @return the value of appointment.need_number
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Integer getNeedNumber() {
        return needNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appointment.need_number
     *
     * @param needNumber the value for appointment.need_number
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setNeedNumber(Integer needNumber) {
        this.needNumber = needNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment.pay_type
     *
     * @return the value of appointment.pay_type
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appointment.pay_type
     *
     * @param payType the value for appointment.pay_type
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment.appoint_imgs
     *
     * @return the value of appointment.appoint_imgs
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public String getAppointImgs() {
        return appointImgs;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appointment.appoint_imgs
     *
     * @param appointImgs the value for appointment.appoint_imgs
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setAppointImgs(String appointImgs) {
        this.appointImgs = appointImgs == null ? null : appointImgs.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment.reward
     *
     * @return the value of appointment.reward
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Integer getReward() {
        return reward;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appointment.reward
     *
     * @param reward the value for appointment.reward
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setReward(Integer reward) {
        this.reward = reward;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment.state
     *
     * @return the value of appointment.state
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appointment.state
     *
     * @param state the value for appointment.state
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment.confirm_id
     *
     * @return the value of appointment.confirm_id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Integer getConfirmId() {
        return confirmId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appointment.confirm_id
     *
     * @param confirmId the value for appointment.confirm_id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setConfirmId(Integer confirmId) {
        this.confirmId = confirmId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment.create_time
     *
     * @return the value of appointment.create_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appointment.create_time
     *
     * @param createTime the value for appointment.create_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment.update_time
     *
     * @return the value of appointment.update_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appointment.update_time
     *
     * @param updateTime the value for appointment.update_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}