package com.liang.springcloud.alibaba.base.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2021-04-14 15:22
 *
 * 异步消息，同步消息，单向消息 - 消费者
 */
public class Consumer {

    public static void main(String[] args) throws InterruptedException, MQClientException {

        //创建一个消息消费者，并设置一个消息消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");

        //指定 NameServer 地址
        consumer.setNamesrvAddr("localhost:9876");

        // Subscribe one more more topics to consume.
        //订阅指定 Topic 下的所有消息
        consumer.subscribe("TopicTest", "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 启动消费者
        consumer.start();

        System.out.println("消息消费者已启动");
    }
}