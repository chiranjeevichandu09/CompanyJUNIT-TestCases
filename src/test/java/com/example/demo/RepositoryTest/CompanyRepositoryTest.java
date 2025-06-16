package com.example.demo.RepositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.Entity.Company;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CompanyRepositoryTest {

	@Autowired
	private com.example.demo.Repository.companyRepository companyRepository;

	@Test
	@BeforeEach()
	public void init() {
		Company c1 = new Company(null, "Amazon", "Amazon112", "online shopping", "Hyderbad");
		Company c2 = new Company(null, "Flipkart", "Flipkart112", "online shopping", "Hyderbad");
		Company c3 = new Company(null, "Dell", "Dell001", "Laptop company", "Mumbai");
		Company c4 = new Company(null, "lenovo", "Lenovo002", "electronic gagets shopping", "Delhi");
		companyRepository.save(c1);
		companyRepository.save(c2);
		companyRepository.save(c3);
		companyRepository.save(c4);
	}

	@Test
	@DisplayName("Test save company details")
	void saveCompanyTest() {
		Company company = new Company();
		company.setCompanyId(null);
		company.setCompanyName("Amazon");
		company.setCompanyId("Amazon112");
		company.setCompanyPurpose("online shopping");
		company.setCompanyLocation("Hyderbad");
		Company savedCompany = companyRepository.save(company);
		assertThat(savedCompany).isNotNull();
		assertThat(savedCompany.getCompanyName()).isEqualTo("Amazon");
	}

	@Test
	@DisplayName("Find By companyName")

	void findByCompanyNameTest() {

		Company found = companyRepository.findByCompanyName("Amazon");
		assertThat(found).isNotNull();
		assertThat(found.getCompanyName()).isEqualTo("Amazon");
	}

	@Test
	@DisplayName("Testing FindByComapanyLocation")
	void findByCompanyLocationTest() {

		List<Company> company = companyRepository.findByCompanyLocation("Mumbai");
		assertThat(company).isNotNull();
		assertThat(company).hasSize(1);

	}

	@Test
	@DisplayName("Testing findByCompanyLocationAndCompanyPurpose")
	void findByCompanyLocationAndCompanyPurpose() {
		List<Company> company = companyRepository.findByCompanyLocationAndCompanyPurpose("Hyderbad", "online shopping");

		assertThat(company).isNotNull();
		assertThat(company).hasSize(2);
  
	}
}
