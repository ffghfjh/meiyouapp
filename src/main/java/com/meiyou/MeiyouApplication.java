package com.meiyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching //hzy,启用Redis缓存
public class MeiyouApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeiyouApplication.class, args);
    }

}
