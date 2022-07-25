package com.SmallThanks.demo.mqConsumer;


import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 监听消息队列的消息
 */
@Slf4j
@Component
@EnableRabbit
public class receiveMessage {
    public static final String DELAYED_EXCHANGE_NAME = "delayed_exchange";
    public static final String DELAYED_QUEUE_NAME = "delayed_queue";
    //    @RabbitListener(queues = "${queue.DEAD_LETTER_QUEUE_NAME:dead_letter_queue")
    /**
     * RabbitMq的延迟队列配置，使用前需给mq安装延迟交换机插件
     *
     * @author smallthanks
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = DELAYED_QUEUE_NAME,
                    durable = "true",
                    exclusive = "false",
                    autoDelete = "false"),
            exchange = @Exchange(
                    name = DELAYED_EXCHANGE_NAME,
                    type = "x-delayed-message",
                    arguments = @Argument(
                            name = "x-delayed-type",
                            value = "direct")),
            key = DELAYED_QUEUE_NAME
    ))
    public void receiveDeadQueueMessage(String name, Message message, Channel channel) {
        String s = new String(message.getBody());
        log.info("当前：{},收到一条消息到队列中{}", name, message);
//        try {
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = DELAYED_QUEUE_NAME+1,
                    durable = "true",
                    exclusive = "false",
                    autoDelete = "false"),
            exchange = @Exchange(
                    name = DELAYED_EXCHANGE_NAME+1),
            key = DELAYED_QUEUE_NAME+1
    ))
    public void receiveDeadQueueMessage1(String name, Message message, Channel channel) {
        String s = new String(message.getBody());
        log.info("当前1：{},收到一条消息到队列中{}", name, message);
    }
}
