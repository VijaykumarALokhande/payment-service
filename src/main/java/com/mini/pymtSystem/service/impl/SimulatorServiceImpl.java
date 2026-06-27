package com.mini.pymtSystem.service.impl;

import com.mini.pymtSystem.dto.PaymentEvent;
import com.mini.pymtSystem.entity.ProcessedEvent;
import com.mini.pymtSystem.entity.PymtDetails;
import com.mini.pymtSystem.entity.SimulatorRules;
import com.mini.pymtSystem.repository.ProcessedEventRepository;
import com.mini.pymtSystem.repository.PymtDetailsRepository;
import com.mini.pymtSystem.repository.SimulatorRuleRepository;
import com.mini.pymtSystem.service.SimulatorCacheService;
import com.mini.pymtSystem.service.SimulatorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SimulatorServiceImpl implements SimulatorService {

    private final SimulatorRuleRepository simulatorRepository;
    private final PymtDetailsRepository pymtDetailsRepository;
    private final SimulatorCacheService simulatorCacheService;
    private final ProcessedEventRepository processedRepository;

    public SimulatorServiceImpl(SimulatorRuleRepository simulatorRepository, PymtDetailsRepository pymtDetailsRepository, SimulatorCacheService simulatorCacheService, ProcessedEventRepository processedRepository){
        this.simulatorRepository = simulatorRepository;
        this.pymtDetailsRepository = pymtDetailsRepository;
        this.simulatorCacheService = simulatorCacheService;
        this.processedRepository = processedRepository;
    }

    @Transactional
    @Override
    public void processPaymentEvent(PaymentEvent paymentEvent){
        Long eventId = paymentEvent.getEventId();
        PymtDetails pymtDetails = paymentEvent.getPymtDetails();

        System.out.println("Received in simulator for paymentId:"+pymtDetails.getId() );

        if(true){
            throw new RuntimeException("Testing Retry");
        }

        //check for idempotency -> this makes sense only from learning perspective.
        if(processedRepository.existsById(eventId)){
            System.out.println("Duplicate Event Received : " + eventId);
            return;
        }

        //Else this event came to Simulator Consumer first time

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

        ProcessedEvent processedEvent = new ProcessedEvent(eventId, LocalDateTime.now());
        processedRepository.save(processedEvent);

        System.out.println("Simulator Transaction Completed");
    }
}
