package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Case;

@Repository
public interface CaseRepository extends CrudRepository<Case, Long> {

	Case findByCaseId(Long caseId);
}
