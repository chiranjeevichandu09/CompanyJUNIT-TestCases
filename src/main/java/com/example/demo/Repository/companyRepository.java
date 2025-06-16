package com.example.demo.Repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Company;

@Repository
public interface companyRepository extends JpaRepository<Company, Integer> {

	public Company save(Company c);

	public Company findByCompanyName(String companyName);

	public List<Company> findByCompanyLocation(String companyLocation);

	public List<Company> findByCompanyLocationAndCompanyPurpose(String companyLocation, String companyPurpose);

}
