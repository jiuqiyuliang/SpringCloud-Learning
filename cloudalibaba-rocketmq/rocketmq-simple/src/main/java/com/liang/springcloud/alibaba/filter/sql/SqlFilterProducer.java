package com.liang.springcloud.alibaba.filter.sql;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2021-04-19 15:18
 *
 */
public class SqlFilterProducer {


    public static void main(String[] args) throws Exception{

        //创建一个消息生产者，并设置一个消息生产者组
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");

        //指定 NameServer 地址
        producer.setNamesrvAddr("localhost:9876");

        //初始化 ProducerInOrder，整个应用生命周期内只需要初始化一次
        producer.start();

        String[] tags = new String[] {"TagA", "TagB", "TagC"};
        for (int i = 0; i < 10; i++) {
            //创建一条消息对象，指定其主题、标签和消息内容
            Message message = new Message(
                    "FilterSQLTopic" /* 消息主题名 */,
                    tags[i % tags.length] /* 消息标签 */,
                    ("sync producer Hello Java demo RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* 消息内容 */
            );

            message.putUserProperty("a", String.valueOf(i));

            //发送消息并返回结果
            SendResult sendResult = producer.send(message);

            System.out.printf("%s%n", sendResult);
        }

        // 一旦生产者实例不再被使用则将其关闭，包括清理资源，关闭网络连接等
        producer.shutdown();

    }
}
