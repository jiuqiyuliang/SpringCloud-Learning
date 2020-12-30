package com.liang.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-20 14:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    public static Integer SUCCESS_CODE = 1;

    public static Integer FAIL_CODE = -1;

    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code,String message){
        this(code,message,null);
    }


    public static <T> CommonResult<T> success(String message){
        return new CommonResult(SUCCESS_CODE,message);
    }

    public static <T> CommonResult<T> failure( String message){
        return new CommonResult(FAIL_CODE,message);
    }

}
