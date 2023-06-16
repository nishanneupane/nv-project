package com.nv.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nv.entity.Selling_Items;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDto {
	private Integer id;

	private String title;

	@Lob
	private String description;

	private LocalDate createdDate;

	@OneToMany
	private List<Selling_Items> items;

	@ManyToOne
	@JsonIgnore
	private UserDto userDto;
}
