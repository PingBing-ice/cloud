package com.springcloud.service;

import com.springcloud.domain.CommonResult;
import com.springcloud.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * 账户
 */
@Service
@FeignClient(value = "steata-account-service")
public interface AccountService {
    // 向库存微服务发送post请求,来根据ID做账户扣减
    @PostMapping(value = "/account/decrease")
    CommonResult<String> decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);
}
