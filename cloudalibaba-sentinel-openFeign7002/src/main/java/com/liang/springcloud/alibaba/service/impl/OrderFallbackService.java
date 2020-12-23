package com.liang.springcloud.alibaba.service.impl;

import com.liang.springcloud.alibaba.service.OrderService;
import com.liang.springcloud.entities.CommonResult;
import com.liang.springcloud.entities.Order;
import org.springframework.stereotype.Component;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-22 10:51
 */
@Component
public class OrderFallbackService implements OrderService {

    @Override
    public CommonResult<Order> getOrder(Long id) {
        return new CommonResult<>(4444,"服务降级返回，OrderFallbackService",new Order(id,"errorSerial"));
    }
}
