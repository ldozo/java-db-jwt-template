package com.enginaar.jwtapp.auth.domain.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;

@MappedSuperclass
public abstract class BaseEntity {
	@Column(name = "created_at")
	@NotNull
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	@NotNull
	private LocalDateTime updatedAt;

	@PreUpdate
	private void preUpdate() {
		updatedAt = LocalDateTime.now();
	}

	@PrePersist
	private void prePersist() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}
}
