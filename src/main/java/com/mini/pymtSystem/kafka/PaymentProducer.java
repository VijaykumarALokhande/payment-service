package com.mini.pymtSystem.kafka;

import com.mini.pymtSystem.dto.PaymentInitiationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {

    // Spring's Kafka client
    private final KafkaTemplate<String, PaymentInitiationEvent> kafkaTemplate;

    // Constructor Injection
    public PaymentProducer(KafkaTemplate<String, PaymentInitiationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(PaymentInitiationEvent event) {

        // Send event to Kafka topic
        kafkaTemplate.send("vijay-pymt-test", event);
    }

}
