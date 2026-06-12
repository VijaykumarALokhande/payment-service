package com.mini.pymtSystem.service.impl;

import com.mini.pymtSystem.entity.PymtDetails;
import com.mini.pymtSystem.repository.PymtDetailsRepository;
import com.mini.pymtSystem.service.PymtService;
import org.springframework.stereotype.Service;

@Service
public class PymtServiceImpl implements PymtService {

    private final PymtDetailsRepository repository;

    public PymtServiceImpl(PymtDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public PymtDetails createPaymet(PymtDetails payment) {
        return repository.save(payment);
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
