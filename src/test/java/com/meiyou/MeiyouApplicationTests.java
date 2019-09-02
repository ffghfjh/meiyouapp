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
    RootMessageMapper mapper;

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    public void contextLoads() {

    }

    @Autowired
    ClubMapper clubMapper;

    @Test
    public void nu(){
        //List<Integer> list = new ArrayList<>();
//        RootMessageExample rootMessageExample = new RootMessageExample();
//        rootMessageExample.createCriteria().andNameEqualTo("ssss");
//        List<RootMessage> rootMessages = mapper.selectByExample(rootMessageExample);
        //boolean empty = list.isEmpty();

        ClubExample example = new ClubExample();
        example.createCriteria().andIdEqualTo(100);
        List<Club> result = clubMapper.selectByExample(example);
        System.out.println(result);
    }


    @Test
    public void can(){
//        Calendar calendar=Calendar.getInstance();
//            calendar.setTime(new Date());
//            System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//今天的日期
//            calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);//让日期加1
//            System.out.println(calendar.get(Calendar.DATE));//加1之后的日期Top

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Club club = clubMapper.selectByPrimaryKey(56);
        Date createTime = club.getCreateTime();
        Date outTime = club.getOutTime();

        long cha = outTime.getTime()-createTime.getTime();
        long days = cha/(1000*60*60*24);
        System.out.println(createTime);
        System.out.println(outTime);
        System.out.println(createTime.getTime());
        System.out.println(outTime.getTime());
        System.out.println("cha:"+cha);
        System.out.println("days:"+days);

        long hours = (cha-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
        long minutes = (cha-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
        System.out.println(""+days+"天"+hours+"小时"+minutes+"分");

    }
}
