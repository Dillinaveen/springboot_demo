package com.jvlcode.springboot_demo.service;

import com.jvlcode.springboot_demo.dto.CustomerRequest;
import com.jvlcode.springboot_demo.dto.CustomerResponse;
import com.jvlcode.springboot_demo.entity.Customer;
import com.jvlcode.springboot_demo.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomer(Long id) {
        return customerRepository.findById(id)
                .map(CustomerResponse::from)
                .orElseThrow(() ->
                        new IllegalArgumentException("Customer not found"));
    }

    @Transactional
    public CustomerResponse updateCustomer(
            Long id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Customer not found"));

        boolean phoneUsed = customerRepository.existsByPhone(
                request.getPhone());

        if (phoneUsed && !customer.getPhone().equals(request.getPhone())) {
            throw new IllegalArgumentException(
                    "Phone number is already registered");
        }

        customer.setFullName(request.getFullName());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());

        return CustomerResponse.from(customerRepository.save(customer));
    }
}