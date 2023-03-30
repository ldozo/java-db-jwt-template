package com.enginaar.jwtapp.auth.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enginaar.jwtapp.auth.domain.entities.User;
import com.enginaar.jwtapp.auth.domain.http.LoginRequest;
import com.enginaar.jwtapp.auth.domain.http.LoginResponse;
import com.enginaar.jwtapp.auth.domain.http.RegisterRequest;
import com.enginaar.jwtapp.auth.services.AuthService;

@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private AuthService users;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        var response = users.login(request);
        return response;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        var response = users.register(request);
        return response;
    }

    @GetMapping
    public Principal me() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("secured")
    @Secured("ROLE_USER")
    private String secured() {
        return "{ \"message\": \"ROLE_USER granted\"}";
    }

}
