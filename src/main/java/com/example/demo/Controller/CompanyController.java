package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Company;
import com.example.demo.ServiceImpl.CompanyServiceImpl;

@RestController
public class CompanyController {
	@Autowired
	public CompanyServiceImpl companySer;
	
	
	@PostMapping("/save")
	public ResponseEntity<Company> saveCompany(@RequestBody Company com)
	{
		Company c=companySer.SaveCompany(com);
		return new ResponseEntity<Company>(c,HttpStatus.CREATED);
		
	}
	@GetMapping("/getByCompanyName/{companyName}")
	public ResponseEntity<Company> getByCompanyName(@PathVariable String companyName)
	{
		Company com=companySer.getByCompanyName( companyName);
		return ResponseEntity.ok(com);
	}
	
	@GetMapping("/getByCompanyLocation/{companyLocation}")
	public ResponseEntity<List<Company>> getByCompanyLocation(@PathVariable String companyLocation)
	{
		List<Company> com=companySer.getByCompanyLocation( companyLocation);
		return ResponseEntity.ok(com);
	}
	
	@GetMapping("/getByCompanyLocationAndCompanyPurpose/{companyLocation}/{companyPurpose}")
	public ResponseEntity<List<Company>> getByCompanyLocationAndCompanyPurpose(@PathVariable String companyLocation,@PathVariable String companyPurpose )
	{
		List<Company> com=companySer.getByCompanyLocationAndCompanyPurpose(companyLocation,companyPurpose);
		return ResponseEntity.ok(com);
	}

}
