package com.liang.springcloud.alibaba.controller;

import com.liang.springcloud.entities.CommonResult;
import com.liang.springcloud.entities.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-21 16:30
 */

@RestController
public class OrderController {

    @Value("${server.port}")
    private String serverPort;

    //模拟数据库数据
    public static HashMap<Long, Order> hashMap = new HashMap<>();
    static {
        hashMap.put(1L,new Order(1L,"fdajlfdasl2181"));
        hashMap.put(2L,new Order(2L,"fafadfdafd2182"));
        hashMap.put(3L,new Order(3L,"fdafdafdafd2183"));


    }

    @GetMapping("/order/{id}")
    public CommonResult<Order> getOrder(@PathVariable("id") Long id){
        Order order = hashMap.get(id);

        CommonResult commonResult = new CommonResult(200,"from mysql,serverPort:" + serverPort,order);

        if(id ==5){
            throw new IllegalArgumentException("IllegalArgumentException,非法参数");
        }

        return commonResult;
    }
}
