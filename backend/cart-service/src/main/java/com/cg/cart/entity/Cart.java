package com.cg.cart.entity;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cartId;
	@Column(nullable = false)
	private Long userId;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
	private List<Product> products = new ArrayList<>();

	public void setProducts(List<Product> products) {
		if (products != null)
			this.products = products;

		for (Product product : this.products)
			product.setCart(this);
	}
}
