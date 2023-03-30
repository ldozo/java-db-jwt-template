package com.enginaar.jwtapp.auth.domain.http;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
}
