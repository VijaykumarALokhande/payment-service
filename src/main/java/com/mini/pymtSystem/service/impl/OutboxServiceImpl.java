package com.mini.pymtSystem.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.pymtSystem.entity.OutboxEvent;
import com.mini.pymtSystem.entity.OutboxStatus;
import com.mini.pymtSystem.entity.PymtDetails;
import com.mini.pymtSystem.repository.OutboxEventRepository;
import com.mini.pymtSystem.service.OutboxService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OutboxServiceImpl implements OutboxService {

    //dependency injection
    private final OutboxEventRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public OutboxServiceImpl(
            OutboxEventRepository outboxRepository,
            ObjectMapper objectMapper) {

        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void saveEvent(PymtDetails pymtDetails){
        OutboxEvent event = new OutboxEvent();

        //set the attributes of the object event
        event.setEventType("PAYMENT_CREATED");
        event.setAggregateType("PAYMENT");
        event.setAggregateId(pymtDetails.getId());

        try {
            event.setPayload(objectMapper.writeValueAsString(pymtDetails));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to serialize payment", e);
        }

        event.setStatus(OutboxStatus.NEW);
        event.setRetryCount(0);
        event.setCreatedAt(LocalDateTime.now());

        //save to repo
        outboxRepository.save(event);
    }

    @Override
    public List<OutboxEvent> getPendingEvents(){
        return outboxRepository.findByStatus(OutboxStatus.NEW);
    }

    @Override
    public void markAsSent(OutboxEvent event){
        event.setStatus(OutboxStatus.SENT);
        event.setPublishedAt(LocalDateTime.now());
        outboxRepository.save(event);
    }

}
