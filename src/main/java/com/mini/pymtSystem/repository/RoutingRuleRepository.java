package com.mini.pymtSystem.repository;

import com.mini.pymtSystem.entity.RoutingRules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoutingRuleRepository extends JpaRepository<RoutingRules, Long> {

    //Optional helps to tackle risky null value exception
    Optional<RoutingRules> findByDebitAcct(String debitAcct);
}
