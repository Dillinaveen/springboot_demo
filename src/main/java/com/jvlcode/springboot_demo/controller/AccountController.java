package com.jvlcode.springboot_demo.controller;

import com.jvlcode.springboot_demo.dto.*;
import com.jvlcode.springboot_demo.entity.AccountStatus;
import com.jvlcode.springboot_demo.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public AccountResponse createAccount(
            @Valid @RequestBody AccountRequest request) {
        return accountService.createAccount(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'FRAUD_ANALYST')")
    public AccountResponse getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public List<AccountResponse> getCustomerAccounts(
            @PathVariable Long customerId) {
        return accountService.getAccountsByCustomer(customerId);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public AccountResponse updateStatus(
            @PathVariable Long id,
            @RequestParam AccountStatus status) {
        return accountService.updateStatus(id, status);
    }
}