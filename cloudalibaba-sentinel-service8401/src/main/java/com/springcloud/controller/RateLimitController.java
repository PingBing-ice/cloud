package com.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import com.springcloud.entities.CommonResult;
import com.springcloud.entities.Payment;
import com.springcloud.myHandler.CustomerBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommonResult<Payment> byResource() {
        return new CommonResult<Payment>(200, "按资源名称限流测试OK", new Payment(2020L, "serial001"));
    }

    public CommonResult<String> handleException(BlockException exception) {
        return new CommonResult<String>(444, exception.getClass().getCanonicalName() + "\t 服务不可用");
    }

    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public CommonResult<Payment> byUrl() {
        return new CommonResult<Payment>(200, "按url限流测试OK", new Payment(2020L, "serial002"));
    }

    @GetMapping("/cbk")
    @SentinelResource(value = "cbk",
            blockHandlerClass = CustomerBlockHandler.class/*扫描兜底类*/,
            blockHandler = "handleException1"/*指定兜底方法*/)
    public CommonResult<String> customerBlockHandler() {
        return new CommonResult<String>(200, "按客户自定义称限流测试OK");
    }
}


