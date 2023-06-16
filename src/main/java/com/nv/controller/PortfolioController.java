package com.nv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nv.dto.ApiResponse;
import com.nv.dto.PortfolioDto;
import com.nv.entity.Portfolio;
import com.nv.service.impl.PortfolioService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/v1/portfolio")
public class PortfolioController {
	@Autowired
	private PortfolioService portfolioService;
	
	@PostMapping("/create")
	public ResponseEntity<Portfolio> createPortfolio(@RequestBody Portfolio portfolio,@PathParam("token") String token){
		return new ResponseEntity<Portfolio>(portfolioService.createPortFolio(portfolio, token),HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{portfolioId}")
	public ResponseEntity<PortfolioDto> updatePortfolio(@RequestBody PortfolioDto portfolio,@PathVariable Integer portfolioId ,@PathParam("token") String token){
		return new ResponseEntity<PortfolioDto>(portfolioService.updatePortFolio(portfolio, portfolioId, token),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{portfolioId}")
	public ResponseEntity<ApiResponse> deletePortfolio(@PathVariable Integer portfolioId ,@PathParam("token") String token){
		portfolioService.deletePortfolio(portfolioId, token);
		return new ResponseEntity<ApiResponse>(new ApiResponse("portfolio deleted sucessfully",true),HttpStatus.OK);
	}
	
	@GetMapping("/get/{portfolioId}")
	public ResponseEntity<PortfolioDto> getPortfolio(@PathVariable Integer portfolioId ,@PathParam("token") String token){
		return new ResponseEntity<PortfolioDto>(portfolioService.getPortFolio(portfolioId, token),HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<PortfolioDto>> getAll(@PathParam("token") String token){
		return new ResponseEntity<List<PortfolioDto>>(portfolioService.getAllPortFolio(token),HttpStatus.OK);
	}
}
