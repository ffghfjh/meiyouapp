package com.meiyou.mapper;

import com.meiyou.pojo.TourAsk;
import com.meiyou.pojo.TourAskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TourAskMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tour_ask
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    int countByExample(TourAskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tour_ask
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    int deleteByExample(TourAskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tour_ask
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tour_ask
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    int insert(TourAsk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tour_ask
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    int insertSelective(TourAsk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tour_ask
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    List<TourAsk> selectByExample(TourAskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tour_ask
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    TourAsk selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tour_ask
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    int updateByExampleSelective(@Param("record") TourAsk record, @Param("example") TourAskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tour_ask
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    int updateByExample(@Param("record") TourAsk record, @Param("example") TourAskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tour_ask
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    int updateByPrimaryKeySelective(TourAsk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tour_ask
     *
     * @mbggenerated Wed Aug 21 16:35:07 CST 2019
     */
    int updateByPrimaryKey(TourAsk record);
}