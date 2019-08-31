package com.meiyou.service;

import com.meiyou.model.LayuiTableJson;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/30 14:24
 * @description：用户举报接口层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
public interface UserReportService {

    /**
     * 通过账号屏蔽用户
     * @param account
     * @return
     */
    LayuiTableJson hidePersonByAccount(String account);

    /**
     * 屏蔽被举报人
     * @param uid
     * @param type
     * @return
     */
    LayuiTableJson hideReportedPersonById(int page, int limit, int uid, int type);

    /**
     * 获得所有用户举报信息
     * @return
     */
    LayuiTableJson listUserReport(int page, int limit);

    /**
     * 获得用户举报总条数
     * @return
     */
    int getUserReportTotolCount();

}
