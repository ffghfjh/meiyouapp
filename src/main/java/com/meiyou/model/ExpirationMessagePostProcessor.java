package com.meiyou.model;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

import java.io.Serializable;

/**
 * 设置消息的Header以及消息的属性
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-23 19:21
 **/
public class ExpirationMessagePostProcessor implements MessagePostProcessor, Serializable {

    private final Long ttl; // 毫秒

    public ExpirationMessagePostProcessor(Long ttl) {
        this.ttl = ttl;
    }
    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties()
                .setExpiration(ttl.toString()); // 设置per-message的失效时间
        return message;
    }
}