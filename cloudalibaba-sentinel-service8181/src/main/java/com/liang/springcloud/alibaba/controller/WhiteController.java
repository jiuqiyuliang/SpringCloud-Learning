package com.liang.springcloud.alibaba.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-18 16:03
 */
@RestController
public class WhiteController {


    @GetMapping(value = "/white")
    public String hello() {
        return "white Sentinel";
    }
}
