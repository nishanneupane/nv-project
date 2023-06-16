package com.nv.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nv.dto.AuthRequest;
import com.nv.dto.AuthResponse;
import com.nv.dto.RegisterResponse;
import com.nv.dto.UpdateRequest;
import com.nv.dto.UserDto;
import com.nv.entity.AuthenticationToken;
import com.nv.entity.Role;
import com.nv.entity.User;
import com.nv.exception.CustomException;
import com.nv.repository.TokenRepository;
import com.nv.repository.UserRepository;


@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthService service;
	@Autowired
	private TokenRepository tokenRepository;

	public RegisterResponse registerUser(User user) {
		if(userRepository.findByEmail(user.getEmail())==null) {
			throw new CustomException("user already present");
		}
		
		User u1=new User();
		u1.setPassword(passwordEncoder.encode(user.getPassword()));
		u1.setRole(Role.USER);
		u1.setApproved(false);
		u1.setFirstName(user.getFirstName());
		u1.setLastName(user.getLastName());
		u1.setEmail(user.getEmail());
		u1.setAbout(user.getAbout());
		u1.setAddress(user.getAddress());
		u1.setContact(user.getContact());
		
		
		User savedUser = userRepository.save(u1);
		
		AuthenticationToken token=new AuthenticationToken();
		token.setToken(UUID.randomUUID().toString()+UUID.randomUUID().toString());
		token.setUser(savedUser);
		token.setCreatedDate(LocalDate.now());
		tokenRepository.save(token);

		return new RegisterResponse("you have registered sucessfully", true);
	}
	
	public AuthResponse login(AuthRequest req) {
		User user = userRepository.findByEmail(req.getEmail()).orElseThrow(()->new CustomException("usser is not present"));
		if(user==null) {
			throw new CustomException("user is not valid");
		}
		
		if(!user.getEmail().equals(req.getEmail())) {
			throw new CustomException("invalid username or password");
		}
		
		AuthenticationToken token=service.getToken(user);
		
		if(Objects.isNull(token)) {
			throw new CustomException("token is not present");
		}

		return new AuthResponse(token.getToken(),true);
	}

	public UserDto editUser(UpdateRequest userDto,String token) {
		User user = userRepository.findByEmail(userDto.getEmail())
				.orElseThrow(() -> new CustomException("user not found with this email"+userDto.getEmail()));
		
		User admin = userRepository.findByEmail(service.getUsernameFromToken(token)).orElseThrow(() -> new CustomException("user not found with this email"));
		if (admin.getRole().toString() == "ADMIN") {
			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			user.setAbout(userDto.getAbout());
			user.setAddress(userDto.getAddress());
			user.setContact(userDto.getContact());
			user.setEmail(userDto.getEmail());
			user.setRole(userDto.getRole());
			user.setPassword(userDto.getPassword());
			user.setApproved(userDto.isApproved());
			user.setImage(userDto.getImage());			
			
			userRepository.save(user);

			UserDto dto = new UserDto();
			dto.setFirstName(user.getFirstName());
			dto.setLastName(user.getLastName());
			dto.setEmail(user.getEmail());
			dto.setAbout(user.getAbout());
			dto.setAddress(user.getAddress());
			dto.setRole(user.getRole());
			dto.setContact(user.getContact());

			return dto;

		} else {
			throw new CustomException("you are not admin");
		}
	}

	public void deleteUser(String email,String token) {
		User user = userRepository.findByEmail(email).orElseThrow(()->new CustomException("user not found with this email"));
		
		String username=service.getUsernameFromToken(token);
		User admin = userRepository.findByEmail(username).orElseThrow(()->new CustomException("user not found with this email"));

		if(user!=null&&admin.getRole().toString()=="ADMIN") {
			AuthenticationToken tok = tokenRepository.findByToken(token);
			tokenRepository.delete(tok);
			userRepository.delete(user);
		}
	}
	
	public List<UserDto> viewAllUser(String token) {
		String username=service.getUsernameFromToken(token);
		System.out.println(username);
		User user = userRepository.findByEmail(username).orElseThrow(()->new CustomException("user not found with this email"));
		
		if(user.getRole().toString()=="ADMIN") {
			List<User> users = userRepository.findAll();
			
			Stream<UserDto> stream = users.stream().map(u->{
				UserDto dto=new UserDto();
				dto.setUserId(u.getUserId());
				dto.setFirstName(u.getFirstName());
				dto.setLastName(u.getLastName());
				dto.setEmail(u.getEmail());
				dto.setAbout(u.getAbout());
				dto.setAddress(u.getAddress());
				dto.setRole(u.getRole());
				dto.setContact(u.getContact());
				return dto;
			});
			
			List<UserDto> userDtos = stream.collect(Collectors.toList());
			return userDtos;
		}
		throw new CustomException("you are not admin");
	}
	
	public UserDto findById(Integer userId,String token) {
		String username=service.getUsernameFromToken(token);
		User admin = userRepository.findByEmail(username).orElseThrow(()->new CustomException("user not found with this email"));
		if(admin.getRole().toString()=="ADMIN") {
			User user = userRepository.findById(userId).orElseThrow(()->new CustomException("user not found with this id"));
			
				UserDto dto=new UserDto();
				dto.setUserId(userId);
				dto.setFirstName(user.getFirstName());
				dto.setLastName(user.getLastName());
				dto.setEmail(user.getEmail());
				dto.setAbout(user.getAbout());
				dto.setAddress(user.getAddress());
				dto.setRole(user.getRole());
				dto.setContact(user.getContact());
				dto.setPassword(user.getPassword());
				return dto;
			
		}
		throw new CustomException("you are not admin");
	}
	

}
