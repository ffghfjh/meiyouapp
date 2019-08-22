package com.meiyou;

import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Test
    public void contextLoads() {
        System.out.println(new Date().getTime());
    }
}
