package com.jvlcode.springboot_demo.controller;

import com.jvlcode.springboot_demo.dto.TransactionRequest;
import com.jvlcode.springboot_demo.dto.TransactionResponse;
import com.jvlcode.springboot_demo.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public TransactionResponse deposit(
            @Valid @RequestBody TransactionRequest request) {
        return transactionService.deposit(request);
    }

    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public TransactionResponse withdraw(
            @Valid @RequestBody TransactionRequest request) {
        return transactionService.withdraw(request);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public TransactionResponse transfer(
            @Valid @RequestBody TransactionRequest request) {
        return transactionService.transfer(request);
    }
}