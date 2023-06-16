package com.nv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nv.entity.Portfolio;
import com.nv.entity.User;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer>{
	
	List<Portfolio> findByUser(User user);

}
