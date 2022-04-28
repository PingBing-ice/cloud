package com.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT",fallback =PaymentServiceImp.class) // value: 服务端在注册中心的名字; fallback : 全局降级的配置的实现类
public interface PaymentService {
    @GetMapping("/payment/ok/{id}")
    String payment_OK(@PathVariable("id") Integer id);


    @GetMapping("/payment/out/{id}")
    String payment_Out(@PathVariable("id") Integer id);

}


