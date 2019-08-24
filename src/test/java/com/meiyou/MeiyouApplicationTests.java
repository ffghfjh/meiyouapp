package com.meiyou;

import com.meiyou.config.QueueConfig;
import com.meiyou.model.ExpirationMessagePostProcessor;
import com.meiyou.model.ProcessReceiver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeiyouApplicationTests {


    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    public void contextLoads() {

    }


    @Test
    public void testDelayQueuePerMessageTTL() throws InterruptedException {
        ProcessReceiver.latch = new CountDownLatch(3);
        for (int i = 1; i <= 3; i++) {
            long expiration = i * 4000;
            rabbitTemplate.convertAndSend(QueueConfig.DELAY_QUEUE_PER_MESSAGE_TTL_NAME,
                    (Object) ("Message From delay_queue_per_message_ttl with expiration " + expiration), new ExpirationMessagePostProcessor(expiration));
        }
        ProcessReceiver.latch.await();
    }

}
