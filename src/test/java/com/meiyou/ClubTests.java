package com.meiyou;

import com.meiyou.mapper.ClubBuyMapper;
import com.meiyou.pojo.ClubBuy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @description:
 * @author: Mr.Z
 * @create: 2019-08-22 09:03
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClubTests {

    @Autowired
    ClubBuyMapper clubBuyMapper;

    @Test
    public void contextLoads() {
        ClubBuy clubBuy = new ClubBuy();
        clubBuy.setClubId(7);
        clubBuy.setBuyerId(9);
        int i = clubBuyMapper.insertSelective(clubBuy);
        if(i == 1){
            System.out.println("购买成功!!!");
        }
    }
}
