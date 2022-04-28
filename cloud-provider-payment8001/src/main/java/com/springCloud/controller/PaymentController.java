package com.springCloud.controller;


import com.springCloud.entities.CommonResult;
import com.springCloud.entities.Payment;
import com.springCloud.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.plugin.com.DispatchClient;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
@RestController
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")  // 获取 端口号
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public CommonResult<Integer> create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("插入结果:{} serverPot:{}", result, serverPort);
        if (result > 0) {
            return new CommonResult<>(200, "插入数据库成功 serverPort:" + serverPort, result);
        } else {
            return new CommonResult<>(400, "插入数据库失败", null);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment paymentById = paymentService.getPaymentById(id);
        log.info("插入结果:{} serverPot: {}", paymentById, serverPort);
        if (paymentById != null) {
            return new CommonResult<>(200, "查询数据库成功 ,serverPort:" + serverPort, paymentById);
        } else {
            return new CommonResult<>(400, "查询数据库失败" + id, null);
        }
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("服务的消息:{}", service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info("CLOUD-PAYMENT-SERVICE 服务的具体信息: ID:{} Host:{}  Port:{} Url:{} ",
                    instance.getInstanceId(), instance.getHost(), instance.getPort(), instance.getUri());
        }

        return this.discoveryClient;
    }

    @GetMapping("/port")
    public String Port() {
        return serverPort;
    }

    @GetMapping(value = "/feign/timeout")
    public String paymentFeignTimeOut() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return serverPort;
    }
}
