package com.meiyou;

import com.meiyou.mapper.RootMessageMapper;
import com.meiyou.pojo.RootMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.MessageDigest;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/24 09:04
 * @description：系统参数测试类
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RootMessageTest {

    @Autowired
    RootMessageMapper rootMessageMapper;

    @Test
    public void updateMessageByName () {
        RootMessage message = new RootMessage();
        message.setName("test");
        message.setValue("888");
        int i = rootMessageMapper.updateMessageByName(message);
        System.out.println("测试结果" + i);
    }

}
