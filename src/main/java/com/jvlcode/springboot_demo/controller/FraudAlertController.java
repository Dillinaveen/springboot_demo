package com.jvlcode.springboot_demo.controller;

import com.jvlcode.springboot_demo.dto.FraudAlertResponse;
import com.jvlcode.springboot_demo.entity.FraudAlert;
import com.jvlcode.springboot_demo.repository.FraudAlertRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fraud-alerts")
@PreAuthorize("hasAnyRole('ADMIN', 'FRAUD_ANALYST')")
public class FraudAlertController {

    private final FraudAlertRepository fraudAlertRepository;

    public FraudAlertController(
            FraudAlertRepository fraudAlertRepository) {
        this.fraudAlertRepository = fraudAlertRepository;
    }

    @GetMapping("/pending")
    public List<FraudAlertResponse> getPendingAlerts() {
        return fraudAlertRepository
                .findByReviewedFalseOrderByCreatedAtDesc()
                .stream()
                .map(FraudAlertResponse::from)
                .toList();
    }

    @PatchMapping("/{id}/review")
    public FraudAlertResponse markReviewed(@PathVariable Long id) {
        FraudAlert alert = fraudAlertRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Fraud alert not found"));

        alert.setReviewed(true);

        return FraudAlertResponse.from(
                fraudAlertRepository.save(alert));
    }
}