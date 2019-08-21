package com.meiyou.mapper;

import com.meiyou.pojo.AppointAsk;
import com.meiyou.pojo.AppointAskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppointAskMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appoint_ask
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int countByExample(AppointAskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appoint_ask
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int deleteByExample(AppointAskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appoint_ask
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appoint_ask
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int insert(AppointAsk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appoint_ask
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int insertSelective(AppointAsk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appoint_ask
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    List<AppointAsk> selectByExample(AppointAskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appoint_ask
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    AppointAsk selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appoint_ask
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int updateByExampleSelective(@Param("record") AppointAsk record, @Param("example") AppointAskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appoint_ask
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int updateByExample(@Param("record") AppointAsk record, @Param("example") AppointAskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appoint_ask
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int updateByPrimaryKeySelective(AppointAsk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appoint_ask
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int updateByPrimaryKey(AppointAsk record);
}