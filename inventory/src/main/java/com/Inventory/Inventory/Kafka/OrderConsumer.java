package com.Inventory.Inventory.Kafka;

import com.Base.Base.Dto.OrderEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    // 1. Logger fixed (static final, using the correct class)
    private static final Logger logger = LoggerFactory.getLogger(OrderEventDto.class);

    // 2. Annotation syntax fixed (Parentheses, not braces)
    @KafkaListener(
            topics = "${spring.kafka.template.default-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void getMessage(OrderEventDto orderEventDto) {
        // 3. Log message fixed (Consumer receives)
        logger.info("Received OrderEvent: {}", orderEventDto);
    }
}