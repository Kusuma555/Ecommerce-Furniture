package com.cg.users.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_table")
@Data
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	private String firstName;
	private String lastName;
	private String gender;
	@Column(unique = true)
	private String mobileNumber;
	@Enumerated(EnumType.STRING)
	private USER_ROLE role;
	@Column(unique = true)
	private String emailId;
	private String password;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Address> addresses; 
	
	public void setAddress(List<Address> address) {
		this.addresses = address;
		if(this.addresses == null)return;
		
		for(Address add: addresses)
			add.setUser(this);
	}
	
}
