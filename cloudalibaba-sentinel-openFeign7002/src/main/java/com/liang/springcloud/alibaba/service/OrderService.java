package com.liang.springcloud.alibaba.service;

import com.liang.springcloud.alibaba.service.impl.OrderFallbackService;
import com.liang.springcloud.entities.CommonResult;
import com.liang.springcloud.entities.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-22 10:49
 */
@FeignClient(value = "nacos-order-provider")
//@FeignClient(value = "nacos-order-provider",fallback = OrderFallbackService.class)
@Component
public interface OrderService {

    @GetMapping("/order/{id}")
    public CommonResult<Order> getOrder(@PathVariable("id") Long id);
}
