package com.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {
    @Value("${server.port}")
    private String port;


    @GetMapping(value = "/echo/{id}")
    public String echo(@PathVariable("id")String id) {
        return "Hello Nacos Discovery\t"+id +"\tport:"+port;
    }
}
