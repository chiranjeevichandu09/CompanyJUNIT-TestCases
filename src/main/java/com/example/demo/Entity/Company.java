package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Company name is required")
	@Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
	private String companyName;

	@NotBlank(message = "Company ID is required")
	@Size(min = 5, max = 20, message = "Company ID must be between 5 and 20 characters")
	private String companyId;

	private String companyPurpose;

	private String companyLocation;
}