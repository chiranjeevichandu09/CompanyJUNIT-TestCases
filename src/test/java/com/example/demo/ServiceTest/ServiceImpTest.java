package com.example.demo.ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.Entity.Company;
import com.example.demo.Repository.companyRepository;
import com.example.demo.ServiceImpl.CompanyServiceImpl;

//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ServiceImpTest {
	 @Mock
	    private companyRepository companyRepo;

	    @InjectMocks
	    private CompanyServiceImpl companyService;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }
	@Test
	void saveCompanyTest() {
		// Arrange
		Company c1 = new Company(null, "Amazon", "Amazon112", "online shopping", "Hyderbad");
		when(companyRepo.save(c1)).thenReturn(new Company(1, "Amazon", "Amazon112", "online shopping", "Hyderbad"));
		// Act
		Company com = companyService.SaveCompany(c1);
		// Assert
		assertThat(com).isNotNull();
		assertThat(com.getId()).isEqualTo(1);
		verify(companyRepo, times(1)).save(c1);
	}

	@Test
	void getByCompanyNameTest() {
		// Arrange
		Company c1 = new Company(null, "Flipkart", "flipkart001", "online shopping", "chennai");

		// Act
		when(companyRepo.findByCompanyName(c1.getCompanyName()))
				.thenReturn(new Company(null, "Flipkart", "flipkart001", "online shopping", "chennai"));

		Company com = companyService.getByCompanyName("Flipkart");
		// Assert
		assertThat(com).isNotNull();
		assertThat(com.getCompanyName()).isEqualTo("Flipkart");
		verify(companyRepo, times(1)).findByCompanyName("Flipkart");

	}


	 @Test
	    void getByCompanyLocationTest() {
	        // Arrange
	        Company c3 = new Company(null, "Dell", "Dell001", "Laptop company", "Hyderabad");
	        Company c4 = new Company(null, "lenovo", "Lenovo002", "electronic gadgets shopping", "Chennai");
	        List<Company> company = Arrays.asList(c3);

	        when(companyRepo.findByCompanyLocation("Hyderabad")).thenReturn(company);
	        

	        // Act
	        List<Company> com = companyService.getByCompanyLocation("Hyderabad");

	        // Assert
	        assertThat(com).isNotNull();
	        assertThat(com).hasSize(1);
	    }
	

	@Test
	void getByCompanyLocationAndCompanyPurposeTest() {
		Company c3 = new Company(null, "Dell", "Dell001", "Laptop company", "Hyderbad");
		Company c4 = new Company(null, "lenovo", "Lenovo002", "Laptop company", "Hyderbad");
		List<Company> com = Arrays.asList(c3, c4);
		when(companyRepo.findByCompanyLocationAndCompanyPurpose("Hyderbad", "Laptop company")).thenReturn(com);
		List<Company> c = companyService.getByCompanyLocationAndCompanyPurpose("Hyderbad", "Laptop company");
		assertThat(c).isNotNull();
		assertThat(c).hasSize(2);
	}

}
