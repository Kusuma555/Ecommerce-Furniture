package com.cg.users.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDto {
	private Long addressId;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private Long pin;
	private String state;
}
