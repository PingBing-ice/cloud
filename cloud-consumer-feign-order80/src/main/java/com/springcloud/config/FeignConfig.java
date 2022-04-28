package com.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    /**
     * 开启日志打印功能
     * @return  记录请求和响应的标头、正文和元数据。
     */
    @Bean
    Logger.Level feignLogConfig() {
        return Logger.Level.FULL;
    }
}
