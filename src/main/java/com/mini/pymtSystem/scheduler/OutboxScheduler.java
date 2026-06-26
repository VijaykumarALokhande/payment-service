package com.mini.pymtSystem.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.pymtSystem.entity.OutboxEvent;
import com.mini.pymtSystem.entity.PymtDetails;
import com.mini.pymtSystem.kafka.SimulatorProducer;
import com.mini.pymtSystem.service.OutboxService;
import com.mini.pymtSystem.service.RoutingCacheService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutboxScheduler {

    //Dependency injection
    private final OutboxService outboxService;
    private final SimulatorProducer simulatorProducer;
    private final RoutingCacheService routingCacheService;
    private final ObjectMapper objectMapper;

    public OutboxScheduler(OutboxService outboxService, SimulatorProducer simulatorProducer, RoutingCacheService routingCacheService, ObjectMapper objectMapper){
        this.outboxService = outboxService;
        this.simulatorProducer = simulatorProducer;
        this.objectMapper = objectMapper;
        this.routingCacheService = routingCacheService;
    }

    //event publication from outbox DB
    @Scheduled(fixedDelay = 5000)
    public void publishOutBoxEvents() {
        List<OutboxEvent> events = outboxService.getPendingEvents();
        for(OutboxEvent event : events){

            try{
                //Convert json Payload back into payment object
                PymtDetails pymtDetails = objectMapper.readValue(event.getPayload(), PymtDetails.class);

                //check routing cache
                String route = routingCacheService.getRoute(pymtDetails.getDebitAcct() );
                if(route!=null){
                    //push to simuilator queue and mark outbox as sent.
                    System.out.println("Route found in Redis");
                    simulatorProducer.publish(pymtDetails);
                    outboxService.markAsSent(event);
                    System.out.println("Outbox Event "
                                    + event.getId()
                                    + " marked as SENT");
                }else{
                    System.out.println("Not in redis, Stop HERE!");
                }
            }catch (Exception ex) {

                System.out.println("Failed to process Outbox Event "
                                + event.getId());

                ex.printStackTrace();
            }
        }
    }
}
