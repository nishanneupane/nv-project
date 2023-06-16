package com.nv.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nv.dto.PortfolioDto;
import com.nv.entity.Portfolio;
import com.nv.entity.User;
import com.nv.exception.CustomException;
import com.nv.repository.PortfolioRepository;
import com.nv.repository.Selling_ItemsRepository;


@Service
public class PortfolioService {

	@Autowired
	private AuthService authService;
	@Autowired
	private PortfolioRepository portfolioRepository;
	@Autowired
	private Selling_ItemsRepository itemsRepository;
	@Autowired
	private ModelMapper modelMapper;

	public Portfolio createPortFolio(Portfolio dto, String token) {
		authService.authenticate(token);
		User user = authService.getUser(token);

//		Portfolio portfolio = portfolioRepository.findByUser(user);
		if (user == null) {
			throw new CustomException("Invalid token");
		}

		Portfolio pd = new Portfolio();
		pd.setTitle(dto.getTitle());
		pd.setDescription(dto.getDescription());
		pd.setCreatedDate(LocalDate.now());
		pd.setUser(user);
//		pd.setItems(itemsRepository.findByPortfolio(pd));
		portfolioRepository.save(pd);
		return pd;
	}

	public PortfolioDto updatePortFolio(PortfolioDto dto, Integer portfolioId, String token) {
		authService.authenticate(token);
		User user = authService.getUser(token);
		if (user == null) {
			throw new CustomException("Invalid token");
		}

		Portfolio d1 = new Portfolio();
		d1.setTitle(dto.getTitle());
		d1.setDescription(dto.getDescription());
		d1.setCreatedDate(LocalDate.now());
		d1.setUser(user);
		d1.setItems(null);

		portfolioRepository.save(d1);
		return dto;
	}

	public PortfolioDto getPortFolio(Integer portfolioId, String token) {
		authService.authenticate(token);
		User user = authService.getUser(token);
		if (user == null) {
			throw new CustomException("Invalid token");
		}
		Portfolio portfolio = portfolioRepository.findById(portfolioId)
				.orElseThrow(() -> new CustomException("portfolio not found"));

		PortfolioDto dto = modelMapper.map(portfolio, PortfolioDto.class);
		return dto;
	}

	public List<PortfolioDto> getAllPortFolio(String token) {
		authService.authenticate(token);
		User user = authService.getUser(token);
		if (user == null) {
			throw new CustomException("Invalid token");
		}
		List<Portfolio> portfolio = portfolioRepository.findByUser(user);

		return portfolio.stream().map(p -> modelMapper.map(p, PortfolioDto.class)).collect(Collectors.toList());
	}

	public void deletePortfolio(Integer portfolioId,String token) {
		authService.authenticate(token);
		User user = authService.getUser(token);
		if (user == null) {
			throw new CustomException("Invalid token");
		}
		Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow(()->new CustomException("portfolio not found"));
		portfolioRepository.delete(portfolio);
	}
}
