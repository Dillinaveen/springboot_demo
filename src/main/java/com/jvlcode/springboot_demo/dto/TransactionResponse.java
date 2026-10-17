package com.jvlcode.springboot_demo.dto;

import com.jvlcode.springboot_demo.entity.BankTransaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        String referenceNumber,
        String type,
        String status,
        BigDecimal amount,
        Long sourceAccountId,
        Long destinationAccountId,
        LocalDateTime createdAt
) {
    public static TransactionResponse from(BankTransaction tx) {
        return new TransactionResponse(
                tx.getId(),
                tx.getReferenceNumber(),
                tx.getType().name(),
                tx.getStatus().name(),
                tx.getAmount(),
                tx.getSourceAccount() == null
                        ? null : tx.getSourceAccount().getId(),
                tx.getDestinationAccount() == null
                        ? null : tx.getDestinationAccount().getId(),
                tx.getCreatedAt()
        );
    }
}