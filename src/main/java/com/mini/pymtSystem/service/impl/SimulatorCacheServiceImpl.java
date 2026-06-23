package com.mini.pymtSystem.service.impl;

import com.mini.pymtSystem.service.SimulatorCacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SimulatorCacheServiceImpl implements SimulatorCacheService{

    private final RedisTemplate<String,Object>
            redisTemplate;

    public SimulatorCacheServiceImpl(
            RedisTemplate<String,Object>
                    redisTemplate){

        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveStatus(String debitAcct, String status){

        redisTemplate.opsForValue()
                .set(
                        "SIM:" + debitAcct,
                        status);
    }

    @Override
    public String getStatus(String debitAcct){

        return (String)
                redisTemplate.opsForValue()
                        .get("SIM:" + debitAcct);
    }
}
