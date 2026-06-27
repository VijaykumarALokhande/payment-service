package com.mini.pymtSystem.kafka;

import com.mini.pymtSystem.dto.PaymentEvent;
import com.mini.pymtSystem.dto.PaymentInitiationEvent;
import com.mini.pymtSystem.entity.PymtDetails;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SimulatorProducer {

    // Spring's Kafka client
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    // Constructor Injection
    public SimulatorProducer(KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(PaymentEvent paymentEvent) {

        // Send event to Kafka topic
        kafkaTemplate.send("simulator-topic", paymentEvent);
    }

}
