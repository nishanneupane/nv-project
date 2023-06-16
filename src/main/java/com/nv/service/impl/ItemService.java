package com.nv.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nv.dto.ApiResponse;
import com.nv.entity.Portfolio;
import com.nv.entity.Selling_Items;
import com.nv.entity.User;
import com.nv.exception.CustomException;
import com.nv.repository.PortfolioRepository;
import com.nv.repository.Selling_ItemsRepository;

@Service
public class ItemService {

	@Autowired
	private Selling_ItemsRepository itemsRepository;
	@Autowired
	private AuthService authService;
	@Autowired
	private PortfolioRepository portfolioRepository;
	@Autowired
	private ModelMapper modelMapper;

	public ApiResponse createItem(Selling_Items item, Integer portfolioId, String token) {
	    authService.authenticate(token);

	    Portfolio portfolio = portfolioRepository.findById(portfolioId)
	            .orElseThrow(() -> new CustomException("portfolio is not available"));

	    Selling_Items items = new Selling_Items();
	    items.setItemName(item.getItemName());
	    items.setDuration(item.getDuration());
	    items.setItemQuantity(item.getItemQuantity());
	    items.setItemDescription(item.getItemDescription());
	    items.setPricePerItem(item.getPricePerItem());
	    items.setPortfolio(portfolio); // Set the portfolio obtained from the repository
	    itemsRepository.save(items); // Save the 'items' object instead of 'item'
	    return new ApiResponse("item added successfully", true);
	}

	
	public List<Selling_Items> getAllItems(Integer portfolioId,String token){
		authService.authenticate(token);
		Portfolio portfolio=portfolioRepository.findById(portfolioId).orElseThrow(()->new CustomException("portfolio not found"));
		
		return itemsRepository.findByPortfolio(portfolio);
	}

}
