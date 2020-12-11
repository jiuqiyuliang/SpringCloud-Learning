package com.liang.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-11 09:48
 */

@RestController
public class HotKeyController {

    @GetMapping("/testHotKey")
//    @SentinelResource(value = "testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "name",required = false)String name ,@RequestParam(value = "age",required = false)int age){
        return "-----------testHotKey";
    }

    public String deal_testHotKey(String name, int age, BlockException exception){
        return "-------deal_testHotKey，自定义降级方法";
    }
}
