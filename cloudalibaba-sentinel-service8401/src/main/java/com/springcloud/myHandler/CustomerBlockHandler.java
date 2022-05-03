package com.springcloud.myHandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.springcloud.entities.CommonResult;

/**
 * 全局兜底方法
 */
public class CustomerBlockHandler {
    public static CommonResult<String> handleException1(BlockException ex) {
         return new CommonResult<String>(200, "按客户自定义,global ==========> 1");
    }
    public static CommonResult<String> handleException2(BlockException ex) {
        return new CommonResult<String>(200, "按客户自定义,global ==========> 2");
    }
}
