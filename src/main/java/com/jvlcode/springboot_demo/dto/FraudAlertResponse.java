package com.jvlcode.springboot_demo.dto;

import com.jvlcode.springboot_demo.entity.BankTransaction;
import com.jvlcode.springboot_demo.entity.FraudAlert;
import java.time.LocalDateTime;

public record FraudAlertResponse(
        Long id,
        Long transactionId,
        String transactionReference,
        String reason,
        boolean reviewed,
        LocalDateTime createdAt
) {
    public static FraudAlertResponse from(FraudAlert alert) {
        BankTransaction tx = alert.getTransaction();

        return new FraudAlertResponse(
                alert.getId(),
                tx.getId(),
                tx.getReferenceNumber(),
                alert.getReason(),
                alert.isReviewed(),
                alert.getCreatedAt()
        );
    }
}