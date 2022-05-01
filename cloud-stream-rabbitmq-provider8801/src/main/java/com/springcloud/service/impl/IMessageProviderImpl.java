package com.springcloud.service.impl;

import com.springcloud.service.IMessageProvider;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

//@Service
@Log4j2
@EnableBinding(Source.class) // 定义消息的通道
public class IMessageProviderImpl implements IMessageProvider {
    @Resource
    private MessageChannel output; // 消息发送管道

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        this.output.send(MessageBuilder.withPayload(serial).build());  // 创建并发送消息
        log.info("serial =====>  {}",serial);
        return serial;
    }
}
