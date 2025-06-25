package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.Company;
import org.springframework.stereotype.Service;

@Service
public interface CompanyService {
	
	public Company SaveCompany(Company c);
	
	public Company getByCompanyName(String companyName);
	
	public List<Company> getByCompanyLocation(String companyLocation);
	
	public List<Company> getByCompanyLocationAndCompanyPurpose(String companyLocation, String companyPurpose);

}
