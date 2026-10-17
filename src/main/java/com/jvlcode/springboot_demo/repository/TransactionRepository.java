package com.jvlcode.springboot_demo.repository;

import com.jvlcode.springboot_demo.entity.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository
        extends JpaRepository<BankTransaction, Long> {

    Optional<BankTransaction> findByReferenceNumber(
            String referenceNumber);

    List<BankTransaction> findBySourceAccountIdOrderByCreatedAtDesc(
            Long accountId);

    List<BankTransaction>
    findByDestinationAccountIdOrderByCreatedAtDesc(Long accountId);

    boolean existsByReferenceNumber(String referenceNumber);
}