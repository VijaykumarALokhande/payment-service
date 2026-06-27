package com.mini.pymtSystem.service;

import com.mini.pymtSystem.dto.PaymentEvent;

public interface SimulatorService {
    void processPaymentEvent(PaymentEvent paymentEvent);
}
