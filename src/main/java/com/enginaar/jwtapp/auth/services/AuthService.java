package com.enginaar.jwtapp.auth.services;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.enginaar.jwtapp.auth.domain.entities.User;
import com.enginaar.jwtapp.auth.domain.http.LoginRequest;
import com.enginaar.jwtapp.auth.domain.http.LoginResponse;
import com.enginaar.jwtapp.auth.domain.http.RegisterRequest;
import com.enginaar.jwtapp.auth.exception.EnginaarException;
import com.enginaar.jwtapp.auth.repositories.UserRepository;
import com.enginaar.jwtapp.config.JwtUtil;


@Service
public class AuthService {

	//#region variables
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private JwtUtil jwt;

	@Autowired
	private PasswordEncoder passwords;
	//#endregion

	public LoginResponse login(LoginRequest request) {
		var user = userRepo.findByEmail(request.getEmail());
		Assert.isTrue(user.isPresent(), "Invalid email/password");
		Assert.isTrue(passwords.matches(request.getPassword(), user.get().getPassword()),  "Invalid email/password");
		return toLoginResponse(user.get());
	}


	public User register(RegisterRequest request) {
		var user = userRepo.findByEmail(request.getEmail());
		Assert.isTrue(user.isEmpty(), "Email already existed");
		var u = toUser(request);
		var saved = userRepo.save(u);
		return saved;
	}

	//#region mapper

	private LoginResponse toLoginResponse(User user) {
		try {
			var token = jwt.createToken(user.getEmail(), user.getId().toString(), "ROLE_USER");
			return new LoginResponse(token);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new EnginaarException("JWT token creation failed");
		}
	}

	private User toUser(RegisterRequest request) {		
		var tmp = new User();
		tmp.setEmail(request.getEmail());
		var encoded = passwords.encode(request.getPassword());
		tmp.setPassword(encoded);
		return tmp;
	}
	//#endregion

}
