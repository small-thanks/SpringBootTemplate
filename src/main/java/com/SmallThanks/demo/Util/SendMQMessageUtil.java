package com.SmallThanks.demo.Util;

import com.SmallThanks.demo.config.DelayedQueueConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class SendMQMessageUtil {

    private final RabbitTemplate rabbitTemplate;

    public SendMQMessageUtil(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendDelayMessage(String message, long ttl) {
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME,
                DelayedQueueConfig.DELAYED_QUEUE_NAME,
                message,
                msg -> {
                    msg.getMessageProperties().setDelay((int)ttl);
                    return msg;
                });
    }
}
