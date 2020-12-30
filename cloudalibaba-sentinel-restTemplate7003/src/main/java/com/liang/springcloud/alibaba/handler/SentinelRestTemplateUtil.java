package com.liang.springcloud.alibaba.handler;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.liang.springcloud.entities.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;

/**
 * @PROJECT_NAME: SpringCloud-Learning
 * @USER: yuliang
 * @DESCRIPTION:
 * @DATE: 2020-12-29 14:32
 */

@Slf4j
public class SentinelRestTemplateUtil {

    public static class FallbackUtil {
        /* handle degrade isDegradeFailure 降级  */
//         com.alibaba.cloud.sentinel.custom.SentinelProtectInterceptor.handleBlockException(HttpRequest, byte[], ClientHttpRequestExecution, BlockException)
        public static SentinelClientHttpResponse fallback(HttpRequest request,
                                                          byte[] body, ClientHttpRequestExecution execution, BlockException ex) {
           log.error("RestTemplate call url={}, 已触发降级", request.getURI(), ex);

            return new SentinelClientHttpResponse(JSON.toJSONString(CommonResult.failure(ex.toString())));
        }
    }

    public static class BlockHandlerUtil {
        /* handle flow 限流  */
        // com.alibaba.cloud.sentinel.custom.SentinelProtectInterceptor.handleBlockException(HttpRequest, byte[], ClientHttpRequestExecution, BlockException)
        public static SentinelClientHttpResponse blockHandler(HttpRequest request,
                                                              byte[] body, ClientHttpRequestExecution execution, BlockException ex) {
            log.error("RestTemplate call url=:{},已触发限流", request.getURI(), ex);
            return new SentinelClientHttpResponse(JSON.toJSONString(CommonResult.failure(ex.toString())));
        }
    }

    public static class UrlCleanerUtil {
        /* Url清洗 hostWithPathResource */
//         com.alibaba.cloud.sentinel.custom.SentinelProtectInterceptor.intercept(HttpRequest, byte[], ClientHttpRequestExecution)
        public static String urlCleaner(String hostWithPathResource) {
            log.info("RestTemplate clean hostWithPathResource={}" , hostWithPathResource);
            if (StringUtils.isEmpty(hostWithPathResource)){   //判断字符是否为空
                return hostWithPathResource;
            }
            if(hostWithPathResource.contains("/order/")){   //判断URL中是不是包含以/order/路径
                return "/order/*";
            }
            return hostWithPathResource;
        }

    }
}