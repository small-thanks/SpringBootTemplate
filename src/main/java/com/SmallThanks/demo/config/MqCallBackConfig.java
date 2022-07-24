package com.SmallThanks.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqCallBackConfig implements InitializingBean, RabbitTemplate.ConfirmCallback {

    private final RabbitTemplate rabbitTemplate;

    public MqCallBackConfig(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void afterPropertiesSet() {
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData == null ? "" : correlationData.getId();
        if (ack) {
            log.info("交换机成功接受了消息id{}", id);
        } else {
            log.info("交换机没有接受了消息id{}，原因是{}", id, cause);
        }
    }
}
