package org.demo.crm.auth.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String role;
}
