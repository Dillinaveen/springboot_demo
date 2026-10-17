package com.jvlcode.springboot_demo.dto;

import com.jvlcode.springboot_demo.entity.Account;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(
        Long id,
        String accountNumber,
        Long customerId,
        BigDecimal balance,
        String status,
        LocalDateTime createdAt
) {
    public static AccountResponse from(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getCustomer().getId(),
                account.getBalance(),
                account.getStatus().name(),
                account.getCreatedAt()
        );
    }
}