package com.meiyou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.meiyou.mapper")
public class MeiyouApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeiyouApplication.class, args);
    }

}
