package com.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.springcloud.entities.CommonResult;
import com.springcloud.entities.Payment;

import com.springcloud.service.PaymentServiceSQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class CircleBreakerController {
    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;



    @RequestMapping("/con/{id}")
    @SentinelResource(value = "fallback", fallback = "handlerFallback",  //fallback负责业务异常
            blockHandler = "blockHandler",  //blockHandler负责在sentinel里面配置的降级限流 ,两个都触发了优先听blockHandler
            exceptionsToIgnore = {IllegalArgumentException.class})  // 排除异常
    public CommonResult<Payment> fallback(@PathVariable Long id) {
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);
        if (id == 4) {
            throw new IllegalArgumentException("非法参数异常....");
        } else if (result != null && result.getData() == null) {
            throw new NullPointerException("NullPointerException,该ID没有对应记录");
        }
        return result;
    }

    public CommonResult<Payment> handlerFallback(@PathVariable("id") Long id, Throwable e) {
        Payment payment = new Payment(id, "null");
        return new CommonResult<>(444, "fallback,无此流水,exception  " + e.getMessage(), payment);
    }

    public CommonResult<Payment> blockHandler(@PathVariable("id") Long id, BlockException blockException) {
        Payment payment = new Payment(id, "null");
        return new CommonResult<>(445, "blockHandler-sentinel限流,无此流水: blockException  " + blockException.getMessage(), payment);
    }

    // ====================openFeign
//    @Resource
//    public PaymentServiceSQL paymentServiceSQL;
//    @GetMapping("/consumer/openfeign/{id}")
//    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
//        if (id == 4) {
//            throw new RuntimeException("没有该id");
//        }
//        return paymentServiceSQL.paymentSQL(id);
//    }

}

