package com.myRule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySelfRule {

    /**
     * @return  从所有活着的服务器中随机选择
     */
    @Bean
    public RandomRule randomRule() {
        return new RandomRule();
    }
//    IRule
//    public RoundRobinRule roundRobinRule() {
//
//    }
}
