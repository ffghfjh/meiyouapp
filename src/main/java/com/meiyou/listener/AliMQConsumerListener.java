package com.meiyou.listener;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-24 09:18
 **/
public class AliMQConsumerListener implements MqttCallback {


    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("收到消息");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}