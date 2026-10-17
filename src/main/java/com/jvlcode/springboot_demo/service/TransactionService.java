package com.jvlcode.springboot_demo.service;

import com.jvlcode.springboot_demo.dto.TransactionRequest;
import com.jvlcode.springboot_demo.dto.TransactionResponse;
import com.jvlcode.springboot_demo.entity.*;
import com.jvlcode.springboot_demo.repository.AccountRepository;
import com.jvlcode.springboot_demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final FraudDetectionService fraudDetectionService;

    public TransactionService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            FraudDetectionService fraudDetectionService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.fraudDetectionService = fraudDetectionService;
    }

    @Transactional
    public TransactionResponse deposit(TransactionRequest request) {
        validateAmount(request.getAmount());

        Account account = lockAccount(request.getAccountId());
        ensureActive(account);

        BankTransaction tx = createTransaction(
                TransactionType.DEPOSIT,
                request.getAmount(),
                null,
                account);

        if (fraudDetectionService.inspect(tx)) {
            tx.setStatus(TransactionStatus.FLAGGED);
            return TransactionResponse.from(
                    transactionRepository.save(tx));
        }

        account.setBalance(
                account.getBalance().add(request.getAmount()));
        accountRepository.save(account);

        tx.setStatus(TransactionStatus.SUCCESS);
        return TransactionResponse.from(
                transactionRepository.save(tx));
    }

    @Transactional
    public TransactionResponse withdraw(TransactionRequest request) {
        validateAmount(request.getAmount());

        Account account = lockAccount(request.getAccountId());
        ensureActive(account);

        BankTransaction tx = createTransaction(
                TransactionType.WITHDRAWAL,
                request.getAmount(),
                account,
                null);

        if (fraudDetectionService.inspect(tx)) {
            tx.setStatus(TransactionStatus.FLAGGED);
            return TransactionResponse.from(
                    transactionRepository.save(tx));
        }

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        account.setBalance(
                account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);

        tx.setStatus(TransactionStatus.SUCCESS);
        return TransactionResponse.from(
                transactionRepository.save(tx));
    }

    @Transactional
    public TransactionResponse transfer(TransactionRequest request) {
        validateAmount(request.getAmount());

        Long sourceId = request.getAccountId();
        Long destinationId = request.getDestinationAccountId();

        if (destinationId == null || sourceId.equals(destinationId)) {
            throw new IllegalArgumentException(
                    "Provide two different account IDs");
        }

        // Lock in a consistent order to reduce deadlock risk.
        Account first = lockAccount(Math.min(sourceId, destinationId));
        Account second = lockAccount(Math.max(sourceId, destinationId));

        Account source = sourceId.equals(first.getId()) ? first : second;
        Account destination =
                destinationId.equals(first.getId()) ? first : second;

        ensureActive(source);
        ensureActive(destination);

        BankTransaction tx = createTransaction(
                TransactionType.TRANSFER,
                request.getAmount(),
                source,
                destination);

        if (fraudDetectionService.inspect(tx)) {
            tx.setStatus(TransactionStatus.FLAGGED);
            return TransactionResponse.from(
                    transactionRepository.save(tx));
        }

        if (source.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        source.setBalance(
                source.getBalance().subtract(request.getAmount()));
        destination.setBalance(
                destination.getBalance().add(request.getAmount()));

        accountRepository.save(source);
        accountRepository.save(destination);

        tx.setStatus(TransactionStatus.SUCCESS);
        return TransactionResponse.from(
                transactionRepository.save(tx));
    }

    private Account lockAccount(Long id) {
        return accountRepository.findByIdForUpdate(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Account not found: " + id));
    }

    private void ensureActive(Account account) {
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalArgumentException(
                    "Account is not active");
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero");
        }
        if (amount.scale() > 2) {
            throw new IllegalArgumentException(
                    "Amount cannot have more than two decimal places");
        }
    }

    private BankTransaction createTransaction(
            TransactionType type,
            BigDecimal amount,
            Account source,
            Account destination) {

        BankTransaction tx = new BankTransaction();
        tx.setReferenceNumber(UUID.randomUUID().toString());
        tx.setType(type);
        tx.setAmount(amount);
        tx.setSourceAccount(source);
        tx.setDestinationAccount(destination);
        tx.setStatus(TransactionStatus.PENDING);

        return transactionRepository.save(tx);
    }
}