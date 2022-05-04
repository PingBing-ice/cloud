package com.springcloud.service.Impl;

import com.springcloud.dao.OrderDao;
import com.springcloud.domain.Order;
import com.springcloud.service.AccountService;
import com.springcloud.service.OrderService;
import com.springcloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private AccountService accountService;
    @Resource
    private StorageService storageService;

    /**
     * 创建订单->调用库存服务扣减库存->调用账户服务扣减账户余额->修改订单状态
     * 简单说：
     * 下订单->减库存->减余额->改状态
     */
    @Override
    @GlobalTransactional(name = "create-order",rollbackFor = Exception.class) // rollbackFor 添加异常,出现异常进行回滚
    public void create(Order order) {
        log.info("========> 开始创建订单");
        orderDao.create(order);
        log.info("===========> 订单微服务开始调用库存,做扣减");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("===========> 订单微服务开始调用账户,做扣减");
        accountService.decrease(order.getUserId(), order.getMoney());

        // 修该订单状态 从0 到1 ,1表示已经完成
        log.info("=====> 修改订单状态开始");
        orderDao.update(order.getUserId(),0);
        log.info("=====> 修改订单状态结束");

        log.info("=======<下订单结束(*^_^*)>=======");
    }
}
