package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.dto.CaseRequest;
import com.example.demo.model.Case;
import com.example.demo.service.CaseService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class CaseControllerTests {

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private CaseService caseService;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Test
	void testCaseCreation() throws Exception {
		LocalDateTime currentDateTime = LocalDateTime.now();

		Case caseElement = Case.builder()
							.title("Election")
							.description("Election will start on 2025")
							.creationDate(currentDateTime)
							.lastUpdateDate(currentDateTime).build();

		Mockito.when(caseService.createCase(Mockito.any(CaseRequest.class)))
			   .thenReturn(caseElement);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/cases")
										.content(objectMapper.writeValueAsString(modelMapper.map(caseElement, CaseRequest.class)))
										.contentType(MediaType.APPLICATION_JSON)
										.accept(MediaType.APPLICATION_JSON));

		response.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.title", is(caseElement.getTitle())))
				.andExpect(jsonPath("$.description", is(caseElement.getDescription())));
	}
	
	@Test
	void testGetCaseById() throws Exception {

		LocalDateTime currentDateTime = LocalDateTime.now();
		long caseId = 1L;
		
		Case caseElement = Case.builder()
							.caseId(caseId)
							.title("Election")
							.description("Election will start on 2025")
							.creationDate(currentDateTime)
							.lastUpdateDate(currentDateTime).build();
		
		Mockito.when(caseService.findCaseById(caseId)).thenReturn(caseElement);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders
										.get("/cases/{id}", caseId));

		response.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is(caseElement.getTitle())))
				.andExpect(jsonPath("$.description", is(caseElement.getDescription())));
	}
	
	@Test
	void testCaseUpdate() throws Exception {
		LocalDateTime currentDateTime = LocalDateTime.now();
		
		long caseId = 1L;
		
		Case caseElementSaved = Case.builder()
							.caseId(caseId)
							.title("Election")
							.description("Election will start on 2025")
							.creationDate(currentDateTime)
							.lastUpdateDate(currentDateTime).build();

		Case caseElementUpdated = Case.builder()
								      .caseId(caseId)
								      .title("Election")
								      .description("Election will start on 2026")
								      .creationDate(currentDateTime)
								      .lastUpdateDate(currentDateTime).build();

		Mockito.when(caseService.findCaseById(caseId))
			   .thenReturn(caseElementSaved);
		
		Mockito.when(caseService.updateCase(Mockito.any(Long.class), Mockito.any(CaseRequest.class)))
		   	   .thenReturn(caseElementUpdated);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/cases/{id}", caseId)
										.content(objectMapper.writeValueAsString(modelMapper.map(caseElementUpdated, CaseRequest.class)))
										.contentType(MediaType.APPLICATION_JSON)
										.accept(MediaType.APPLICATION_JSON));

		response.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is(caseElementUpdated.getTitle())))
				.andExpect(jsonPath("$.description", is(caseElementUpdated.getDescription())));
	}
	
	@Test
	void testDeleteCaseById() throws Exception {

		long caseId = 1L;
		
		Mockito.when(caseService.deleteById(caseId))
			   .thenReturn(true);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders
										.delete("/cases/{id}", caseId));

		response.andDo(print())
				.andExpect(status().isOk());
	}
	
	
}
