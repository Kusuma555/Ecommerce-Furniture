package com.cg.users.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.users.dto.UserCredentials;
import com.cg.users.dto.UserDto;
import com.cg.users.entity.User;
import com.cg.users.repository.UserRepo;
import com.cg.users.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo repo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto add(UserDto userDto) {
		Optional<User> optionalUser = repo.findByEmailId(userDto.getEmailId());
		if (optionalUser.isPresent())
			throw new RuntimeException("User already exists");

		if (repo.findByMobileNumber(userDto.getMobileNumber()).isPresent())
			throw new RuntimeException("Mobile number already registered");

		User user = mapper.map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));

		return mapper.map(repo.save(user), UserDto.class);
	}

	@Override
	public UserDto update(UserDto userDto) {
		Optional<User> optionalUser = repo.findById(userDto.getUserId());
		if (optionalUser.isEmpty())
			throw new RuntimeException("User Not found");

		User user = optionalUser.get();
		if (userDto.getMobileNumber() != null && 
				!userDto.getMobileNumber().equals(user.getMobileNumber())) {
			if (repo.findByMobileNumber(userDto.getMobileNumber()).isPresent())
				throw new RuntimeException("Mobile number already registered");

			user.setMobileNumber(userDto.getMobileNumber());
		}

		if (userDto.getFirstName() != null && 
				!userDto.getFirstName().equals(user.getFirstName()))
			user.setFirstName(userDto.getFirstName());

		if (userDto.getLastName() != null && 
				!userDto.getLastName().equals(user.getLastName()))
			user.setLastName(userDto.getLastName());

		if (userDto.getPassword() != null && 
				!userDto.getPassword().equals(user.getPassword())) {
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		}
		
		user.setGender(userDto.getGender());

		return mapper.map(repo.save(user), UserDto.class);
	}

	@Override
	public UserDto delete(Long id) {
		User user = repo.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
		repo.deleteById(id);
		return mapper.map(user, UserDto.class);
	}

	@Override
	public UserDto get(Long userId) {
		User user = repo.findById(userId).orElseThrow(() -> new RuntimeException("User Not found"));
		return mapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> get() {
		return mapper.map(repo.findAll(), new TypeToken<List<UserDto>>() {
		}.getType());
	}

	@Override
	public UserDto get(String email) {
		User user = repo.findByEmailId(email).orElseThrow(() -> new RuntimeException("User not found"));
		return mapper.map(user, UserDto.class);
	}

	@Override
	public UserCredentials getCredntials(String email) {
		User user = repo.findByEmailId(email).orElseThrow(() -> new RuntimeException("User not found"));
//		System.out.println("testasdasdasdasdas");
		return mapper.map(user, UserCredentials.class);
	}

}
