package com.springcloud.controller;

import com.springcloud.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Log4j2
public class PaymentController {
    @Resource
    PaymentService paymentService;

    @GetMapping("/payment/ok/{id}")
    public String payment_OK(@PathVariable("id")Integer id) {
        String result = paymentService.paymentInfo_OK(id);
        log.info("=======resultOK:{}========",result);
        return result;
    }
    @GetMapping("/payment/out/{id}")
    public String payment_Out(@PathVariable("id")Integer id) {
        String result = paymentService.paymentInfo_TimeOut(id);
        log.info("=======result:{}========",result);
        return result;
    }
    // ==========服务熔断=======================
    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id)
    {
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("****result: "+result);
        return result;
    }
}
