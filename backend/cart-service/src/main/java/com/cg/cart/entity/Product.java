package com.cg.cart.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long productId;
	private Integer quantity;
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "cartId")
	private Cart cart;
	
	private Date updatedAt;

	@PreUpdate
	@PrePersist
	void updateDate(){
		updatedAt = new Date();
	}
}
