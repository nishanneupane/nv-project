package com.nv.dto;

import com.nv.entity.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest {
	private String firstName;
	
	private String lastName;
	
	@Email
	private String email;
	
	private String password;
	
	private String address;
	
	private String contact;
	
	@Lob
	private String about;
	
	private String image;
	
	private boolean isApproved;
	
	@Enumerated(EnumType.STRING)
	private Role role;

}
