package com.springCloud.service;

import com.springCloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {
    //
    int create(Payment payment);

    // 根据id查找
    Payment getPaymentById(@Param("id") Long id);
}
