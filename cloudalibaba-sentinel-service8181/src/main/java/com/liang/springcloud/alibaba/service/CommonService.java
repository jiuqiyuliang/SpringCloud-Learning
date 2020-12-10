package com.liang.springcloud.alibaba.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-09 16:33
 */

@Service
public class CommonService {


    @SentinelResource(value = "testCommon")
    public void testCommon() {
        System.out.println("-----testCommon");
    }
}
