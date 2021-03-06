package com.meiyou.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table shop
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table shop
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table shop
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    protected List<Criteria> oredCriteria;


    private Integer pageNo;
    private Integer pageSize;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shop
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public ShopExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shop
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shop
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shop
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shop
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shop
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shop
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shop
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shop
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shop
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shop
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table shop
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andPublishIdIsNull() {
            addCriterion("publish_id is null");
            return (Criteria) this;
        }

        public Criteria andPublishIdIsNotNull() {
            addCriterion("publish_id is not null");
            return (Criteria) this;
        }

        public Criteria andPublishIdEqualTo(Integer value) {
            addCriterion("publish_id =", value, "publishId");
            return (Criteria) this;
        }

        public Criteria andPublishIdNotEqualTo(Integer value) {
            addCriterion("publish_id <>", value, "publishId");
            return (Criteria) this;
        }

        public Criteria andPublishIdGreaterThan(Integer value) {
            addCriterion("publish_id >", value, "publishId");
            return (Criteria) this;
        }

        public Criteria andPublishIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("publish_id >=", value, "publishId");
            return (Criteria) this;
        }

        public Criteria andPublishIdLessThan(Integer value) {
            addCriterion("publish_id <", value, "publishId");
            return (Criteria) this;
        }

        public Criteria andPublishIdLessThanOrEqualTo(Integer value) {
            addCriterion("publish_id <=", value, "publishId");
            return (Criteria) this;
        }

        public Criteria andPublishIdIn(List<Integer> values) {
            addCriterion("publish_id in", values, "publishId");
            return (Criteria) this;
        }

        public Criteria andPublishIdNotIn(List<Integer> values) {
            addCriterion("publish_id not in", values, "publishId");
            return (Criteria) this;
        }

        public Criteria andPublishIdBetween(Integer value1, Integer value2) {
            addCriterion("publish_id between", value1, value2, "publishId");
            return (Criteria) this;
        }

        public Criteria andPublishIdNotBetween(Integer value1, Integer value2) {
            addCriterion("publish_id not between", value1, value2, "publishId");
            return (Criteria) this;
        }

        public Criteria andServiceAreaIsNull() {
            addCriterion("service_area is null");
            return (Criteria) this;
        }

        public Criteria andServiceAreaIsNotNull() {
            addCriterion("service_area is not null");
            return (Criteria) this;
        }

        public Criteria andServiceAreaEqualTo(String value) {
            addCriterion("service_area =", value, "serviceArea");
            return (Criteria) this;
        }

        public Criteria andServiceAreaNotEqualTo(String value) {
            addCriterion("service_area <>", value, "serviceArea");
            return (Criteria) this;
        }

        public Criteria andServiceAreaGreaterThan(String value) {
            addCriterion("service_area >", value, "serviceArea");
            return (Criteria) this;
        }

        public Criteria andServiceAreaGreaterThanOrEqualTo(String value) {
            addCriterion("service_area >=", value, "serviceArea");
            return (Criteria) this;
        }

        public Criteria andServiceAreaLessThan(String value) {
            addCriterion("service_area <", value, "serviceArea");
            return (Criteria) this;
        }

        public Criteria andServiceAreaLessThanOrEqualTo(String value) {
            addCriterion("service_area <=", value, "serviceArea");
            return (Criteria) this;
        }

        public Criteria andServiceAreaLike(String value) {
            addCriterion("service_area like", value, "serviceArea");
            return (Criteria) this;
        }

        public Criteria andServiceAreaNotLike(String value) {
            addCriterion("service_area not like", value, "serviceArea");
            return (Criteria) this;
        }

        public Criteria andServiceAreaIn(List<String> values) {
            addCriterion("service_area in", values, "serviceArea");
            return (Criteria) this;
        }

        public Criteria andServiceAreaNotIn(List<String> values) {
            addCriterion("service_area not in", values, "serviceArea");
            return (Criteria) this;
        }

        public Criteria andServiceAreaBetween(String value1, String value2) {
            addCriterion("service_area between", value1, value2, "serviceArea");
            return (Criteria) this;
        }

        public Criteria andServiceAreaNotBetween(String value1, String value2) {
            addCriterion("service_area not between", value1, value2, "serviceArea");
            return (Criteria) this;
        }

        public Criteria andTravelTimeIsNull() {
            addCriterion("travel_time is null");
            return (Criteria) this;
        }

        public Criteria andTravelTimeIsNotNull() {
            addCriterion("travel_time is not null");
            return (Criteria) this;
        }

        public Criteria andTravelTimeEqualTo(String value) {
            addCriterion("travel_time =", value, "travelTime");
            return (Criteria) this;
        }

        public Criteria andTravelTimeNotEqualTo(String value) {
            addCriterion("travel_time <>", value, "travelTime");
            return (Criteria) this;
        }

        public Criteria andTravelTimeGreaterThan(String value) {
            addCriterion("travel_time >", value, "travelTime");
            return (Criteria) this;
        }

        public Criteria andTravelTimeGreaterThanOrEqualTo(String value) {
            addCriterion("travel_time >=", value, "travelTime");
            return (Criteria) this;
        }

        public Criteria andTravelTimeLessThan(String value) {
            addCriterion("travel_time <", value, "travelTime");
            return (Criteria) this;
        }

        public Criteria andTravelTimeLessThanOrEqualTo(String value) {
            addCriterion("travel_time <=", value, "travelTime");
            return (Criteria) this;
        }

        public Criteria andTravelTimeLike(String value) {
            addCriterion("travel_time like", value, "travelTime");
            return (Criteria) this;
        }

        public Criteria andTravelTimeNotLike(String value) {
            addCriterion("travel_time not like", value, "travelTime");
            return (Criteria) this;
        }

        public Criteria andTravelTimeIn(List<String> values) {
            addCriterion("travel_time in", values, "travelTime");
            return (Criteria) this;
        }

        public Criteria andTravelTimeNotIn(List<String> values) {
            addCriterion("travel_time not in", values, "travelTime");
            return (Criteria) this;
        }

        public Criteria andTravelTimeBetween(String value1, String value2) {
            addCriterion("travel_time between", value1, value2, "travelTime");
            return (Criteria) this;
        }

        public Criteria andTravelTimeNotBetween(String value1, String value2) {
            addCriterion("travel_time not between", value1, value2, "travelTime");
            return (Criteria) this;
        }

        public Criteria andChargeIsNull() {
            addCriterion("charge is null");
            return (Criteria) this;
        }

        public Criteria andChargeIsNotNull() {
            addCriterion("charge is not null");
            return (Criteria) this;
        }

        public Criteria andChargeEqualTo(Integer value) {
            addCriterion("charge =", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeNotEqualTo(Integer value) {
            addCriterion("charge <>", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeGreaterThan(Integer value) {
            addCriterion("charge >", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeGreaterThanOrEqualTo(Integer value) {
            addCriterion("charge >=", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeLessThan(Integer value) {
            addCriterion("charge <", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeLessThanOrEqualTo(Integer value) {
            addCriterion("charge <=", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeIn(List<Integer> values) {
            addCriterion("charge in", values, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeNotIn(List<Integer> values) {
            addCriterion("charge not in", values, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeBetween(Integer value1, Integer value2) {
            addCriterion("charge between", value1, value2, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeNotBetween(Integer value1, Integer value2) {
            addCriterion("charge not between", value1, value2, "charge");
            return (Criteria) this;
        }

        public Criteria andOutTimeIsNull() {
            addCriterion("out_time is null");
            return (Criteria) this;
        }

        public Criteria andOutTimeIsNotNull() {
            addCriterion("out_time is not null");
            return (Criteria) this;
        }

        public Criteria andOutTimeEqualTo(Date value) {
            addCriterion("out_time =", value, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeNotEqualTo(Date value) {
            addCriterion("out_time <>", value, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeGreaterThan(Date value) {
            addCriterion("out_time >", value, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("out_time >=", value, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeLessThan(Date value) {
            addCriterion("out_time <", value, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeLessThanOrEqualTo(Date value) {
            addCriterion("out_time <=", value, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeIn(List<Date> values) {
            addCriterion("out_time in", values, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeNotIn(List<Date> values) {
            addCriterion("out_time not in", values, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeBetween(Date value1, Date value2) {
            addCriterion("out_time between", value1, value2, "outTime");
            return (Criteria) this;
        }

        public Criteria andOutTimeNotBetween(Date value1, Date value2) {
            addCriterion("out_time not between", value1, value2, "outTime");
            return (Criteria) this;
        }

        public Criteria andImgsUrlIsNull() {
            addCriterion("imgs_url is null");
            return (Criteria) this;
        }

        public Criteria andImgsUrlIsNotNull() {
            addCriterion("imgs_url is not null");
            return (Criteria) this;
        }

        public Criteria andImgsUrlEqualTo(String value) {
            addCriterion("imgs_url =", value, "imgsUrl");
            return (Criteria) this;
        }

        public Criteria andImgsUrlNotEqualTo(String value) {
            addCriterion("imgs_url <>", value, "imgsUrl");
            return (Criteria) this;
        }

        public Criteria andImgsUrlGreaterThan(String value) {
            addCriterion("imgs_url >", value, "imgsUrl");
            return (Criteria) this;
        }

        public Criteria andImgsUrlGreaterThanOrEqualTo(String value) {
            addCriterion("imgs_url >=", value, "imgsUrl");
            return (Criteria) this;
        }

        public Criteria andImgsUrlLessThan(String value) {
            addCriterion("imgs_url <", value, "imgsUrl");
            return (Criteria) this;
        }

        public Criteria andImgsUrlLessThanOrEqualTo(String value) {
            addCriterion("imgs_url <=", value, "imgsUrl");
            return (Criteria) this;
        }

        public Criteria andImgsUrlLike(String value) {
            addCriterion("imgs_url like", value, "imgsUrl");
            return (Criteria) this;
        }

        public Criteria andImgsUrlNotLike(String value) {
            addCriterion("imgs_url not like", value, "imgsUrl");
            return (Criteria) this;
        }

        public Criteria andImgsUrlIn(List<String> values) {
            addCriterion("imgs_url in", values, "imgsUrl");
            return (Criteria) this;
        }

        public Criteria andImgsUrlNotIn(List<String> values) {
            addCriterion("imgs_url not in", values, "imgsUrl");
            return (Criteria) this;
        }

        public Criteria andImgsUrlBetween(String value1, String value2) {
            addCriterion("imgs_url between", value1, value2, "imgsUrl");
            return (Criteria) this;
        }

        public Criteria andImgsUrlNotBetween(String value1, String value2) {
            addCriterion("imgs_url not between", value1, value2, "imgsUrl");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andBoolCloseIsNull() {
            addCriterion("bool_close is null");
            return (Criteria) this;
        }

        public Criteria andBoolCloseIsNotNull() {
            addCriterion("bool_close is not null");
            return (Criteria) this;
        }

        public Criteria andBoolCloseEqualTo(Boolean value) {
            addCriterion("bool_close =", value, "boolClose");
            return (Criteria) this;
        }

        public Criteria andBoolCloseNotEqualTo(Boolean value) {
            addCriterion("bool_close <>", value, "boolClose");
            return (Criteria) this;
        }

        public Criteria andBoolCloseGreaterThan(Boolean value) {
            addCriterion("bool_close >", value, "boolClose");
            return (Criteria) this;
        }

        public Criteria andBoolCloseGreaterThanOrEqualTo(Boolean value) {
            addCriterion("bool_close >=", value, "boolClose");
            return (Criteria) this;
        }

        public Criteria andBoolCloseLessThan(Boolean value) {
            addCriterion("bool_close <", value, "boolClose");
            return (Criteria) this;
        }

        public Criteria andBoolCloseLessThanOrEqualTo(Boolean value) {
            addCriterion("bool_close <=", value, "boolClose");
            return (Criteria) this;
        }

        public Criteria andBoolCloseIn(List<Boolean> values) {
            addCriterion("bool_close in", values, "boolClose");
            return (Criteria) this;
        }

        public Criteria andBoolCloseNotIn(List<Boolean> values) {
            addCriterion("bool_close not in", values, "boolClose");
            return (Criteria) this;
        }

        public Criteria andBoolCloseBetween(Boolean value1, Boolean value2) {
            addCriterion("bool_close between", value1, value2, "boolClose");
            return (Criteria) this;
        }

        public Criteria andBoolCloseNotBetween(Boolean value1, Boolean value2) {
            addCriterion("bool_close not between", value1, value2, "boolClose");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table shop
     *
     * @mbggenerated do_not_delete_during_merge Wed Aug 21 16:35:07 CST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table shop
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}