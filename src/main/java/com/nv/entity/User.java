package com.nv.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	@NotNull(message = "name required")
	private String firstName;
	
	@NotNull(message = "name required")
	private String lastName;
	
	@Email
	@Column(name="email",unique = true)
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
	private boolean isApproved;
	
	@Enumerated(EnumType.STRING)
	private Role role;



}
