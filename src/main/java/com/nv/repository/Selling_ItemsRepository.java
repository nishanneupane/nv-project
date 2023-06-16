package com.nv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nv.entity.Portfolio;
import com.nv.entity.Selling_Items;

public interface Selling_ItemsRepository extends JpaRepository<Selling_Items, Integer> {
	
	List<Selling_Items> findByPortfolio(Portfolio portfolio);

}
