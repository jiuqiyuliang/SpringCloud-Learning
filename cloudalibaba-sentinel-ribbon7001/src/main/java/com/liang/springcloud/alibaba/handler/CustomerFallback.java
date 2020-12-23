package com.liang.springcloud.alibaba.handler;

import com.liang.springcloud.entities.CommonResult;
import com.liang.springcloud.entities.Order;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-22 16:08
 */

public class CustomerFallback {

    public static CommonResult<Order> customerFallback(@PathVariable("id")Long id, Throwable throwable){
        Order order = new Order(id, "默认值");
        return new CommonResult<>(444,"业务异常访问，按照客户自定义的全局异常" + throwable.getMessage(),order);

    }
}
