package com.cg.users.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class Address {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long addressId;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private Long pin;
	private String state;
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;
}
