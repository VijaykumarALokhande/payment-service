package com.mini.pymtSystem.service.impl;

import com.mini.pymtSystem.entity.PymtDetails;
import com.mini.pymtSystem.repository.PymtDetailsRepository;
import com.mini.pymtSystem.service.OutboxService;
import com.mini.pymtSystem.service.PymtService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PymtServiceImpl implements PymtService {

    private final PymtDetailsRepository repository;
    private final OutboxService outboxService;

    public PymtServiceImpl(PymtDetailsRepository repository, OutboxService outboxService) {
        this.repository = repository;
        this.outboxService = outboxService;
    }

    @Override
    @Transactional
    public PymtDetails createPaymet(PymtDetails payment) {
        PymtDetails savedPayment = repository.save(payment);
        outboxService.saveEvent(savedPayment);
        return savedPayment;
    }

    @Override
    public PymtDetails getPayment(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found"));
    }

    @Override
    public void deletePayment(Long id) {
        repository.deleteById(id);
    }
}
