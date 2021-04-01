package com.liang.springcloud.alibaba;

import com.alibaba.csp.sentinel.cluster.server.ClusterTokenServer;
import com.alibaba.csp.sentinel.cluster.server.SentinelDefaultTokenServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2021-04-02 09:53
 */
@SpringBootApplication
public class SentinelAloneServer9092 {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SentinelAloneServer9092.class,args);

        ClusterTokenServer tokenServer = new SentinelDefaultTokenServer();

        tokenServer.start();

    }


}
