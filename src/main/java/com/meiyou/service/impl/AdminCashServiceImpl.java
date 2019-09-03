package com.meiyou.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meiyou.mapper.CashMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.model.LayuiTableJson;
import com.meiyou.pojo.Cash;
import com.meiyou.pojo.CashExample;
import com.meiyou.pojo.User;
import com.meiyou.service.AdminCashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/31 15:44
 * @description：提现管理业务实现层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Service
public class AdminCashServiceImpl implements AdminCashService {

    @Autowired
    CashMapper cashMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 获取所有提现记录
     *
     * @param page  页码
     * @param limit 页数
     * @return
     */
    @Override
    public LayuiTableJson listCash(int page, int limit) {
        PageHelper.startPage(page, limit);//20为每页的大小
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        CashExample cashExample = new CashExample();
        cashExample.setOrderByClause("id desc");
        List<Cash> cashList = cashMapper.selectByExample(cashExample);
        if (cashList.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("id", 0);
            hashMap.put("cash_number", "无数据");
            hashMap.put("account", "无数据");
            hashMap.put("cash_money", "无数据");
            hashMap.put("cash_type", "无数据");
            hashMap.put("state", "无数据");
            hashMap.put("time", "无数据");
            list.add(hashMap);
            PageInfo pageInfo = new PageInfo(list);
            return LayuiTableJson.success().addCount(0).addData(pageInfo);
        }
        //遍历提现记录
        for (Cash cash : cashList) {
            //查找用户
            User user = userMapper.selectByPrimaryKey(cash.getCashId());
            if (user == null) {
                continue;
            }
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("id", cash.getId());
            hashMap.put("cash_number", cash.getCashNumber());
            hashMap.put("account", user.getAccount());
            hashMap.put("cash_money", cash.getCashMoney());
            hashMap.put("cash_type", cash.getCashType());
            hashMap.put("state", cash.getState());
            hashMap.put("time", DateUtil.formatDateTime(cash.getCreateTime()));
            list.add(hashMap);
        }
        if (list.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("id", 0);
            hashMap.put("cash_number", "无数据");
            hashMap.put("account", "无数据");
            hashMap.put("cash_money", "无数据");
            hashMap.put("cash_type", "无数据");
            hashMap.put("state", "无数据");
            hashMap.put("time", "无数据");
            list.add(hashMap);
            PageInfo pageInfo = new PageInfo(list);
            return LayuiTableJson.success().addCount(0).addData(pageInfo);
        }
        PageInfo pageInfo = new PageInfo(list);
        return LayuiTableJson.success().addCount(getCashTotalCount()).addData(pageInfo);
    }

    /**
     * 提现审核
     *
     * @param page  页码
     * @param limit 条数
     * @param id    提现id
     * @param type  1时审核通过，0时审核不通过
     * @return
     */
    @Override
    public LayuiTableJson checkPassCash(int page, int limit, int id, int type) {
        Cash cash = new Cash();
        cash.setId(id);
        cash.setUpdateTime(new Date());
        //审核通过
        if (type == 1) {
            cash.setState(1);
            cashMapper.updateByPrimaryKeySelective(cash);
            return listCash(page, limit);
        }
        //审核不通过
        cash.setState(2);
        cashMapper.updateByPrimaryKeySelective(cash);
        return listCash(page, limit);
    }

    /**
     * 获取提现总条数
     *
     * @return
     */
    @Override
    public int getCashTotalCount() {
        int count = 0;
        List<Cash> cashList = cashMapper.selectByExample(null);
        if (cashList.isEmpty()) {
            return count;
        }
        for (Cash cash : cashList) {
            User user = userMapper.selectByPrimaryKey(cash.getCashId());
            if (user == null) {
                continue;
            }
            count++;
        }
        return count;
    }
}
