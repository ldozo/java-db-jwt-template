package com.enginaar.jwtapp.auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enginaar.jwtapp.auth.domain.entities.User;


public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(Object email);

}
