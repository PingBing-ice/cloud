package com.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.springcloud.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Log4j2
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod") // 设置全局服务降级配置
public class OrderHystrixController {


    @Resource
    private PaymentService paymentService;

    @GetMapping("/consumer/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        return paymentService.payment_OK(id);
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @GetMapping("/consumer/out/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        int a = 10 / 0;
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return paymentService.payment_Out(id);
    }
    @HystrixCommand // 添加这个是全局降级配置
    @GetMapping("/consumer/bal/{id}")
    public String paymentInfo_TimeOutGlobal(@PathVariable("id") Integer id) {
        int a = 10 / 0;
        return paymentService.payment_Out(id);
    }
    public String paymentInfo_TimeOutHandler(@PathVariable("id") Integer id) {
        return "/(ㄒoㄒ)/调用支付接口超时或异常：\t" + "\t当前线程池名字" + Thread.currentThread().getName();
    }

    // 下面是全局配置
    public String payment_Global_FallbackMethod() {
        return "Global异常处理信息，请稍后再试，/(ㄒoㄒ)/~~";
    }
}
