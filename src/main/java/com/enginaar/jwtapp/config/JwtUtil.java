package com.enginaar.jwtapp.config;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

	private static final long validityInMilliseconds = (24 * 60 * 60 * 1000); // 1 day

	@Value("${enginaar.jwt.secret}")
	private String secret;
	
	byte[] privateKeyBytes, publicKeyBytes;

	private SecretKeySpec secretKey;

	@PostConstruct
	public void initiate() {
		secretKey = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
	}

	public String createToken(String email, String id, String... roles) throws NoSuchAlgorithmException, InvalidKeySpecException {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);
				
		return Jwts.builder()
				.claim("scopes", roles)
				.setSubject(email)
				.setId(id)
				.setIssuedAt(now).setExpiration(validity)
				.signWith(secretKey)
				.compact();
	}

	public Jws<Claims> parseToken(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return Jwts.parserBuilder()
					.setSigningKey(secretKey)
					.build().parseClaimsJws(token);
	}
}



