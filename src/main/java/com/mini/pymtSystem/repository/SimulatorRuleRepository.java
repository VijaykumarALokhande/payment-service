package com.mini.pymtSystem.repository;

import com.mini.pymtSystem.entity.SimulatorRules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SimulatorRuleRepository extends JpaRepository<SimulatorRules, Long> {

    //Optional helps to tackle null pointer exception.
    Optional<SimulatorRules> findByDebitAcct(String debitAcct);
}
