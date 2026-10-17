package com.jvlcode.springboot_demo.service;

import com.jvlcode.springboot_demo.entity.BankTransaction;
import com.jvlcode.springboot_demo.entity.FraudAlert;
import com.jvlcode.springboot_demo.repository.FraudAlertRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class FraudDetectionService {

    private final FraudAlertRepository fraudAlertRepository;

    @Value("${app.fraud.max-transaction-amount:100000}")
    private BigDecimal maxTransactionAmount;

    public FraudDetectionService(
            FraudAlertRepository fraudAlertRepository) {
        this.fraudAlertRepository = fraudAlertRepository;
    }

    @Transactional
    public boolean inspect(BankTransaction transaction) {
        if (transaction.getAmount()
                .compareTo(maxTransactionAmount) <= 0) {
            return false;
        }

        if (!fraudAlertRepository.existsByTransactionId(
                transaction.getId())) {
            FraudAlert alert = new FraudAlert();
            alert.setTransaction(transaction);
            alert.setReason(
                    "Transaction amount exceeds configured threshold");
            fraudAlertRepository.save(alert);
        }

        return true;
    }
}