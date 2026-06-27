package com.mini.pymtSystem.dto;

import com.mini.pymtSystem.entity.PymtDetails;

public class PaymentEvent {
    private PymtDetails pymtDetails;
    private Long eventId;

    public PaymentEvent() {
    }

    public PaymentEvent(PymtDetails pymtDetails, Long eventId) {
        this.pymtDetails = pymtDetails;
        this.eventId = eventId;
    }

    public PymtDetails getPymtDetails() {
        return pymtDetails;
    }

    public void setPymtDetails(PymtDetails pymtDetails) {
        this.pymtDetails = pymtDetails;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
