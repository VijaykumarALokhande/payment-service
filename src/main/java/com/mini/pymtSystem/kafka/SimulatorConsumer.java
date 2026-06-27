package com.mini.pymtSystem.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.pymtSystem.dto.PaymentEvent;
import com.mini.pymtSystem.entity.FailedEvent;
import com.mini.pymtSystem.entity.ProcessedEvent;
import com.mini.pymtSystem.entity.PymtDetails;
import com.mini.pymtSystem.entity.SimulatorRules;
import com.mini.pymtSystem.repository.*;
import com.mini.pymtSystem.service.RoutingCacheService;
import com.mini.pymtSystem.service.SimulatorCacheService;
import com.mini.pymtSystem.service.SimulatorService;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SimulatorConsumer {

    private final SimulatorService simulatorService;
    private final FailedEventRepository failedEventRepository;
    private final ObjectMapper objectMapper;

    public SimulatorConsumer(SimulatorService simulatorService, FailedEventRepository failedEventRepository, ObjectMapper objectMapper) {
        this.simulatorService = simulatorService;
        this.failedEventRepository = failedEventRepository;
        this.objectMapper = objectMapper;
    }

    @RetryableTopic(
            attempts = "4",
            backoff = @Backoff(
                    delay = 5000,
                    multiplier = 2.0
            )
    )
    @KafkaListener(
            topics = "simulator-topic",
            groupId = "payment-group")
    public void simulatePayment(PaymentEvent paymentEvent){
        simulatorService.processPaymentEvent(paymentEvent);
    }

    @DltHandler
    public void handleDeadLetter(PaymentEvent paymentEvent){

        System.out.println("Message reached DLT");

        FailedEvent failed = new FailedEvent();
        failed.setEventId(paymentEvent.getEventId() );
        failed.setFailureReason("Retry Exhausted");
        failed.setFailedAt(LocalDateTime.now());
        failed.setReplayed(false);
        try {
            failed.setPayload(
                    objectMapper.writeValueAsString(paymentEvent));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to serialize PaymentEvent", e);
        }

        failedEventRepository.save(failed);
        System.out.println("Stored in Failed Event table: "+paymentEvent.getEventId());
    }
}
