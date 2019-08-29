package com.meiyou.service;

import com.meiyou.utils.Msg;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/26 13:25
 * @description：
 * @modified By：huangzhaoyang
 * @version:
 */
public interface ActivityReportService {

    /**
     * 动态举报
     * @param aid 动态id
     * @param uid 举报人id
     * @param type 举报类型
     * @return
     */
    Msg report(int aid, int uid, String type);

}
