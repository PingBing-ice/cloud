package com.springcloud.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImp implements PaymentService{ // 全局降级的配置类
    @Override
    public String payment_OK(Integer id) {
        // 改方法报错,和超时调用该方法
        return "服务调用失败，提示来自：cloud-consumer-feign-order80";
    }

    @Override
    public String payment_Out(Integer id) {
        // 改方法报错,和超时调用该方法
        return "服务调用失败，提示来自：cloud-consumer-feign-order80";
    }
}
