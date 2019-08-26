package com.meiyou.utils;

import com.meiyou.mapper.RootMessageMapper;
import com.meiyou.pojo.RootMessage;
import com.meiyou.pojo.RootMessageExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: meiyou
 * @description: 约会公共类
 * @author: JK
 * @create: 2019-08-23 08:42
 **/
@Component
public class RootMessageUtil {
    @Autowired
    private RootMessageMapper rootMessageMapper;

    public int getRootMessage(String rootMessageName){
        RootMessageExample rootMessageExample = new RootMessageExample();
        rootMessageExample.createCriteria().andNameEqualTo(rootMessageName);
        //查询系统动态数据表中所有的数据
        List<RootMessage> list = rootMessageMapper.selectByExample(rootMessageExample);
        //根据传进来的名称获取系统动态数据表中的名称
        RootMessage rootMessage = list.get(0);

        //获取系统动态数据表中的名称获取对应的金额
        String rootMessageValue = rootMessage.getValue();
        //将金额从String转换成Integer
        Integer rootMessageValue1=Integer.parseInt(rootMessageValue);
        return rootMessageValue1;
    }
}
