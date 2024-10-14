package com.service.voting.candidates.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.service.voting.candidates.model.Candidates;

@Repository
public interface CandidatesRepository extends JpaRepository<Candidates, Long>, JpaSpecificationExecutor<Candidates>{
    Optional<Candidates> findByPicture(String picture);
}
