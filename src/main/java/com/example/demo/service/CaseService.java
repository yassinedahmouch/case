package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CaseRequest;
import com.example.demo.model.Case;
import com.example.demo.repository.CaseRepository;

/**
 * Case service.
 * 
 * @author Yassine
 *
 */
@Service
public class CaseService {

	@Autowired
	private CaseRepository caseRepository;
	
	public Case findCaseById(Long caseId) {
		return caseRepository.findByCaseId(caseId);
	}
	
	public Case createCase(CaseRequest caseRequest) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		return caseRepository.save(Case.builder().title(caseRequest.getTitle())
												 .description(caseRequest.getDescription())
												 .creationDate(currentDateTime)
												 .lastUpdateDate(currentDateTime)
												 .build());
	}
	
	public Case updateCase(Long caseId, CaseRequest caseRequest) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		Case existingCase = caseRepository.findByCaseId(caseId);
		existingCase.setTitle(caseRequest.getTitle());
		existingCase.setDescription(caseRequest.getDescription());
		existingCase.setLastUpdateDate(currentDateTime);
		return caseRepository.save(existingCase);
	}
	
	public boolean deleteById(Long caseId) {
		boolean caseDeleted = false;
		Case existingCase =  caseRepository.findByCaseId(caseId);
		
		if(existingCase != null) {
		  caseRepository.delete(existingCase);
		  caseDeleted = true;
		}
		
		return caseDeleted;
	}
}
