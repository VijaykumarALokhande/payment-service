package com.mini.pymtSystem.kafka;

import com.mini.pymtSystem.dto.PaymentInitiationEvent;
import com.mini.pymtSystem.entity.PymtDetails;
import com.mini.pymtSystem.entity.RoutingRules;
import com.mini.pymtSystem.repository.PymtDetailsRepository;
import com.mini.pymtSystem.repository.RoutingRuleRepository;
import com.mini.pymtSystem.service.RoutingCacheService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentConsumer {

    //dependency injection
    private final PymtDetailsRepository repository;
    private final RoutingRuleRepository routingRepository;
    private final SimulatorProducer simulatorProducer;
    private final RoutingCacheService routingCacheService;

    public PaymentConsumer(PymtDetailsRepository repository, RoutingRuleRepository routingRepository, SimulatorProducer simulatorProducer, RoutingCacheService routingCacheService){
        this.repository = repository;
        this.routingRepository = routingRepository;
        this.simulatorProducer = simulatorProducer;
        this.routingCacheService = routingCacheService;
    }

    @KafkaListener(
            topics = "vijay-pymt-test",
            groupId = "payment-group")

    public void consume(PaymentInitiationEvent event){
        System.out.println("received message from kafka");

        //create db entity
        PymtDetails payment = new PymtDetails();
        payment.setDebitAcct(event.getDebitAcct());
        payment.setDebitCCY(event.getDebitCCY());
        //You give whatever status, It will always be received at the first
        payment.setPymtStatus("Received");

        //Save to repo
        repository.save(payment);
        System.out.println("Saved into DB");

        //First check cache, am assuming cache loader is working here properly.
        String route = routingCacheService.getRoute(payment.getDebitAcct());

        if(route != null){
            System.out.println("Route found in Redis");
            simulatorProducer.publish(payment);
        }else{
            System.out.println("Not in redis, Stop HERE!");
        }
    }

}
