package com.cg.users.service;

import java.util.List;

import com.cg.users.dto.AddressDto;


public interface AddressService {
	AddressDto add(AddressDto dto, Long userId);

	AddressDto update(AddressDto dto, Long userId);

	AddressDto get(Long userId, Long addressId);

	List<AddressDto> get(Long userId);

	AddressDto delete(Long userId, Long addressId);
}
