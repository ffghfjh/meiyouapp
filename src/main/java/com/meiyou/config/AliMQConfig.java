package com.meiyou.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-23 14:28
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

    //超时时间
    @Value("${sendMsgTimeoutMillis}")
    public String sendMsgTimeoutMillis;

    @Value("${suspendTimeMillis}")
    public String suspendTimeMillis;

    @Value("${maxReconsumeTimes}")
    public String maxReconsumeTimes;

    /*@Bean(initMethod = "start", destroyMethod = "shutdown")
    public ProducerBean getProducer() {
        ProducerBean producerBean = new ProducerBean();
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ProducerId, producerId);
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        properties.put(PropertyKeyConst.SendMsgTimeoutMillis, sendMsgTimeoutMillis);
        properties.put(PropertyKeyConst.ONSAddr, onsAddr);
        producerBean.setProperties(properties);
        return producerBean;
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ConsumerBean getConsumer() {
        ConsumerBean consumerBean = new ConsumerBean();
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, consumerId);
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        properties.put(PropertyKeyConst.SuspendTimeMillis, suspendTimeMillis);
        properties.put(PropertyKeyConst.MaxReconsumeTimes, maxReconsumeTimes);
        properties.put(PropertyKeyConst.ONSAddr, onsAddr);
        consumerBean.setProperties(properties);
        Subscription subscription = new Subscription();
        subscription.setTopic(topic);
        subscription.setExpression(tag);
        Map<Subscription, MessageListener> map = new HashMap();
        map.put(subscription, new AliMQConsumerListener());
        consumerBean.setSubscriptionTable(map);
        return consumerBean;
    }*/
}
