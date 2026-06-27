package com.mini.pymtSystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "failed_event")
public class FailedEvent {
    @Id
    @Column(name = "event_id")
    private Long eventId;
    private String payload;
    private String failureReason;
    private LocalDateTime failedAt;
    private Boolean replayed;

    public FailedEvent() {
    }

    public FailedEvent(Long eventId, String payload, String failureReason, LocalDateTime failedAt, Boolean replayed) {
        this.eventId = eventId;
        this.payload = payload;
        this.failureReason = failureReason;
        this.failedAt = failedAt;
        this.replayed = replayed;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public LocalDateTime getFailedAt() {
        return failedAt;
    }

    public void setFailedAt(LocalDateTime failedAt) {
        this.failedAt = failedAt;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public Boolean getReplayed() {
        return replayed;
    }

    public void setReplayed(Boolean replayed) {
        this.replayed = replayed;
    }
}
