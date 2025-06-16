   package com.example.demo.ServiceImpl;

import java.lang.System.Logger;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.Entity.Company;
import com.example.demo.Repository.companyRepository;
import com.example.demo.Service.CompanyService;

public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private companyRepository companyRepo;
	
	private org.slf4j.Logger log=LoggerFactory.getLogger(CompanyServiceImpl.class);
	@Override
	public Company SaveCompany(Company c) {
		Company com=companyRepo.save(c);
	
		return com;
	}

	@Override
	public Company getByCompanyName(String companyName) {
		Company com=companyRepo.findByCompanyName(companyName);
		return com;
	}

	@Override
	public List<Company> getByCompanyLocation(String companyLocation) {
		List<Company> com=companyRepo.findByCompanyLocation(companyLocation);
		return com;
	}

	@Override
	public List<Company> getByCompanyLocationAndCompanyPurpose(String companyLocation, String companyPurpose) {
		List<Company> com=companyRepo.findByCompanyLocationAndCompanyPurpose(companyLocation, companyPurpose);
		return com;
	}

}

