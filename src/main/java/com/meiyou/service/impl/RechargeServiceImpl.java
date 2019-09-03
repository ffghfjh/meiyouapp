package com.meiyou.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meiyou.mapper.RechargeMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.model.LayuiTableJson;
import com.meiyou.pojo.Recharge;
import com.meiyou.pojo.RechargeExample;
import com.meiyou.pojo.User;
import com.meiyou.service.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/31 13:46
 * @description：管理员充值业务实现层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Service
public class RechargeServiceImpl implements RechargeService {

    @Autowired
    RechargeMapper rechargeMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 获得所有充值记录
     * @param page
     * @param limit
     * @return
     */
    @Override
    public LayuiTableJson listRecharge(int page, int limit) {
        PageHelper.startPage(page, limit);//20为每页的大小
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        RechargeExample rechargeExample = new RechargeExample();
        rechargeExample.setOrderByClause("id desc");
        //获取所有充值记录
        List<Recharge> recharges = rechargeMapper.selectByExample(rechargeExample);
        if (recharges.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("id", 0);
            hashMap.put("orderNum", "无数据");
            hashMap.put("money", "无数据");
            hashMap.put("account", "无数据");;
            hashMap.put("type", "无数据");
            hashMap.put("time", "无数据");
            hashMap.put("state", "无数据");
            list.add(hashMap);
            PageInfo pageInfo = new PageInfo(list);
            return LayuiTableJson.success().addCount(0).addData(pageInfo);
        }
        //遍历充值表
        for (Recharge recharge : recharges) {
            //获得用户信息
            User user = userMapper.selectByPrimaryKey(recharge.getPersonId());
            if (user == null) {
                continue;
            }
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("id", recharge.getId());
            hashMap.put("orderNum", recharge.getOrderNumber());
            hashMap.put("account", user.getAccount());
            hashMap.put("money", recharge.getMoney());
            hashMap.put("type", recharge.getChargeType());
            hashMap.put("time", DateUtil.formatDateTime(recharge.getCreateTime()));
            hashMap.put("state", recharge.getState());
            list.add(hashMap);
        }
        if (list.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("id", 0);
            hashMap.put("orderNum", 0);
            hashMap.put("money", 0);
            hashMap.put("account", 0);;
            hashMap.put("type", 0);
            hashMap.put("time", 0);
            hashMap.put("state", 0);
            list.add(hashMap);
            PageInfo pageInfo = new PageInfo(list);
            return LayuiTableJson.success().addCount(0).addData(pageInfo);
        }
        PageInfo pageInfo = new PageInfo(list);
        return LayuiTableJson.success().addCount(getRechargeTotalCount()).addData(pageInfo);
    }

    /**
     * 获取所有充值记录总条数
     *
     * @return
     */
    @Override
    public int getRechargeTotalCount() {
        int count = 0;
        List<Recharge> recharges = rechargeMapper.selectByExample(null);
        if (recharges.isEmpty()) {
            return count;
        }
        for (Recharge recharge : recharges) {
            User user = userMapper.selectByPrimaryKey(recharge.getPersonId());
            if (user == null) {
                continue;
            }
            count++;
        }
        return count;
    }

    /**
     * 审核通过
     *
     * @param page
     * @param limit
     * @param id    充值记录的id
     * @param type  审核类型，0不通过，1通过
     * @return
     */
    @Override
    public LayuiTableJson checkPassById(int page, int limit, int id, int type) {
        Recharge recharge = new Recharge();
        recharge.setId(id);
        recharge.setUpdateTime(new Date());
        //审核通过
        if (type == 1) {
            recharge.setState(1);
            rechargeMapper.updateByPrimaryKeySelective(recharge);
            return listRecharge(page, limit);
        }
        //审核不通过
        recharge.setState(0);
        rechargeMapper.updateByPrimaryKeySelective(recharge);
        return listRecharge(page, limit);
    }

}
