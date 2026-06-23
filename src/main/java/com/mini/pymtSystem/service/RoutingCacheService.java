package com.mini.pymtSystem.service;

public interface RoutingCacheService {

    void saveRoute(
            String debitAcct,
            String targetTopic);

    String getRoute(
            String debitAcct);
}
