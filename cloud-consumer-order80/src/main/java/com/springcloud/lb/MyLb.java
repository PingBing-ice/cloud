package com.springcloud.lb;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Log4j2
public class MyLb implements LoadBalancer {

    private final AtomicInteger integer = new AtomicInteger(0);

    public final int getAndIncrement() {
        int current;
        int next;
        do {
            current = this.integer.get();
            next = current >= Integer.MAX_VALUE ? 0 : current+1;
        } while (!this.integer.compareAndSet(current,next));
        log.info("第几次访问,次数next的值为:"+next);
        return next;
    }

    @Override
    public ServiceInstance instance(List<ServiceInstance> instances) {
        int index = getAndIncrement() % instances.size();

        return instances.get(index);
    }
}
