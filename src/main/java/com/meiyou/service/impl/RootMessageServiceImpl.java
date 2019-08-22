package com.meiyou.service.impl;

import com.meiyou.mapper.RootMessageMapper;
import com.meiyou.pojo.RootMessage;
import com.meiyou.pojo.RootMessageExample;
import com.meiyou.service.RootMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/22 16:47
 * @description：系统参数服务实现层
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Service
@CacheConfig(cacheNames = "MeiyouCache")
public class RootMessageServiceImpl implements RootMessageService {

    @Autowired
    RootMessageMapper rootMessageMapper;

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
        if (messages.size()>0 && messages!=null) {
            return messages.get(0).getValue();
        }
        return null;
    }
}
