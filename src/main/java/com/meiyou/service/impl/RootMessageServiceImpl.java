package com.meiyou.service.impl;

import com.meiyou.mapper.RootMessageMapper;
import com.meiyou.pojo.RootMessage;
import com.meiyou.pojo.RootMessageExample;
import com.meiyou.service.RootMessageService;
import com.meiyou.utils.Msg;
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

    @Override
    public Msg saveMessage(String name, String value) {
        RootMessage message = new RootMessage();
        message.setName(name);
        message.setValue(value);
        message.setCreateTime(new Date());
        message.setUpdateTime(new Date());
        int i = rootMessageMapper.insertSelective(message);
        if (i == 0) {
            return Msg.fail();
        }
        return Msg.success();
    }


    @Override
    @CacheEvict()
    public Msg removeMessage(int mid) {
        int i =  rootMessageMapper.deleteByPrimaryKey(mid);
        if (i == 0) {
            return Msg.fail();
        }
        return Msg.success();
    }

    //使用CachePut()注解，确保数据库更新的时候，缓存也更新了
    @CachePut(key = "#mid")
    @Override
    public Msg updateMessageById(int mid, String name, String value) {
        RootMessage message = new RootMessage();
        message.setId(mid);
        message.setName(name);
        message.setValue(value);
        message.setUpdateTime(new Date());
        int i = rootMessageMapper.updateByPrimaryKeySelective(message);
        if (i == 0) {
            return Msg.fail();
        }
        return Msg.success();
    }

    /**
     * 根据参数名修改参数值
     * @param name
     * @param value
     * @return
     */
    @CachePut(key = "#name")
    @Override
    public Msg updateMessageByName(String name, String value) {
        RootMessage message = new RootMessage();

        message.setName(name);
        message.setValue(value);
        message.setUpdateTime(new Date());
        int i = rootMessageMapper.updateMessageByName(message);
        if (i == 0) {
            return Msg.fail();
        }
        return Msg.success();
    }

   // @Cacheable(key = "#name")
    @Override
    public String getMessageByName(String name) {
        RootMessageExample example = new RootMessageExample();
        RootMessageExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        List<RootMessage> messages = rootMessageMapper.selectByExample(example);
        if (messages.size() > 0 && messages != null) {
            return messages.get(0).getValue();
        }
        return "No Root Message Value!";
    }

    //拉取所有系统参数
    @Override
    public Msg listMessage() {

        List<RootMessage> rootMessages = rootMessageMapper.selectByExample(null);
        if (rootMessages.isEmpty()) {
            return Msg.fail();
        }
        Msg msg = new Msg();
        msg.setCode(100);
        msg.setMsg("拉取所有系统参数成功");
        msg.add("msgList", rootMessages);
        return msg;
    }

}
