package com.example.demo.ControllerTest;

import static org.mockito.Mockito.when;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import com.example.demo.ExceptionHandling.CompanyNotFoundException;


import com.example.demo.Repository.companyRepository;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;

import com.example.demo.Controller.CompanyController;
import com.example.demo.Entity.Company;
import com.example.demo.ServiceImpl.CompanyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CompanyController.class)
@AutoConfigureRestDocs(outputDir="target/generated-snippets")
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
			.content(objectMapper.writeValueAsString(com)));
	/**
	 * mockMvc.perform(...) sends a simulated HTTP POST request to /save.
	 * .contentType(...) sets the request Content-Type header to application/json.
	 * .content(...) converts the com object to a JSON string using Jackson's ObjectMapper.
	 * ResultActions m: Stores the result of the performed request so you can chain assertions on it.
	 * This doesn't hit the real controller logic — it's a mock request-response flow.


	 */
System.out.println(objectMapper.writeValueAsString(savedCom));
	m.andExpect(status().isCreated()).andDo(print())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.companyName").value("Amazon")).andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
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
				.content(objectMapper.writeValueAsString(savedCom))).andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.companyName").value("Amazon")).andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
	}

	@Test
	void getByCompanyLocation() throws Exception {
		Company savedCom = new Company(1, "Amazon", "Amazon001", "online shopping", "Hyderabad");
		Company savedCom1 = new Company(2, "Flipkart", "Flipkart111", "online shopping", "Hyderabad");
List<Company> c=Arrays.asList(savedCom,savedCom1);
		when(companySer.getByCompanyLocation("Hyderabad")).thenReturn(c);

		mockMvc.perform(get("/getByCompanyLocation/Hyderabad").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(c)))
		.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.length()").value(2)).andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
	}
	@Test
	void getByCompanyLocationAndCompanyPurposeTest() throws Exception {
		Company savedCom = new Company(1, "Amazon", "Amazon001", "online shopping", "Hyderabad");
		Company savedCom1 = new Company(2, "Flipkart", "Flipkart111", "online shopping", "Hyderabad");
		List<Company> c = Arrays.asList(savedCom, savedCom1);

		when(companySer.getByCompanyLocationAndCompanyPurpose("Hyderabad", "online shopping")).thenReturn(c);

		mockMvc.perform(get("/getByCompanyLocationAndCompanyPurpose/Hyderabad/online shopping")
						.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(c)))

				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.length()").value(2))
				.andDo(document("{methodName}",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint())
				));
	}

	@Test
	void saveCompany_InvalidInput_ReturnsBadRequestAndDetailedErrors() throws Exception {
		// Create a Company object with invalid data (e.g., missing name and invalid ID)
		Company invalidInput = new Company(null, "", "BAD", "Online Shopping", "Hyderabad");


		ResultActions result = mockMvc.perform(post("/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidInput)));

		result.andExpect(status().isBadRequest())
				.andDo(print()) // Print the response for debugging
				.andDo(document("save-company-invalid-input",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint()),
						responseFields(
								fieldWithPath("timestamp").description("The time the error occurred"),
								fieldWithPath("status").description("HTTP status code (400)"),
								fieldWithPath("error").description("Error type (Bad Request)"),
								fieldWithPath("message").description("List of validation error messages"),
								fieldWithPath("path").description("The request URI")
						)
				));
	}

	@Test
	void getByCompanyName_Invalid_Input() throws Exception {
		// Mock the service to throw a CompanyNotFoundException when searching for "Flipkart"
		when(companySer.getByCompanyName("Flipkart")).thenThrow(new CompanyNotFoundException("Company with name 'Flipkart' not found"));		ResultActions result = mockMvc.perform(get("/getByCompanyName/Flipkart")
				.contentType(MediaType.APPLICATION_JSON)); // Removed content()

		result.andExpect(status().isNotFound())
				.andDo(print())
				.andExpect(jsonPath("$.message").value("Company with name 'Flipkart' not found")) // Correct assertion
				.andDo(document("get-By-company-Name-Invalid",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint()),
						responseFields(
								fieldWithPath("timestamp").description("The time the error occurred"),
								fieldWithPath("status").description("HTTP status code (404)"),
								fieldWithPath("error").description("Error type (Not Found)"),
								fieldWithPath("message").description("Error message"),
								fieldWithPath("path").description("The request URI")
						)
				));
	}
	@Test
	void getByCompanyLocation_Invalid_Input() throws Exception
	{
		when(companySer.getByCompanyLocation("chennai")).thenThrow(new CompanyNotFoundException("No company details for this company location"));
		ResultActions result=mockMvc.perform(get("/getByCompanyLocation/chennai").contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound())

				.andDo(print())
				.andExpect(jsonPath("$.message").value("No company details for this company location")) // Correct assertion

				.andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),responseFields(
						fieldWithPath("timestamp").description("The time the error occurred"),
						fieldWithPath("status").description("HTTP status code (404)"),
						fieldWithPath("error").description("Error type (Not Found)"),
						fieldWithPath("message").description("Error message"),
						fieldWithPath("path").description("The request URI")
				)));

	}

	@Test
void getByCompanyLocationAndCompanyPurposeTest_Invalid_Input()throws Exception
{
	when(companySer.getByCompanyLocationAndCompanyPurpose("Hyderabad","online shopping")).thenThrow(new CompanyNotFoundException("Data not found based on the company Location and company purpose"));


ResultActions	m=mockMvc.perform(get("/getByCompanyLocationAndCompanyPurpose/Hyderabad/online shopping").contentType(MediaType.APPLICATION_JSON));


	m.andExpect(status().isNotFound()).
			andDo(print())
			.andExpect(jsonPath("$.message").value("Data not found based on the company Location and company purpose"))
			.andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),responseFields(
					fieldWithPath("timestamp").description("The time the error occurred"),
					fieldWithPath("status").description("HTTP status code (404)"),
					fieldWithPath("error").description("Error type (Not Found)"),
					fieldWithPath("message").description("Error message"),
					fieldWithPath("path").description("The request URI")
			)));

}

}
