package com.liang.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.liang.springcloud.alibaba.handler.CustomerBlockHandler;
import com.liang.springcloud.entities.CommonResult;
import com.liang.springcloud.entities.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-20 11:39
 */

@RestController
public class SentinelController {

    /**
     * 按照url地址限流
     * @return
     */
    @GetMapping("/byUrl")
    public CommonResult byUrl(){
        return new CommonResult(200,"按URL限流测试ok",new Order(2020l,"serial002"));
    }


    /**
     * 按照资源限流
     * @return
     */
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource")
    public CommonResult byResource(){
        return new CommonResult(200,"按资源名限流测试ok",new Order(2020l,"serial001"));
    }


    /**
     * 自定义异常
     * @return
     */
    @GetMapping("/byResource_handler/{id}")
    @SentinelResource(value = "byResource_handler",blockHandler = "handlerException")
    public CommonResult byResource_handler(@PathVariable Long id ){
        return new CommonResult(200,"按资源名限流测试ok",new Order(id,"serial001"));
    }

    /**
     * 方法名要同blockHandler属性相同，最后加一个参数，类型为BlockException，其余与原函数一致.
     * @param ex 类型为BlockException
     * @return
     */
    public CommonResult handlerException(Long id,BlockException ex){
        return new CommonResult(444,ex.getClass().getCanonicalName() + "服务不可用");
    }



    /**
     * 使用blockHandlerClass，自定义全局异常
     * @return
     */
    @GetMapping("/customBlockHandler")
    @SentinelResource(value = "customBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class,blockHandler = "handlerException")
    public CommonResult customBlockHandler(){
        return new CommonResult(200,"正常访问，自定义全局异常测试ok",new Order(2020l,"serial003"));
    }

}
