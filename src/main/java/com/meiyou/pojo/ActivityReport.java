package com.meiyou.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ActivityReport {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_report.id
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_report.type
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_report.reporter_id
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    private Integer reporterId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_report.activity_id
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    private Integer activityId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_report.create_time
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_report.update_teim
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    private Date updateTeim;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_report.id
     *
     * @return the value of activity_report.id
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_report.id
     *
     * @param id the value for activity_report.id
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_report.type
     *
     * @return the value of activity_report.type
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_report.type
     *
     * @param type the value for activity_report.type
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_report.reporter_id
     *
     * @return the value of activity_report.reporter_id
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    public Integer getReporterId() {
        return reporterId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_report.reporter_id
     *
     * @param reporterId the value for activity_report.reporter_id
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    public void setReporterId(Integer reporterId) {
        this.reporterId = reporterId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_report.activity_id
     *
     * @return the value of activity_report.activity_id
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    public Integer getActivityId() {
        return activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_report.activity_id
     *
     * @param activityId the value for activity_report.activity_id
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_report.create_time
     *
     * @return the value of activity_report.create_time
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_report.create_time
     *
     * @param createTime the value for activity_report.create_time
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_report.update_teim
     *
     * @return the value of activity_report.update_teim
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    public Date getUpdateTeim() {
        return updateTeim;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_report.update_teim
     *
     * @param updateTeim the value for activity_report.update_teim
     *
     * @mbggenerated Thu Aug 29 09:29:43 CST 2019
     */
    public void setUpdateTeim(Date updateTeim) {
        this.updateTeim = updateTeim;
    }
}