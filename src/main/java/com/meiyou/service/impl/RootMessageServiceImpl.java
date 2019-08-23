package com.meiyou.service.impl;

import com.meiyou.mapper.RootMessageMapper;
import com.meiyou.pojo.RootMessage;
import com.meiyou.pojo.RootMessageExample;
import com.meiyou.service.RootMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.Date;

/**
 * @program: meiyouapp
 * @description:
 * @author: JK
 * @create: 2019-08-21 15:27
 **/
@Service
@CacheConfig(cacheNames = "MeiyouCache")
public class RootMessageServiceImpl implements RootMessageService {
    @Autowired
    private RootMessageMapper rootMessageMapper;

    /**
     * @Description: 查询所有动态数据
     * @Author: JK
     * @Date: 2019/8/22
     */
    @Override
    public List<RootMessage> select() {
        RootMessageExample rootMessageExample = new RootMessageExample();
        //设置查询条件，设置系统动态数据表中 name = sincerity_money
        rootMessageExample.createCriteria().andNameEqualTo("sincerity_money");
        List<RootMessage> list = rootMessageMapper.selectByExample(rootMessageExample);
        return list;
    }

    /**
     * @author ：huangzhaoyang
     * @date ：Created in 2019/8/22 16:47
     * @description：系统参数服务实现层
     * @modified By：huangzhaoyang
     * @version: 1.0.0
     */



    @Override
    public int saveMessage(String name, String value) {
        RootMessage message = new RootMessage();
        message.setName(name);
        message.setValue(value);
        message.setCreateTime(new Date());
        message.setUpdateTime(new Date());
        return rootMessageMapper.insertSelective(message);
    }


    @Override
    @CacheEvict()
    public int removeMessage(int mid) {
        return rootMessageMapper.deleteByPrimaryKey(mid);
    }

    //使用CachePut()注解，确保数据库更新的时候，缓存也更新了
    @CachePut(key = "#mid")
    @Override
    public int updateMessage(int mid, String name, String value) {
        RootMessage message = new RootMessage();
        message.setId(mid);
        message.setName(name);
        message.setValue(value);
        message.setUpdateTime(new Date());
        return rootMessageMapper.updateByPrimaryKeySelective(message);
    }

    @Cacheable()
    @Override
    public String getMessageByName(String name) {
        RootMessageExample example = new RootMessageExample();
        RootMessageExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        List<RootMessage> messages = rootMessageMapper.selectByExample(example);
        if (messages.size() > 0 && messages != null) {
            return messages.get(0).getValue();
        }
        return null;
    }
}
