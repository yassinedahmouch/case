package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a case response.
 * 
 * @author Yassine
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseResponse {

	private LocalDateTime creationDate;
	private LocalDateTime lastUpdateDate;
	private String title;
	private String description;
}
