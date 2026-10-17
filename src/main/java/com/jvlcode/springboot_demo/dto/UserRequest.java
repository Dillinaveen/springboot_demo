package com.jvlcode.springboot_demo.dto;

import com.jvlcode.springboot_demo.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRequest {

    @NotBlank
    @Email
    private String email;

    private Role role;

    private Boolean enabled;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}