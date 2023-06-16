package com.nv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nv.dto.ApiResponse;
import com.nv.dto.UpdateRequest;
import com.nv.dto.UserDto;
import com.nv.service.impl.UserService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PutMapping("/update")
	public ResponseEntity<UserDto> updateUser(@RequestBody UpdateRequest req,@PathParam("token") String token){
		return new ResponseEntity<UserDto>(userService.editUser(req,token),HttpStatus.CREATED);
	}
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<UserDto> getById(@PathVariable Integer userId,@PathParam("token") String token){
		return new ResponseEntity<UserDto>(userService.findById(userId, token),HttpStatus.CREATED);
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<UserDto>> getAll(@PathParam("token") String token){
		return new ResponseEntity<List<UserDto>>(userService.viewAllUser(token),HttpStatus.CREATED);
	}
	
	@GetMapping("/delete/{email}")
	public ResponseEntity<ApiResponse> getAll(@PathVariable String email,@PathParam("token") String token){
		userService.deleteUser(email,token);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted sucessfully",true),HttpStatus.CREATED);
	}

}
