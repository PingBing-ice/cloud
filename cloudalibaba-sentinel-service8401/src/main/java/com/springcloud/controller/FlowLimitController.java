package com.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Log4j2
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA() {
        log.info(Thread.currentThread().getName() + "TestA===================");
        return "------testA";
    }

    @GetMapping("/testB")
    public String testB() {
        return "------testB";
    }

    @GetMapping("/testD")
    public String testD() {
        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("testD 测试RT");
        return "------testD";
    }

    @GetMapping("/host")
    @SentinelResource(value = "host", blockHandler = "del_host") //设置 兜底方法
    public String testHostKey(@RequestParam(value = "p1", required = false) String p1,  // required 表示非必要填
                              @RequestParam(value = "p2", required = false) String p2) {
        if (p1 == null || p2 == null) {
            return "======testHostKey";
        }
        return "======testHostKey " + p1 + "\t" + p2;
    }

    public String del_host(String p1, String p2, BlockException ex) {
        log.info(ex);
        return "=========兜底方法del_host/(ㄒoㄒ)/~~"; // sentinel系统默认的提示：Blocked by Sentinel (flow limiting)
    }
}
