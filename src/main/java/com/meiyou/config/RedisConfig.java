package com.meiyou.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-21 15:51
 **/
@Configuration
@EnableCaching
public class RedisConfig {


    @Bean
    public RedisTemplate<Integer, String> getIntStringRedisTemplate(RedisConnectionFactory factory){
        RedisTemplate<Integer, String> redisTemplate = new RedisTemplate<Integer, String>();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }


    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(factory);
        return stringRedisTemplate;
    }
}
