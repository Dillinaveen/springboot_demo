package com.jvlcode.springboot_demo.dto;

public record AuthResponse(
        String token,
        String tokenType,
        Long userId,
        String email,
        String role
) {}