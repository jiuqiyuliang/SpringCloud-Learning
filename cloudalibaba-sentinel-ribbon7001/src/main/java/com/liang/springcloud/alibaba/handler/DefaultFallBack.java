package com.liang.springcloud.alibaba.handler;

import com.liang.springcloud.entities.CommonResult;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-22 16:20
 */
public class DefaultFallBack {

    public static CommonResult customerDefaultFallback(Throwable throwable){
        return new CommonResult<>(444,"兜底业务异常通用的defaultFallback，exception内容" + throwable.getMessage());

    }
}
