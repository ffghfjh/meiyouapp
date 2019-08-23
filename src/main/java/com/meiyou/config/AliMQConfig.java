package com.meiyou.config;


import com.meiyou.model.ProcessReceiver;
import com.meiyou.utils.Constants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
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

    /**
     * TTL配置在消息上的消费队列
     * @return
     */
    @Bean
    Queue delayQueuePerMessageTTL() {
        return QueueBuilder.durable(Constants.DELAY_QUEUE_PER_MESSAGE_TTL_NAME)
                .withArgument("x-dead-letter-exchange", Constants.DELAY_EXCHANGE_NAME) // DLX，dead letter发送到的exchange
                .withArgument("x-dead-letter-routing-key", Constants.DELAY_PROCESS_QUEUE_NAME) // dead letter携带的routing key
                .build();
    }


    /**
     * TTL配置在队列上的消费队列
     * @return
     */
    @Bean
    Queue delayQueuePerQueueTTL() {
        return QueueBuilder.durable(Constants.DELAY_QUEUE_PER_QUEUE_TTL_NAME)
                .withArgument("x-dead-letter-exchange", Constants.DELAY_EXCHANGE_NAME) // DLX
                .withArgument("x-dead-letter-routing-key", Constants.DELAY_PROCESS_QUEUE_NAME) // dead letter携带的routing key (实际消费队列)
                .withArgument("x-message-ttl", Constants.QUEUE_EXPIRATION) // 设置队列的过期时间
                .build();
    }


    /**
     * 实际消费队列
     * @return
     */
    @Bean
    Queue delayProcessQueue() {
        return QueueBuilder.durable(Constants.DELAY_PROCESS_QUEUE_NAME)
                .build();
    }

    /**
     * 死信交换机
     * @return
     */
    @Bean
    DirectExchange delayExchange() {
        return new DirectExchange(Constants.DELAY_EXCHANGE_NAME);
    }

    /**
     * 死新交换机绑定实际消费队列
     * @param delayProcessQueue
     * @param delayExchange
     * @return
     */
    @Bean
    Binding dlxBinding(Queue delayProcessQueue, DirectExchange delayExchange) {
        return BindingBuilder.bind(delayProcessQueue)
                .to(delayExchange)
                .with(Constants.DELAY_PROCESS_QUEUE_NAME);
    }

    /**
     * 监听容器 存放消费者
     * @param connectionFactory
     * @param processReceiver
     * @return
     */
    @Bean
    SimpleMessageListenerContainer processContainer(ConnectionFactory connectionFactory, ProcessReceiver processReceiver) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(Constants.DELAY_PROCESS_QUEUE_NAME); // 监听delay_process_queue
        container.setMessageListener(new MessageListenerAdapter(processReceiver));
        return container;
    }

}
