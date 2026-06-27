package com.mini.pymtSystem.kafka;

import com.mini.pymtSystem.dto.PaymentEvent;
import com.mini.pymtSystem.entity.ProcessedEvent;
import com.mini.pymtSystem.entity.PymtDetails;
import com.mini.pymtSystem.entity.SimulatorRules;
import com.mini.pymtSystem.repository.ProcessedEventRepository;
import com.mini.pymtSystem.repository.PymtDetailsRepository;
import com.mini.pymtSystem.repository.RoutingRuleRepository;
import com.mini.pymtSystem.repository.SimulatorRuleRepository;
import com.mini.pymtSystem.service.RoutingCacheService;
import com.mini.pymtSystem.service.SimulatorCacheService;
import com.mini.pymtSystem.service.SimulatorService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SimulatorConsumer {

    private final SimulatorService simulatorService;

    public SimulatorConsumer(SimulatorService simulatorService) {
        this.simulatorService = simulatorService;
    }

    @KafkaListener(
            topics = "simulator-topic",
            groupId = "payment-group")
    public void simulatePayment(PaymentEvent paymentEvent){
        simulatorService.processPaymentEvent(paymentEvent);
    }
}
