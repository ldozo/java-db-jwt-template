package com.enginaar.jwtapp.config;

import static org.springframework.util.StringUtils.hasText;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	JwtUtil jwtutil;

	@Override
	protected void doFilterInternal(@NonNull final HttpServletRequest request, @NonNull final HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		String header = request.getHeader("Authorization");

		if (!hasText(header) || !header.startsWith("Bearer ")) {
			log.warn("Header has no jwt/internal authentication");
			filterChain.doFilter(request, response);
			return;
		}
		String jwtToken = header.split(" ")[1].trim();
		Jws<Claims> token;
		try {
			token = jwtutil.parseToken(jwtToken);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.warn("Unable to parse JWT", e);
			filterChain.doFilter(request,response);
			return;
		} 

		String email = (String) token.getBody().getSubject();
		List<String> scopes = (List<String>) token.getBody().get("scopes");

		List<SimpleGrantedAuthority> list = scopes.stream().map(SimpleGrantedAuthority::new).toList();

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, list);
		log.info("authenticated user with email : {}", email);
		log.info("Authentication: " + authentication);
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}

}
