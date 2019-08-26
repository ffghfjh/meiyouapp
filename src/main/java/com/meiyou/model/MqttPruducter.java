package com.meiyou.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meiyou.mapper.RootMessageMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.pojo.RootMessageExample;
import com.meiyou.pojo.User;
import com.meiyou.pojo.UserExample;
import com.meiyou.utils.ConnectionOptionWrapper;
import com.meiyou.utils.Constants;
import com.meiyou.utils.MqttConstants;
import com.meiyou.utils.MqttMessageFactory;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;
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
 * @create: 2019-08-24 14:37
 **/
public class MqttPruducter implements Serializable {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RootMessageMapper rootMessageMapper;
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
    String clientId = "GID_video_group@@@producter";
    String p2pClient = "rtc/p2p/GID_video_group@@@";
    /**
     * MQ4IOT 消息的一级 topic，需要在控制台申请才能使用。
     * 如果使用了没有申请或者没有被授权的 topic 会导致鉴权失败，服务端会断开客户端连接。
     */
    final String parentTopic = "rtc";
    final String childTopic = "videoChat";
    /**
     * MQ4IOT支持子级 topic，用来做自定义的过滤，此处为示意，可以填写任何字符串，具体参考https://help.aliyun.com/document_detail/42420.html?spm=a2c4g.11186623.6.544.1ea529cfAO5zV3
     * 需要注意的是，完整的 topic 长度不得超过128个字符。
     */
    final String topic = parentTopic+"/"+childTopic;//需要订阅的话题
    /**
     * MQ4IOT 消息的一级 topic，需要在控制台申请才能使用。
     * 如果使用了没有申请或者没有被授权的 topic 会导致鉴权失败，服务端会断开客户端连接。
     */
    MqttClient mqttClient;


    @PostConstruct
    public void start(){
        System.out.println("生产者启动");
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

            mqttClient.setCallback(new MqttCallbackExtended() {
                @Override
                public void connectComplete(boolean reconnect, String serverURI) {

                    /**
                     * 客户端连接成功后就需要尽快订阅需要的 topic
                     */
                    System.out.println("mqtt服务连接成功");
                }

                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("连接断开");
                    cause.printStackTrace();
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {



                }
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });

            //连接mqtt服务
            mqttClient.connect(connectionOptionWrapper.getMqttConnectOptions());

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


    /**
     * 查询发送者余额
     * @param sender
     * @return
     */
    public boolean authSenderMoey(String sender){
        RootMessageExample example = new RootMessageExample();
        RootMessageExample.Criteria criteriaRoot = example.createCriteria();
        criteriaRoot.andNameEqualTo("video_money");
        float videoMoney = Float.parseFloat(rootMessageMapper.selectByExample(example).get(0).getValue());
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andAccountEqualTo(sender);
        User user = userMapper.selectByExample(userExample).get(0);
        if(videoMoney>user.getMoney()){
            return false;  //余额不足
        }else{
            return true;
        }
    }


    /**
     * 发送消息
     * @param chatType
     * @param msgType
     * @param sender
     * @param reiver
     * @param topic
     * @param info
     */
    public void sendMessage(int chatType, int msgType, String sender, String reiver, String topic, AliRtcAuthInfo info){
        System.out.println("生产者发送消息");
        MqttMessageFactory factory = new MqttMessageFactory(chatType,msgType,sender,reiver,info);
        JSONObject object = factory.getJsonObject();
        final MqttMessage toClientMessage = new MqttMessage(object.toJSONString().getBytes());

        try {
            mqttClient.publish(topic,toClientMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}