package com.mini.pymtSystem.kafka;

import com.mini.pymtSystem.entity.PymtDetails;
import com.mini.pymtSystem.entity.SimulatorRules;
import com.mini.pymtSystem.repository.PymtDetailsRepository;
import com.mini.pymtSystem.repository.RoutingRuleRepository;
import com.mini.pymtSystem.repository.SimulatorRuleRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimulatorConsumer {
    private final SimulatorRuleRepository simulatorRepository;
    private final PymtDetailsRepository pymtDetailsRepository;

    public SimulatorConsumer(SimulatorRuleRepository simulatorRepository, PymtDetailsRepository pymtDetailsRepository){
        this.simulatorRepository = simulatorRepository;
        this.pymtDetailsRepository = pymtDetailsRepository;
    }

    @KafkaListener(
            topics = "simulator-topic",
            groupId = "payment-group")
    public void simulatePayment(PymtDetails pymtDetails){
        System.out.println("Received in simulator for paymentId:"+pymtDetails.getId() );

        Optional<SimulatorRules> rule = simulatorRepository.findByDebitAcct(pymtDetails.getDebitAcct());

        if(rule.isPresent()){
            String status = rule.get().getResponseStatus();
            pymtDetails.setPymtStatus(status);
            pymtDetailsRepository.save(pymtDetails);
            System.out.println("Payment " + pymtDetails.getId() + " updated to " + status);
        }
    }
}
