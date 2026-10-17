package com.jvlcode.springboot_demo.service;

import com.jvlcode.springboot_demo.dto.AccountRequest;
import com.jvlcode.springboot_demo.dto.AccountResponse;
import com.jvlcode.springboot_demo.entity.Account;
import com.jvlcode.springboot_demo.entity.AccountStatus;
import com.jvlcode.springboot_demo.entity.Customer;
import com.jvlcode.springboot_demo.repository.AccountRepository;
import com.jvlcode.springboot_demo.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(
            AccountRepository accountRepository,
            CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public AccountResponse createAccount(AccountRequest request) {
        Customer customer = customerRepository
                .findById(request.getCustomerId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Customer not found"));

        Account account = new Account();
        account.setCustomer(customer);
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(AccountStatus.ACTIVE);

        return AccountResponse.from(accountRepository.save(account));
    }

    @Transactional(readOnly = true)
    public AccountResponse getAccount(Long id) {
        return accountRepository.findById(id)
                .map(AccountResponse::from)
                .orElseThrow(() ->
                        new IllegalArgumentException("Account not found"));
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getAccountsByCustomer(Long customerId) {
        return accountRepository.findByCustomerId(customerId)
                .stream()
                .map(AccountResponse::from)
                .toList();
    }

    @Transactional
    public AccountResponse updateStatus(Long id, AccountStatus status) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Account not found"));

        account.setStatus(status);

        return AccountResponse.from(accountRepository.save(account));
    }

    private String generateAccountNumber() {
        String number;

        do {
            number = UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 12)
                    .toUpperCase();
        } while (accountRepository.existsByAccountNumber(number));

        return number;
    }
}