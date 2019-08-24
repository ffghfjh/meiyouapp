package com.meiyou.model;

import com.meiyou.listener.AliMQConsumerListener;
import com.meiyou.utils.ConnectionOptionWrapper;
import com.meiyou.utils.Constants;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-24 09:14
 **/
public class MqttComsumer {
    /**
            * MQ4IOT 实例 ID，购买后控制台获取
     */
    String instanceId = "post-cn-45915r0fw1h";
    /**
     * 接入点地址，购买 MQ4IOT 实例，且配置完成后即可获取，接入点地址必须填写分配的域名，不得使用 IP 地址直接连接，否则可能会导致客户端异常。
     */
    String endPoint = "post-cn-45915r0fw1h.mqtt.aliyuncs.com";
    /**
     * 账号 accesskey，从账号系统控制台获取
     */
    String accessKey = Constants.ACCESSKEYID;
    /**
     * 账号 secretKey，从账号系统控制台获取，仅在Signature鉴权模式下需要设置
     */
    String secretKey = Constants.ACCESSKEYSERECT;
    /**
     * MQ4IOT clientId，由业务系统分配，需要保证每个 tcp 连接都不一样，保证全局唯一，如果不同的客户端对象（tcp 连接）使用了相同的 clientId 会导致连接异常断开。
     * clientId 由两部分组成，格式为 GroupID@@@DeviceId，其中 groupId 在 MQ4IOT 控制台申请，DeviceId 由业务方自己设置，clientId 总长度不得超过64个字符。
     */
    String clientId = "GID_video_group@@@server_manager";
    /**
     * MQ4IOT 消息的一级 topic，需要在控制台申请才能使用。
     * 如果使用了没有申请或者没有被授权的 topic 会导致鉴权失败，服务端会断开客户端连接。
     */
    MqttClient mqttClient;

    @PostConstruct
    public void start(){
        System.out.println("消费者启动");
        /**
         * QoS参数代表传输质量，可选0，1，2，根据实际需求合理设置，具体参考 https://help.aliyun.com/document_detail/42420.html?spm=a2c4g.11186623.6.544.1ea529cfAO5zV3
         */
        final int qosLevel = 1;
        try {
            final ConnectionOptionWrapper connectionOptionWrapper = new ConnectionOptionWrapper(instanceId, accessKey, secretKey, clientId);
            final MemoryPersistence memoryPersistence = new MemoryPersistence();
            /**
             * 客户端使用的协议和端口必须匹配，具体参考文档 https://help.aliyun.com/document_detail/44866.html?spm=a2c4g.11186623.6.552.25302386RcuYFB
             * 如果是 SSL 加密则设置ssl://endpoint:8883
             */
            mqttClient = new MqttClient("tcp://" + endPoint + ":1883", clientId, memoryPersistence);
            /**
             *  客户端设置好发送超时时间，防止无限阻塞
             */
            mqttClient.setTimeToWait(20000);
            final ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());

            mqttClient.setCallback(new AliMQConsumerListener());//监听

            mqttClient.connect();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void destroy() {
        System.out.println("消费者销毁");
    }
}