package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableFeignClients // 标志使用OpenFeign
public class OrderMainFeign80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMainFeign80.class, args);

    }
}
