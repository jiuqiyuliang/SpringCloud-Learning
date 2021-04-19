package com.liang.springcloud.alibaba.base.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2021-04-19 19:02
 *
 *
 *  异步消息，同步消息，单向消息 - 消费者 - 广播模式
 */
public class BroadcastConsumer {

    public static void main(String[] args) throws Exception {

        //创建一个消息消费者，并设置一个消息消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");

        //指定 NameServer 地址
        consumer.setNamesrvAddr("localhost:9876");
        //设置广播模式
        consumer.setMessageModel(MessageModel.BROADCASTING);

        //订阅指定 Topic 下的所有消息
        consumer.subscribe("TopicTest", "*");

        // 注册回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
//                System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");

                //默认 list 里只有一条消息，可以通过设置参数来批量接收消息
                if (msgs != null) {
                    for (MessageExt ext : msgs) {
                        try {
                            System.out.println(new Date() + ext.toString() + new String(ext.getBody(), "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.println("消息消费者已启动");
    }
}
