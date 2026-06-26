package com.mini.pymtSystem.service;

import com.mini.pymtSystem.entity.OutboxEvent;
import com.mini.pymtSystem.entity.PymtDetails;

import java.util.List;

public interface OutboxService {
    void saveEvent(PymtDetails payment);
    List<OutboxEvent> getPendingEvents();
    void markAsSent(OutboxEvent event);
}
