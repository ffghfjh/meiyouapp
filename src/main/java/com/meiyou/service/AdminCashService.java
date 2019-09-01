package com.meiyou.service;

import com.meiyou.model.LayuiTableJson;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/31 15:40
 * @description：管理提现控制层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
public interface AdminCashService {

    /**
     * 获取所有提现记录
     * @param page 页码
     * @param limit 页数
     * @return
     */
    LayuiTableJson listCash(int page, int limit);

    /**
     * 提现审核
     * @param page 页码
     * @param limit 条数
     * @param id 提现id
     * @param type 1时审核通过，0时审核不通过
     * @return
     */
    LayuiTableJson checkPassCash(int page, int limit, int id, int type);

    /**
     * 获取提现总条数
     * @return
     */
    int getCashTotalCount();

}
