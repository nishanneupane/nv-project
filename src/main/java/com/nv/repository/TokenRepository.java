package com.nv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nv.entity.AuthenticationToken;
import com.nv.entity.User;

public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer>{

	AuthenticationToken findByUser(User user);

	AuthenticationToken findByToken(String token);

}
