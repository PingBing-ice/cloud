package com.springcloud.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Log4j2
public class OrderNacosController {


    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String serverUrl;

    @GetMapping("/com/echo/{id}")
    public String paymentInfo(@PathVariable("id") String id) {
        return restTemplate.getForObject(serverUrl + "/echo/" + id, String.class);
    }

}
