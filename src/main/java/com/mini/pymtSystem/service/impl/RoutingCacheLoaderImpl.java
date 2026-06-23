package com.mini.pymtSystem.service.impl;

import com.mini.pymtSystem.repository.RoutingRuleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RoutingCacheLoaderImpl {

    //dependency injection
    private final RoutingRuleRepository repository;
    private final RoutingCacheServiceImpl cacheService;

    public RoutingCacheLoaderImpl(RoutingRuleRepository repository, RoutingCacheServiceImpl cacheService) {
        this.repository = repository;
        this.cacheService = cacheService;
    }

    @PostConstruct
    public void loadCache(){
        repository.findAll()
                .forEach(rule ->
                        cacheService.saveRoute(
                                rule.getDebitAcct(),
                                rule.getTargetTopic()));
    }

}
