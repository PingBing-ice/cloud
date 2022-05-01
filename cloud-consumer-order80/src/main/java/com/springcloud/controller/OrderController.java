package com.springcloud.controller;


import com.netflix.loadbalancer.IRule;
import com.springcloud.entities.CommonResult;
import com.springcloud.entities.Payment;
import com.springcloud.lb.LoadBalancer;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@Log4j2
@RestController
public class OrderController {
//    private final static String PAYMENT_URL = "http://localhost:8001";
    // 通过在eureka上注册过的微服务名称调用 不能写死
    private final static String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private LoadBalancer loadBalancer;
    @Resource
    private DiscoveryClient discoveryClient;
    /**
     * RestTemplate提供了多种便捷访问远程Http服务的方法，
     * 是一种简单便捷的访问restful服务模板类，是Spring提供的用于访问Rest服务的客户端模板工具集
     */
    @Resource
    private RestTemplate restTemplate;

    // ====================> zipkin+sleuth
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin()
    {
        return restTemplate.getForObject("http://localhost:8001"+"/payment/zipkin/", String.class);
    }

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        // 要访问的服务器url //向8001端口发送post请求
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }
    // getForObject: 返回对象为响应体中数据转化成的对象，基本上可以理解为Json
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        // 向8001端口发送get请求
        CommonResult<Payment> forObject = restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if (forObject != null) {
            log.info("用户根据ID请求成功,请求的果为{}", forObject);
        }
        return forObject;
    }
    // getForEntity: 返回对象为ResponseEntity对象，包含了响应中的一些重要信息，比如响应头、响应状态码、响应体等
    @GetMapping("/consumer/payment/getEntity/{id}")
    public CommonResult<Payment> getPaymentEntity(@PathVariable("id")Long id) {
        ResponseEntity<CommonResult> forEntity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if (forEntity.getStatusCode().is2xxSuccessful()) {
            log.info("getForEntity返回的数据为:"+forEntity.getHeaders()+"\t"+forEntity.getStatusCode());
            return forEntity.getBody();
        }else {
            return new CommonResult<>(400, "操作失误");
        }
    }
    @GetMapping("/consumer/payment/postForEntity")
    public CommonResult<Payment> getPostForEntity(Payment payment) {
        ResponseEntity<CommonResult> postForEntity = restTemplate.postForEntity(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
        return postForEntity.getBody();
    }

    @GetMapping("comm/lb")
    public String getPort() {
        // 获取所有的服务端
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() <= 0) {
            return null;
        }
        ServiceInstance instance = loadBalancer.instance(instances);
        URI uri = instance.getUri();
        log.info("url的值为: {}",uri);
        return restTemplate.getForObject(uri + "port", String.class);
    }
}
