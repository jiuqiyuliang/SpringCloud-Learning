package com.liang.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.liang.springcloud.entities.CommonResult;
import com.liang.springcloud.entities.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2021-04-01 19:12
 */
@RestController
public class OrderController {


    /**
     * 查询订单
     * @return
     */
    @GetMapping("/order/{id}")
    @SentinelResource(value = "order")
    public CommonResult<Order> getOrder(@PathVariable("id") Long id){

        Order order = new Order(id, "default");

        return CommonResult.success(order.toString());
    }

    /**
     * 热点限流，查询订单
     * @return
     */
    @GetMapping("/hot_order/{id}/{serial}")
    @SentinelResource(value = "order")
    public CommonResult<Order> getOrder(@PathVariable("id") Long id,@PathVariable(value = "serial",required = false) String serial){

        Order order = new Order(id, serial);

        return CommonResult.success(order.toString());
    }
}