package com.meiyou;

import com.meiyou.config.QueueConfig;
import com.meiyou.mapper.ClubBuyMapper;
import com.meiyou.mapper.ClubMapper;
import com.meiyou.mapper.RootMessageMapper;
import com.meiyou.model.ExpirationMessagePostProcessor;
import com.meiyou.model.ProcessReceiver;
import com.meiyou.myEnum.StateEnum;
import com.meiyou.pojo.*;
import com.meiyou.service.impl.BaseServiceImpl;
import io.swagger.annotations.ApiOperation;
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
    ClubBuyMapper clubBuyMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    public void contextLoads() {
//        int clubBuyId = 1;
//        ClubBuy clubBuy = new ClubBuy();
//        clubBuy.setId(clubBuyId);
//        clubBuy.setState(StateEnum.DELETE.getValue());
//        clubBuy.setUpdateTime(new Date());
//        clubBuyMapper.updateByPrimaryKeySelective(clubBuy);
    }


}
