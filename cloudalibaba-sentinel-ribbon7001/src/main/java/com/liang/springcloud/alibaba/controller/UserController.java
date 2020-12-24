package com.liang.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.liang.springcloud.alibaba.handler.CustomerFallback;
import com.liang.springcloud.alibaba.handler.DefaultFallBack;
import com.liang.springcloud.entities.CommonResult;
import com.liang.springcloud.entities.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-21 16:46
 */

@RestController
public class UserController {


    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-order-service}")
    private String serverURL;


    @GetMapping("/consumer/fallback/{id}")
    @SentinelResource(value = "fallback") //没有配置
//    @SentinelResource(value = "fallback",fallback = "handlerFallback") //fallback只负责业务异常
//     @SentinelResource(value = "fallback",fallback = "customerFallback",fallbackClass = CustomerFallback.class) //独立的业务异常处理
//     @SentinelResource(value = "fallback",defaultFallback = "defaultFallback") //通常用于通用的 fallback 逻辑（即可以用于很多服务或方法）
//     @SentinelResource(value = "fallback",defaultFallback = "customerDefaultFallback",fallbackClass = DefaultFallBack.class) //通常用于通用的 fallback 逻辑（即可以用于很多服务或方法）
//    @SentinelResource(value = "fallback",blockHandler = "blockHandler") //blockHandler只负责sentinel控制台配置违规
//    @SentinelResource(value = "fallback",fallback = "handlerFallback",blockHandler = "blockHandler", exceptionsToIgnore = {IllegalArgumentException.class} )  //异常被排除掉,不会进入fallback逻辑中，而是会原样抛出。

    public CommonResult<Order> getOrderInfo(@PathVariable("id")Long id){

        CommonResult<Order> result = restTemplate.getForObject(serverURL + "/order/" + id, CommonResult.class);

        //模拟业务异常
        if(id == 4){
            throw new IllegalArgumentException("IllegalArgumentException,非法参数");
        }else if(result.getData() == null){
            throw new NullPointerException("NullPointerException,该ID没有对应记录");
        }

        return result;
    }


    public CommonResult<Order> handlerFallback(@PathVariable("id")Long id,Throwable throwable){

        Order order = new Order(id, "默认值");
        return new CommonResult<>(444,"兜底业务异常handlerFallback，exception内容" + throwable.getMessage(),order);
    }

    public CommonResult defaultFallback(Throwable throwable){

        return new CommonResult<>(444,"兜底业务异常defaultFallback，exception内容" + throwable.getMessage());
    }


    public CommonResult<Order> blockHandler(@PathVariable("id")Long id, BlockException throwable){

        Order order = new Order(id, "默认值");
        return new CommonResult<>(445,"兜底配置异常blockHandler-sentinel限流异常" +
                "，exception内容" + throwable.getMessage(),order);
    }
}
