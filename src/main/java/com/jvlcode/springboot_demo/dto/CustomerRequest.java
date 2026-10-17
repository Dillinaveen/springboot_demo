package com.jvlcode.springboot_demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    @Size(max = 20)
    private String phone;

    @Size(max = 500)
    private String address;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) {
        this.address = address;
    }
}