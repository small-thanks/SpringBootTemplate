package com.SmallThanks.demo.controller;

import com.SmallThanks.demo.Util.SendMQMessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class TestController {

    @Autowired
    private SendMQMessageUtil sendMQMessageUtil;

    @GetMapping("/t1")
    public HashMap t1() {
        HashMap<String, Integer> jsonObject = new HashMap<>();
        jsonObject.put("1", 2);
        jsonObject.put("3", 3);
        jsonObject.put("4", 4);
        return jsonObject;
    }
    // 测试发送到mq消息

    @GetMapping("/t2/{message}")
    public void sendMsg(@PathVariable String message) {
        log.info("当前时间：{},发送一条消息到队列中{}", new Date(), message);
        sendMQMessageUtil.sendDelayMessage(message,TimeUnit.SECONDS.toMillis(5));

    }
}
