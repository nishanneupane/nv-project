package com.nv.dto;


import com.nv.entity.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private Integer userId;

	@NotNull(message = "name is required")
	private String firstName;

	@NotNull(message = "name is required")
	private String lastName;

	@Email
	@NotNull(message = "email required")
	private String email;

	@NotNull(message = "password required")
	private String password;

	@NotNull(message = "address required")
	private String address;

	@NotNull(message = "contact required")
	private String contact;

	@NotNull(message = "about required")
	@Lob
	private String about;
	
	private String image;
	
	@Enumerated(EnumType.STRING)
	private Role role;

}
