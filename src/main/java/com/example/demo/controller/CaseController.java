package com.example.demo.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CaseRequest;
import com.example.demo.dto.CaseResponse;
import com.example.demo.message.ErrorMessage;
import com.example.demo.model.Case;
import com.example.demo.service.CaseService;


/**
 * Rest controller containing CRUD functionality of case item.
 * 
 * @author Yassine
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "cases", produces = APPLICATION_JSON_VALUE)
public class CaseController {
	
	private static Logger LOG = LoggerFactory.getLogger(CaseController.class);

	@Autowired
	private CaseService caseService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Retrieve a specific case by its id.
	 * 
	 * @param caseId is a {@link Long}.
	 * @return a {@link ResponseEntity {@link<{@link Case}>.
	 */
	@GetMapping("/{caseId}")
	public ResponseEntity<Case> getCaseById(@PathVariable Long caseId) {
		LOG.info("=> getCaseById(caseId :{}).", caseId);
		
		Case caseElement = caseService.findCaseById(caseId);

		if (caseElement != null) {
			// return the retrieved case
			LOG.debug("Retrivied case : {}.", caseElement);
			LOG.info("<= getCaseById().");

			return ResponseEntity.ok(caseElement);
		} else {
			LOG.debug("Case corresponding to the id : {} not found", caseId);
			LOG.info("<= getCaseById().");

			return ResponseEntity.notFound().build();
		}
		
	}

	/**
	 * Create a new case.
	 * 
	 * @param caseRequest is a {@link CaseRequest}.
	 * @return a {@link ResponseEntity {@link<{@link CaseResponse}>.
	 */
	@PostMapping
	public ResponseEntity<CaseResponse> createCase(@RequestBody @Valid CaseRequest caseRequest) {
		LOG.info("=> createCase(caseRequest :{}).", caseRequest);

		// Validate and create the new case
		Case createdCase = caseService.createCase(caseRequest);
		// Map the result to the case response
		CaseResponse caseResponse = modelMapper.map(createdCase, CaseResponse.class);

		LOG.debug("Case response returned : {}.", caseResponse);
		LOG.info("<= createCase().");

		// Return the created item in the response
		return ResponseEntity.status(HttpStatus.CREATED).body(caseResponse);
	}
	
	/**
	 * Update an existing case linked to an id with new informations contained in a caseRequest given as an input.
	 * 
	 * @param caseId is a {@link Long}.
	 * @param caseRequest is a {@link CaseRequest}.
	 * @return a {@link ResponseEntity {@link<{@link CaseResponse}>.
	 */
	@PutMapping("/{caseId}")
	public ResponseEntity<CaseResponse> updateCase(@PathVariable Long caseId,
												   @RequestBody @Valid CaseRequest caseRequest) {
		LOG.info("=> updateCase(caseId : {}, caseRequest :{}).", caseId, caseRequest);

		// Validate and update the given case
		Case createdCase = caseService.updateCase(caseId, caseRequest);
		// Map the result to the case response
		CaseResponse caseResponse = modelMapper.map(createdCase, CaseResponse.class);

		LOG.debug("Case response returned : {}.", caseResponse);
		LOG.info("<= updateCase().");

		// Return the updated item in the response
		return ResponseEntity.ok().body(caseResponse);
	}
	
	/**
	 * Deleting a case by its id.
	 * 
	 * @param caseId is a {@link Long}.
	 * @return a {@link ResponseEntity {@link<{@link String}>.
	 */
	@DeleteMapping("/{caseId}")
	public ResponseEntity<String> deleteCase(@PathVariable Long caseId) {
		LOG.info("=> deleteCase(caseId : {}).", caseId);

		boolean caseDeleted = caseService.deleteById(caseId);
		
		if(caseDeleted) {
			LOG.debug("Case corresponding to id : {} deleted.", caseId);
			LOG.info("<= deleteCase().");

			return ResponseEntity.ok().body("Case corresponding to id : " + caseId + " was deleted successfully.");
		} else {
			LOG.debug("Case corresponding to id : {} can not be deleted because it does not exist.", caseId);
			LOG.info("<= deleteCase().");

			return ResponseEntity.badRequest().body("Case corresponding to id : " + caseId + "can not be deleted because it does not exist.");
		}
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	private ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex) {
		List<String> errors = new ArrayList<>();

		// Extract and process validation errors
		BindingResult bindingResult = ex.getBindingResult();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String errorMessage = fieldError.getField() + ": " + fieldError.getDefaultMessage();
			errors.add(errorMessage);
		}
		StringBuilder errorMessageBuilder = new StringBuilder("The errors are the following : ");
		for (int i = 0; i <= errors.size() - 1; i++) {
			errorMessageBuilder.append(errors.get(i));
			if (i != errors.size() - 1) {
				errorMessageBuilder.append(" , ");
			}
		}
		ErrorMessage responseMessage = ErrorMessage.builder().message(errorMessageBuilder.toString()).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);

	}

}
