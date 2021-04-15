package com.liang.springcloud.alibaba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2021-04-14 17:43
 */

@Service
public class ProviderService {

    @Autowired
    private MessageChannel output;

    public void send(String message){
        output.send(MessageBuilder.withPayload(message).build());
    }
}
