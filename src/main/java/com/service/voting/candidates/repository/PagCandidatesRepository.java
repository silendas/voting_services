package com.service.voting.candidates.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.service.voting.candidates.model.Candidates;

@Repository
public interface PagCandidatesRepository extends PagingAndSortingRepository<Candidates, Long>, JpaSpecificationExecutor<Candidates>{
    
}
