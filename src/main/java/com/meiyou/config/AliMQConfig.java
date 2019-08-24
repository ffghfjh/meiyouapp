package com.meiyou.config;

import com.meiyou.model.MqttComsumer;
import com.meiyou.model.MqttPruducter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-24 08:54
 **/
@Configuration
public class AliMQConfig {

    @Value("${producerId}")
    public String producerId;

    @Value("${consumerId}")
    public String consumerId;

    @Value("${accessKey}")
    public String accessKey;

    @Value("${secretKey}")
    public String secretKey;

    @Value("${topic}")
    public String topic;

    @Value("${tag}")
    public String tag;

    @Value("${onsAddr}")
    public String onsAddr;

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public MqttComsumer getComsumer() {
        return new MqttComsumer();
    }
    @Bean(initMethod = "start", destroyMethod = "destroy")
    public MqttPruducter getProducter(){
        return new MqttPruducter();
    }
}