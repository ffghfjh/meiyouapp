package com.meiyou.mapper;

import com.meiyou.pojo.RootMessage;
import com.meiyou.pojo.RootMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RootMessageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table root_message
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int countByExample(RootMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table root_message
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int deleteByExample(RootMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table root_message
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table root_message
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int insert(RootMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table root_message
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int insertSelective(RootMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table root_message
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    List<RootMessage> selectByExample(RootMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table root_message
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    RootMessage selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table root_message
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int updateByExampleSelective(@Param("record") RootMessage record, @Param("example") RootMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table root_message
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int updateByExample(@Param("record") RootMessage record, @Param("example") RootMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table root_message
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int updateByPrimaryKeySelective(RootMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table root_message
     *
     * @mbggenerated Wed Aug 21 14:28:33 CST 2019
     */
    int updateByPrimaryKey(RootMessage record);
}