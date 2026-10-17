package com.jvlcode.springboot_demo.dto;

import com.jvlcode.springboot_demo.entity.User;
import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String email,
        String role,
        boolean enabled,
        LocalDateTime createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                user.isEnabled(),
                user.getCreatedAt()
        );
    }
}