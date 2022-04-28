package com.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable /*序列化接口*/{
    private Long id;
    private String serial;
}

