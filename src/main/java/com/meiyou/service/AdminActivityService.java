package com.meiyou.service;

import com.meiyou.model.LayuiTableJson;
import com.meiyou.utils.Msg;

import java.util.HashMap;
import java.util.List;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/29 14:06
 * @description：管理员动态服务层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
public interface AdminActivityService {

    /**
     * 通过动态id不屏蔽动态
     * @param aid
     * @return
     */
    LayuiTableJson noHideActvityById(int page, int limit, int aid);

    /**
     * 通过用户id不屏蔽用户
     * @param uid
     * @return
     */
    LayuiTableJson noHideUserById(int page, int limit, int uid);

    /**
     * 通过动态id屏蔽动态
     * @param aid
     * @return
     */
    LayuiTableJson hideActivityById(int page, int limit, int aid);

    /**
     * 通过用户id屏蔽用户
     * @param uid
     * @return
     */
    LayuiTableJson hideUserById(int page, int limit, int uid);

    /**
     * 管理员获得举报信息
     * @return
     */
    LayuiTableJson listActivityReport(int page, int limit);

    /**
     * 获得所有动态的总行数
     * @return
     */
    int getActivityReportTotalCount();

}
