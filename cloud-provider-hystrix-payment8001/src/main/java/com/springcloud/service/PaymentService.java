package com.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id) {
        return "线性池\t" + Thread.currentThread().getName() + "\tpaymentInfo_OK\t" + "id值为:" + id + "\tO(∩_∩)O";
    }

    // 服务降级,当访问的时间超过3秒或者报错,调用 paymentInfo_TimeOutHandle 方法
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id) {
        int TimeOut = 0;
        try {
            TimeUnit.SECONDS.sleep(TimeOut);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "线性池\t" + Thread.currentThread().getName() + "\tpaymentInfo_TimeOut,超时时间\t" +
                TimeOut + "\t" + "id值为:" + id + "\tO(∩_∩)O";
    }

    public String paymentInfo_TimeOutHandler(Integer id) {

        return "线性池\t" + Thread.currentThread().getName() + "\tpaymentInfo_TimeOutHandler\t系统繁忙,请稍后再试" +
                "\tid值为:" + id + "\t/(ㄒoㄒ)/~~";
    }



    //=========服务熔断===========================================================================================================
    // 这些配置在 HystrixCommandProperties
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),  // 开启服务熔断
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),  // 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"), // 失败率达到多少跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id)
    {
        if(id < 0)
        {
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id)
    {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }
}
