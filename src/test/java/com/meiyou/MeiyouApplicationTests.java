package com.meiyou;

import com.meiyou.config.QueueConfig;
import com.meiyou.mapper.ClubMapper;
import com.meiyou.mapper.RootMessageMapper;
import com.meiyou.model.ExpirationMessagePostProcessor;
import com.meiyou.model.ProcessReceiver;
import com.meiyou.pojo.Club;
import com.meiyou.pojo.ClubExample;
import com.meiyou.pojo.RootMessage;
import com.meiyou.pojo.RootMessageExample;
import com.meiyou.service.impl.BaseServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeiyouApplicationTests extends BaseServiceImpl {


    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    public void contextLoads() {

    }

}
