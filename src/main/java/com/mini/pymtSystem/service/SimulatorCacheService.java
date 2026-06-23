package com.mini.pymtSystem.service;

public interface SimulatorCacheService {
    void saveStatus(
            String debitAcct,
            String status);

    String getStatus(
            String debitAcct);
}
