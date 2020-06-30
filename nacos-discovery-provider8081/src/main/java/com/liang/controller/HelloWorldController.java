package com.liang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-06-29 14:49
 */

@RestController
public class HelloWorldController {

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return "Hello " + name + " , Welcome to Nacos Discovery!!!";
    }
}
