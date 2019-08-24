package com.meiyou.mapper;

import com.meiyou.pojo.ActivityRead;
import com.meiyou.pojo.ActivityReadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActivityReadMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_read
     *
     * @mbggenerated Sat Aug 24 14:27:29 CST 2019
     */
    int countByExample(ActivityReadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_read
     *
     * @mbggenerated Sat Aug 24 14:27:29 CST 2019
     */
    int deleteByExample(ActivityReadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_read
     *
     * @mbggenerated Sat Aug 24 14:27:29 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_read
     *
     * @mbggenerated Sat Aug 24 14:27:29 CST 2019
     */
    int insert(ActivityRead record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_read
     *
     * @mbggenerated Sat Aug 24 14:27:29 CST 2019
     */
    int insertSelective(ActivityRead record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_read
     *
     * @mbggenerated Sat Aug 24 14:27:29 CST 2019
     */
    List<ActivityRead> selectByExample(ActivityReadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_read
     *
     * @mbggenerated Sat Aug 24 14:27:29 CST 2019
     */
    ActivityRead selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_read
     *
     * @mbggenerated Sat Aug 24 14:27:29 CST 2019
     */
    int updateByExampleSelective(@Param("record") ActivityRead record, @Param("example") ActivityReadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_read
     *
     * @mbggenerated Sat Aug 24 14:27:29 CST 2019
     */
    int updateByExample(@Param("record") ActivityRead record, @Param("example") ActivityReadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_read
     *
     * @mbggenerated Sat Aug 24 14:27:29 CST 2019
     */
    int updateByPrimaryKeySelective(ActivityRead record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_read
     *
     * @mbggenerated Sat Aug 24 14:27:29 CST 2019
     */
    int updateByPrimaryKey(ActivityRead record);
}