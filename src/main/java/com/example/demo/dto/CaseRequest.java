package com.example.demo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a case request.
 * 
 * @author Yassine
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseRequest {

	@NotNull(message = "Title cannot be null")
	@NotEmpty(message = "Title cannot be empty")
	private String title;
	@Size(max = 255, message = "Description must be at most 255 characters")
	private String description;
}
