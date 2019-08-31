package com.meiyou.service;

import com.meiyou.model.LayuiTableJson;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/31 13:45
 * @description：充值接口层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
public interface RechargeService {

    /**
     * 获得所有充值记录
     * @return
     */
    LayuiTableJson listRecharge(int page, int limit);

    /**
     * 获取所有充值记录总条数
     * @return
     */
    int getRechargeTotalCount();

    /**
     * 审核通过
     * @param id 充值记录的id
     * @param type 审核类型，0不通过，1通过
     * @return
     */
    LayuiTableJson checkPassById(int page, int limit, int id, int type);

}
