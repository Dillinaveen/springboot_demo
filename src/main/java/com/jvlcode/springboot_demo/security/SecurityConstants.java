package com.jvlcode.springboot_demo.security;

public final class SecurityConstants {

    public static final String[] PUBLIC_ENDPOINTS = {
        "/api/auth/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**",
        "/actuator/health"
    };

    private SecurityConstants() {
    }
}