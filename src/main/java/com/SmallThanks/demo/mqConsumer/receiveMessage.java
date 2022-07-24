package com.SmallThanks.demo.mqConsumer;


import com.SmallThanks.demo.config.DelayedQueueConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 监听消息队列的消息
 */
@Slf4j
@Component
@EnableRabbit
public class receiveMessage {

//    @RabbitListener(queues = "${queue.DEAD_LETTER_QUEUE_NAME:dead_letter_queue")
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDeadQueueMessage(Message message, Channel channel){
        String s = new String(message.getBody());
        log.info("当前时间：{},发送一条消息到队列中{}", new Date(), message);
//        channel.basicAck(message.getMessageProperties().get, false);

    }
}
