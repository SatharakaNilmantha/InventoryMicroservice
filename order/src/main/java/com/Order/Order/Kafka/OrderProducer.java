package com.Order.Order.Kafka;

import com.Base.Base.Dto.OrderEventDto;
import org.apache.kafka.clients.admin.NewTopic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final NewTopic orderTopic ;

    private final KafkaTemplate<String,OrderEventDto> kafkaTemplate ;


    public OrderProducer(NewTopic orderTopic, KafkaTemplate<String, OrderEventDto> kafkaTemplate) {
        this.orderTopic = orderTopic;
        this.kafkaTemplate = kafkaTemplate;
    }
    private static final Logger logger = LoggerFactory.getLogger(OrderEventDto.class);


    public void sendMessage (OrderEventDto orderEventDto){


        logger.info("Sent OrderEvent to topic={} for orderEvent={}", orderTopic.name(), orderEventDto);

        Message<OrderEventDto> message = MessageBuilder
                .withPayload(orderEventDto)
                .setHeader(KafkaHeaders.TOPIC ,orderTopic.name())
                .build();

        kafkaTemplate.send(message);
    }
}
