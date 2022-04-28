package com.springCloud.dao;

import com.springCloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * dao接口
 */
@Mapper
public interface PaymentDao {
    //
    int create(Payment payment);

    // 根据id查找
    Payment getPaymentById(@Param("id") Long id);
}
