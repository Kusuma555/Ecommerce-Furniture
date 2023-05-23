package com.cg.ps.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Entity
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;
	@Column(nullable = false)
	private String title;
	private String description;
	private double price;
	private int stock;
	
	private Long categoryId;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@PrePersist
	private void createdAt() {
		this.createdAt = this.updatedAt  = LocalDateTime.now(); 
	}
	
	@PreUpdate
	private void update() {
		this.updatedAt  = LocalDateTime.now(); 
	}
}
