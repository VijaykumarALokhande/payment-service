package com.mini.pymtSystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name= "processed_events")
public class ProcessedEvent {
    @Id
    @Column(name ="event_id")
    private Long eventId;

    @Column(name = "processed_at")
    LocalDateTime processedAt;

    public ProcessedEvent() {
    }

    public ProcessedEvent(Long eventId, LocalDateTime processedAt) {
        this.eventId = eventId;
        this.processedAt = processedAt;
    }


    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }
}
