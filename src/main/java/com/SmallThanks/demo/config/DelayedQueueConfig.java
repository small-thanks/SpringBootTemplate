package com.SmallThanks.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

/**
 * RabbitMq的延迟队列配置，使用前需给mq安装延迟交换机插件
 * @author smallthanks
 */
@SpringBootConfiguration
public class DelayedQueueConfig {
    public static final String DELAYED_EXCHANGE_NAME = "delayed_exchange";
    public static final String DELAYED_QUEUE_NAME = "delayed_queue";

    /**
     * 声明一个基于插件的交换机
     * 1、交换机的名称
     * 2、交换机的类型，对应安装插件后新增的类型
     * 3、是否持久化
     * 4、是否自动删除
     * 5、其他参数
     *
     * @return 自定义的交换机
     */
    @Bean
    public CustomExchange delayedExchange() {
        HashMap<String, Object> arguments = new HashMap<>();
        // 延迟类型为直接类型
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false, arguments);
    }

    @Bean
    public Queue delayedQueue() {
        return QueueBuilder.durable(DELAYED_QUEUE_NAME).build();
    }

    @Bean
    public Binding delayedQueueBindingDelayedExchange(
            @Qualifier("delayedExchange") Exchange delayedExchange,
            @Qualifier("delayedQueue") Queue delayedQueue) {
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_QUEUE_NAME).noargs();

    }
}
