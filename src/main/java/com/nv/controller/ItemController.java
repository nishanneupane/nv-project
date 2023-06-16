package com.nv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nv.dto.ApiResponse;
import com.nv.entity.Selling_Items;
import com.nv.service.impl.ItemService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/v1/portfolio/items")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@PostMapping("/add/{portfolioId}")
	public ResponseEntity<ApiResponse> addNewItems(@RequestBody Selling_Items items,@PathVariable Integer portfolioId,@PathParam("token") String token){
		return new ResponseEntity<ApiResponse>(itemService.createItem(items, portfolioId, token),HttpStatus.CREATED);
	}
	
	@GetMapping("/get/{portfolioId}")
	public ResponseEntity<List<Selling_Items>> getItems(@PathVariable Integer portfolioId,@PathParam("token") String token){
		return new ResponseEntity<List<Selling_Items>>(itemService.getAllItems(portfolioId, token),HttpStatus.CREATED);
	}

}
