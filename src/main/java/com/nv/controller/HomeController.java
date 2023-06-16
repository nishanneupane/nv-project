package com.nv.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/")
	public String sayHello() {
		return "Welcome to naulo venture";
	}
	
	@GetMapping("/secure")
	public String securedEndpoint() {
		return "Hello from secured endpoint";
	}

}
