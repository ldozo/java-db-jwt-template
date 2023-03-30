package com.enginaar.jwtapp;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;

import com.enginaar.jwtapp.config.JwtUtil;

@SpringBootApplication
@EnableFeignClients
public class JwtApplication {
	
	@Autowired
	JwtUtil util;

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void createToken() {
		try {
			String token = util.createToken("kenan@enginaar.com", "1", "ROLE_ADMIN", "ROLE_USER");
			System.out.println(token);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}
}
