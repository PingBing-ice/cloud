package com.springcloud.service;

import com.springcloud.domain.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 库存
 */
@Service
@FeignClient(value = "steata-storage-service")
public interface StorageService {
    // 向库存微服务发送post请求,来根据ID做库存扣减
    @PostMapping(value = "/storage/decrease")
    CommonResult<String> decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
