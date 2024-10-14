package com.service.voting.tps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.service.voting.tps.model.TPSArea;

@Repository
public interface TPSAreaRepository extends JpaRepository<TPSArea, Long>, JpaSpecificationExecutor<TPSArea>{
    
}
