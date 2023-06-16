package com.nv.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nv.entity.AuthenticationToken;
import com.nv.entity.User;
import com.nv.exception.CustomException;
import com.nv.repository.TokenRepository;

@Service
public class AuthService {

	@Autowired
	private TokenRepository tokenRepository;

	public void saveConfirmationToken(AuthenticationToken authenticationToken) {
		tokenRepository.save(authenticationToken);
	}

	public AuthenticationToken getToken(User user) {
		
		return tokenRepository.findByUser(user);
	}
	
	public User getUser(String token) {
		final AuthenticationToken authenticationToken=tokenRepository.findByToken(token);
		
		if(Objects.isNull(authenticationToken)) {
			return null;
		}
		
		//token is not null
		return authenticationToken.getUser();
	}
	
	public void authenticate(String token) {
		//null check
		if(Objects.isNull(token)) {
			throw new CustomException("token not present");
		}
		
		if(Objects.isNull(getUser(token))) {
			throw new CustomException("token not valid");
		}
	}
	
	public String getUsernameFromToken(String token) {
		AuthenticationToken authenticationToken=tokenRepository.findByToken(token);
		if(Objects.isNull(authenticationToken)) {
			return null;
		}
		return authenticationToken.getUser().getEmail();
	}

}
