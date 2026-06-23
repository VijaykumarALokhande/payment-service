package com.mini.pymtSystem.service.impl;

import com.mini.pymtSystem.service.RoutingCacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RoutingCacheServiceImpl implements RoutingCacheService {

    //dependency injection
    private final RedisTemplate<String,Object> redisTemplate;
    public RoutingCacheServiceImpl(
            RedisTemplate<String,Object> redisTemplate){

        this.redisTemplate = redisTemplate;
    }

    public void saveRoute(String debitAcct, String targetTopic){

        redisTemplate.opsForValue().set(
                        "ROUTE:" + debitAcct,
                        targetTopic);
    }

    public String getRoute(String debitAcct){

        return (String) redisTemplate.opsForValue().get("ROUTE:" + debitAcct);
    }
}
