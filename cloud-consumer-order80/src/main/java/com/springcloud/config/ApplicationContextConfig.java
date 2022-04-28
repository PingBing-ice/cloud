package com.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 配置类
 */
@Configuration
public class ApplicationContextConfig {
    /**
     * 向容器中注入RestTemplate
     * @return RestTemplate提供了多种便捷访问远程Http服务的方法，
     *  是一种简单便捷的访问restful服务模板类，是Spring提供的用于访问Rest服务的客户端模板工具集
     *
     *  @LoadBalanced 由于客服端访问http://CLOUD-PAYMENT-SERVICE 在注册中心 找不到相对于的服务端 加入这个注解,依次访问服务端
     */
    @Bean
//    @LoadBalanced  // 开启负载均衡的能力  负载均衡: 将请求分散的个服务端口
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
