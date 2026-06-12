package com.mini.pymtSystem.service;

import com.mini.pymtSystem.entity.PymtDetails;

public interface PymtService {
    PymtDetails createPaymet(PymtDetails payment);
    PymtDetails getPayment(Long id);
    void deletePayment(Long id);
}
