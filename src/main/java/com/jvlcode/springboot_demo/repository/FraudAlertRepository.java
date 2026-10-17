package com.jvlcode.springboot_demo.repository;

import com.jvlcode.springboot_demo.entity.FraudAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FraudAlertRepository
        extends JpaRepository<FraudAlert, Long> {

    List<FraudAlert> findByReviewedFalseOrderByCreatedAtDesc();

    Optional<FraudAlert> findByTransactionId(Long transactionId);

    boolean existsByTransactionId(Long transactionId);
}