package com.SmallThanks.demo.Util;

import com.SmallThanks.demo.mqConsumer.receiveMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class SendMQMessageUtil {

    private final RabbitTemplate rabbitTemplate;

    public SendMQMessageUtil(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendDelayMessage(String message, long ttl) {
        rabbitTemplate.convertAndSend(
                receiveMessage.DELAYED_EXCHANGE_NAME,
                receiveMessage.DELAYED_QUEUE_NAME,
                message,
                msg -> {
                    msg.getMessageProperties().setDelay((int)ttl);
                    msg.getMessageProperties().setExpiration("5");
                    return msg;
                },
                new CorrelationData("123"));
    }
}
