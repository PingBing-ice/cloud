package com.springcloud.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Log4j2
public class OrderZkController {
    private final static String INVOKE_URL = "http://cloud-provider-payment";


    @Resource
    RestTemplate restTemplate;

        @GetMapping("/consumer/payment/zk")
    public String PaymentInFo() {
        String result = restTemplate.getForObject(INVOKE_URL + "/payment/zk", String.class);
        log.info("客服端OrderZk80调用服务端payment8004(zookeeper) result==>{}",result);
        return result;
    }
}
