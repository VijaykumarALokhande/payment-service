package com.mini.pymtSystem.repository;

import com.mini.pymtSystem.entity.PymtDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PymtDetailsRepository
        extends JpaRepository<PymtDetails, Long> {
}
