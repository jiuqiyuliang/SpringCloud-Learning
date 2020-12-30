package com.liang.springcloud.alibaba.controller;

import com.liang.springcloud.alibaba.service.OrderService;
import com.liang.springcloud.entities.CommonResult;
import com.liang.springcloud.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-22 10:49
 */
@RestController
public class UserController {


    @Resource
    private OrderService orderService;

    @GetMapping("/consumer/openfeign/{id}")
    public CommonResult<Order> fallback(@PathVariable("id")Long id){


        CommonResult<Order> result = orderService.getOrder(id);


        return result;
    }
}
