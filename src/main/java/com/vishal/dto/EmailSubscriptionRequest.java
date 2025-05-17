package com.vishal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailSubscriptionRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}