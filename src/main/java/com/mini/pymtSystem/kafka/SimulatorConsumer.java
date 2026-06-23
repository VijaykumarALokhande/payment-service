package com.mini.pymtSystem.kafka;

import com.mini.pymtSystem.entity.PymtDetails;
import com.mini.pymtSystem.entity.SimulatorRules;
import com.mini.pymtSystem.repository.PymtDetailsRepository;
import com.mini.pymtSystem.repository.RoutingRuleRepository;
import com.mini.pymtSystem.repository.SimulatorRuleRepository;
import com.mini.pymtSystem.service.RoutingCacheService;
import com.mini.pymtSystem.service.SimulatorCacheService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimulatorConsumer {
    private final SimulatorRuleRepository simulatorRepository;
    private final PymtDetailsRepository pymtDetailsRepository;
    private final SimulatorCacheService simulatorCacheService;

    public SimulatorConsumer(SimulatorRuleRepository simulatorRepository, PymtDetailsRepository pymtDetailsRepository, SimulatorCacheService simulatorCacheService){
        this.simulatorRepository = simulatorRepository;
        this.pymtDetailsRepository = pymtDetailsRepository;
        this.simulatorCacheService = simulatorCacheService;
    }

    @KafkaListener(
            topics = "simulator-topic",
            groupId = "payment-group")
    public void simulatePayment(PymtDetails pymtDetails){
        System.out.println("Received in simulator for paymentId:"+pymtDetails.getId() );

        //First check cache, am NOT assuming cache loader is working here properly.
        String status = simulatorCacheService.getStatus(pymtDetails.getDebitAcct());

        if(status != null){
            System.out.println("got from simulator redis");
            pymtDetails.setPymtStatus(status);
            pymtDetailsRepository.save(pymtDetails);
        }else{
            //I assume that loader doesn't work properly
            Optional<SimulatorRules> rule = simulatorRepository.findByDebitAcct(pymtDetails.getDebitAcct());

            if(rule.isPresent()){
                status = rule.get().getResponseStatus();
                pymtDetails.setPymtStatus(status);
                pymtDetailsRepository.save(pymtDetails);
                System.out.println("Payment " + pymtDetails.getId() + " updated to " + status + " via DB");

                //save to Cache -> since we got the output
                simulatorCacheService.saveStatus(pymtDetails.getDebitAcct(), status);
                System.out.println("Saved to Cache");
            }
        }
    }
}
