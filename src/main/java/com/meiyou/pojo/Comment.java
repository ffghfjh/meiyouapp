package com.meiyou.pojo;

import java.util.Date;

public class Comment {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.create_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.update_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.activity_id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Integer activityId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.person_id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Integer personId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.content
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.bool_see
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Boolean boolSee;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.bool_close
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    private Boolean boolClose;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.id
     *
     * @return the value of comment.id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.id
     *
     * @param id the value for comment.id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.create_time
     *
     * @return the value of comment.create_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.create_time
     *
     * @param createTime the value for comment.create_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.update_time
     *
     * @return the value of comment.update_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.update_time
     *
     * @param updateTime the value for comment.update_time
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.activity_id
     *
     * @return the value of comment.activity_id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Integer getActivityId() {
        return activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.activity_id
     *
     * @param activityId the value for comment.activity_id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.person_id
     *
     * @return the value of comment.person_id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Integer getPersonId() {
        return personId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.person_id
     *
     * @param personId the value for comment.person_id
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.content
     *
     * @return the value of comment.content
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.content
     *
     * @param content the value for comment.content
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.bool_see
     *
     * @return the value of comment.bool_see
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Boolean getBoolSee() {
        return boolSee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.bool_see
     *
     * @param boolSee the value for comment.bool_see
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setBoolSee(Boolean boolSee) {
        this.boolSee = boolSee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.bool_close
     *
     * @return the value of comment.bool_close
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Boolean getBoolClose() {
        return boolClose;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.bool_close
     *
     * @param boolClose the value for comment.bool_close
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setBoolClose(Boolean boolClose) {
        this.boolClose = boolClose;
    }
}