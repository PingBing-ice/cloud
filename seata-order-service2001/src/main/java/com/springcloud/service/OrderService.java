package com.springcloud.service;


import com.springcloud.domain.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    void create(Order order);
}
