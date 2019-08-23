package com.meiyou;

import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateUtil;
import org.junit.Test;

import java.util.Date;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/22 20:51
 * @description：工具类测试
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
public class ToolTest {

    /**
     * hzy
     * 测试时间差
     */
    @Test
    public void printTime() {
        String dateStr1 = "2017-03-01 22:33:23";
        Date date1 = DateUtil.parse(dateStr1);

        String dateStr2 = "2018-04-01 22:33:15";
        Date date2 = DateUtil.parse(dateStr2);
        String formatBetween = DateUtil.formatBetween(date1, date2, BetweenFormater.Level.SECOND) + "前";
        System.out.println(formatBetween);
    }

}
