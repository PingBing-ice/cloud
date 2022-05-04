package com.springcloud.service;

import com.springcloud.entities.CommonResult;
import com.springcloud.entities.Payment;

import com.springcloud.service.PaymentServiceSQL;
import org.springframework.stereotype.Service;

//@Service
public class PaymentFallbackService implements PaymentServiceSQL {
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(444,"服务降级返回,没有该流水信息",new Payment(id, "errorSerial......"));
    }
}
