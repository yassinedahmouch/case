package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is a representation of a case item.
 * 
 * @author Yassine
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "case")
public class Case {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long caseId;
	private LocalDateTime creationDate;
	private LocalDateTime lastUpdateDate;
	@NotNull(message = "Title cannot be null")
	@NotEmpty(message = "Title cannot be empty")
	private String title;
	@Size(max = 255, message = "Description must be at most 255 characters")
	private String description;
}
