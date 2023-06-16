package com.nv.controller;

import java.io.IOException;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nv.dto.ApiResponse;
import com.nv.dto.AuthRequest;
import com.nv.dto.AuthResponse;
import com.nv.dto.RegisterResponse;
import com.nv.dto.UpdateRequest;
import com.nv.dto.UserDto;
import com.nv.entity.User;
import com.nv.service.impl.AuthService;
import com.nv.service.impl.FileService;
import com.nv.service.impl.UserService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;



@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("http://localhost:3000/")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) throws Exception {
		AuthResponse login = userService.login(request);

		return new ResponseEntity<AuthResponse>(new AuthResponse(login.getToken(),true), HttpStatus.OK);
	}

	
	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody User user){
		RegisterResponse registerUser = userService.registerUser(user);
	
		return new ResponseEntity<RegisterResponse>(registerUser,HttpStatus.CREATED);
	}
	
	
	@PostMapping("/addimg/{userId}")
	@Transactional
	public ResponseEntity<UserDto> uploadPostImage(@RequestParam(name = "image") MultipartFile multipartFile,
			@PathVariable Integer userId,@PathParam("token") String token) throws IOException {

		UserDto userDto = userService.findById(userId,token);
		String filename = fileService.uploadImage(path, multipartFile);

		UpdateRequest req=new UpdateRequest();
		req.setFirstName(userDto.getFirstName());
		req.setLastName(userDto.getLastName());
		req.setAbout(userDto.getAbout());
		req.setAddress(userDto.getAddress());
		req.setContact(userDto.getContact());
		req.setPassword(userDto.getPassword());
		req.setImage(multipartFile.getName());
		req.setEmail(userDto.getEmail());
		
		UserDto editUser = userService.editUser(req,token);

		return new ResponseEntity<UserDto>(editUser, HttpStatus.OK);
	}

}
