package com.cg.order.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
@Entity
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;
	private Long userId;
	private Double totalPrice;
	@Enumerated(EnumType.STRING)
	private OrderStatus status = OrderStatus.PENDING;
	private LocalDate deliveryDate;
	private LocalDate updatedAt;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<Product> products = new ArrayList<>();

	public void setProducts(List<Product> products) {
		if (products == null)
			return;

		this.products = products;
		for (Product product : this.products) {
			product.setOrder(this);
			this.totalPrice += product.getPrice();
		}
	}

	@PreUpdate
	@PrePersist
	void updateDate() {
		updatedAt = LocalDate.now();
	}
}
