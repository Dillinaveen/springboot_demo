package com.jvlcode.springboot_demo.dto;

import com.jvlcode.springboot_demo.entity.Customer;

public record CustomerResponse(
        Long id,
        Long userId,
        String email,
        String fullName,
        String phone,
        String address
) {
    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getUser().getId(),
                customer.getUser().getEmail(),
                customer.getFullName(),
                customer.getPhone(),
                customer.getAddress()
        );
    }
}