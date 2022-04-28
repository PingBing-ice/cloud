package com.springCloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Json封装体CommonResult
 */
@Data // set get 方法
@AllArgsConstructor // 有参构造
@NoArgsConstructor // 无参构造
public class CommonResult<T> {
    // 异常类型
    private Integer code;
    // 消息类型
    private String message;
    // 传出去的类型
    private T data;

    public CommonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
