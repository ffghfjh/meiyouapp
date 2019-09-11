package com.meiyou;

import com.meiyou.config.QueueConfig;
import com.meiyou.mapper.ClubBuyMapper;
import com.meiyou.mapper.ClubMapper;
import com.meiyou.mapper.RootMessageMapper;
import com.meiyou.model.AppointmentVo1;
import com.meiyou.model.ClubVO;
import com.meiyou.model.ExpirationMessagePostProcessor;
import com.meiyou.model.ProcessReceiver;
import com.meiyou.myEnum.StateEnum;
import com.meiyou.pojo.*;
import com.meiyou.service.impl.BaseServiceImpl;
import com.meiyou.utils.Msg;
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
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeiyouApplicationTests extends BaseServiceImpl {

    @Autowired
    ClubBuyMapper clubBuyMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    public void contextLoads() {

//        ClubVO clubVO1 = new ClubVO();
//        clubVO1.setClubId(3);
//        clubVO1.setProjectDesc("一号");
//
//        ClubVO clubVO2 = new ClubVO();
//        clubVO2.setClubId(9);
//        clubVO2.setProjectDesc("二号");
//
//        ClubVO clubVO3 = new ClubVO();
//        clubVO3.setClubId(1);
//        clubVO3.setProjectDesc("三号");
//
//        List<ClubVO> clubVOS = new ArrayList<>();
//        clubVOS.add(clubVO1);
//        clubVOS.add(clubVO2);
//        clubVOS.add(clubVO3);
//        System.out.println(clubVOS);
//        clubVOS.stream().sorted((ClubVO1, ClubVO2) -> ClubVO1.getClubId().compareTo(ClubVO2.getClubId())).collect(Collectors.toList());

//        for (int i = 0; i < clubVOS .size(); i++)    {
//            for (int j = clubVOS .size()-1; j > i; j--)  {
//                Long time= clubVOS .get(j).getCreateTime().getTime();
//                Long time1= clubVOS .get(j-1).getCreateTime().getTime();
//                if (time.compareTo(time1)>0)    {
//                    //互换位置
//                    ClubVO clubVO = clubVOS.get(j);
//                    clubVOS.set(j, clubVOS.get(j-1));
//                    clubVOS.set(j-1, clubVO );
//                }
//            }
//        }

//        System.out.println(clubVOS);
    }


}
