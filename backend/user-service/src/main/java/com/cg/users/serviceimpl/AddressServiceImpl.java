package com.cg.users.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.users.dto.AddressDto;
import com.cg.users.entity.Address;
import com.cg.users.entity.User;
import com.cg.users.repository.UserRepo;
import com.cg.users.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	private UserRepo repo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public AddressDto add(AddressDto dto, Long userId) {
		User user = repo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		Address address = mapper.map(dto, Address.class);

		List<Address> addresses = user.getAddresses();
		addresses.add(address);
		user.setAddress(addresses);

		user = repo.save(user);

		address = user.getAddresses().get(addresses.size() - 1);

		return mapper.map(address, AddressDto.class);
	}

	@Override
	public AddressDto update(AddressDto dto, Long userId) {
		User user = repo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		List<Address> addresses = user.getAddresses().stream().map(a -> {

			if (a.getAddressId().equals(dto.getAddressId())) {
				a.setAddressLine1(dto.getAddressLine1());
				a.setAddressLine2(dto.getAddressLine2());
				a.setCity(dto.getCity());
				a.setPin(dto.getPin());
				a.setState(dto.getState());
			}
			
			return a;
		}).collect(Collectors.toList());

		user.setAddresses(addresses);
		repo.save(user);
	
		return dto;
	}

	@Override
	public AddressDto get(Long userId, Long addressId) {
		User user = repo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		Address address = user.getAddresses().stream().findAny().filter(a -> a.getAddressId() == addressId)
				.orElseThrow(() -> new RuntimeException("Address not found"));

		return mapper.map(address, AddressDto.class);
	}

	@Override
	public List<AddressDto> get(Long userId) {
		User user = repo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		return mapper.map(user.getAddresses(), new TypeToken<List<AddressDto>>() {
		}.getType());
	}

	@Override
	public AddressDto delete(Long userId, Long addressId) {
		User user = repo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		Address address = user.getAddresses().stream().findAny().filter(a -> a.getAddressId() == addressId)
				.orElseThrow(() -> new RuntimeException("Address not found"));
		;
		List<Address> addresses = user.getAddresses().stream().filter(a -> a.getAddressId() != addressId)
				.collect(Collectors.toList());

		user.setAddresses(addresses);
		repo.save(user);

		return mapper.map(address, AddressDto.class);
	}

}
