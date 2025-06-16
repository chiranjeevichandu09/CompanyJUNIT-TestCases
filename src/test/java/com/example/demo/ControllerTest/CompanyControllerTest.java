package com.example.demo.ControllerTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.example.demo.Controller.CompanyController;
import com.example.demo.Entity.Company;
import com.example.demo.ServiceImpl.CompanyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {

	@Autowired
	public MockMvc mockMvc;

	@MockBean
	private CompanyServiceImpl companySer;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void saveCompanyTest() throws Exception {
		Company com = new Company(null, "Amazon", "Amazon001", "online shopping", "Hyderabad");
		/**
		 * Creates a Company object without an ID, simulating a new company to be saved.
		 * This is the object you are going to send as input to the controller via a POST request.
		 */
		Company savedCom = new Company(1, "Amazon", "Amazon001", "online shopping", "Hyderabad");
		/**
		 * This is the mocked return value after saving.
		 * Usually, in real apps, after saving to DB, the company will have an id generated (here it's 1).
		 */
		when(companySer.SaveCompany(com)).thenReturn(savedCom);
		/**
		 * This mocks the service layer (companySer) which the controller depends on.
		 * SaveCompany(com) is a method in your service that saves the company.
		 * It ensures that when the controller calls this method, it will return savedCom instead of executing real logic.
		 */
		
	ResultActions m	=mockMvc.perform(post("/save").contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(savedCom)));
	/**
	 * mockMvc.perform(...) sends a simulated HTTP POST request to /save.
	 * .contentType(...) sets the request Content-Type header to application/json.
	 * .content(...) converts the com object to a JSON string using Jackson's ObjectMapper.
	 * ResultActions m: Stores the result of the performed request so you can chain assertions on it.
	 * This doesn't hit the real controller logic — it's a mock request-response flow.


	 */
System.out.println(objectMapper.writeValueAsString(savedCom));
	m.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.companyName").value("Amazon"));
/**
 * .andExpect(status().isCreated()): Asserts that the response status is 201 (Created).
 * .andExpect(jsonPath("$.id").value(1)): Checks that the response JSON contains id = 1.
 * .andExpect(jsonPath("$.companyName").value("Amazon")): Checks the companyName in the response.


 */
	}

	@Test
	void getByCompanyNameTest() throws Exception {
		// Company com = new Company(null, "Amazon", "Amazon001", "online shopping",
		// "Hyderabad");
		Company savedCom = new Company(1, "Amazon", "Amazon001", "online shopping", "Hyderabad");
		when(companySer.getByCompanyName("Amazon")).thenReturn(savedCom);

		mockMvc.perform(get("/getByCompanyName/Amazon").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(savedCom))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.companyName").value("Amazon"));
	}

	@Test
	void getByCompanyLocation() throws Exception {
		Company savedCom = new Company(1, "Amazon", "Amazon001", "online shopping", "Hyderabad");
		Company savedCom1 = new Company(2, "Flipkart", "Flipkart111", "online shopping", "Hyderabad");
List<Company> c=Arrays.asList(savedCom,savedCom1);
		when(companySer.getByCompanyLocation("Hyderabad")).thenReturn(c);
		
		mockMvc.perform(get("/getByCompanyLocation/Hyderabad").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(c)))
		.andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
	}
	@Test
	void getByCompanyLocationAndCompanyPurposeTest() throws Exception{
		Company savedCom = new Company(1, "Amazon", "Amazon001", "online shopping", "Hyderabad");
		Company savedCom1 = new Company(2, "Flipkart", "Flipkart111", "online shopping", "Hyderabad");
List<Company> c=Arrays.asList(savedCom,savedCom1);
when(companySer.getByCompanyLocationAndCompanyPurpose("Hyderabad", "online shopping")).thenReturn(c);
mockMvc.perform(get("/getByCompanyLocationAndCompanyPurpose/Hyderabad/online shopping").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(c))).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
	}
}
