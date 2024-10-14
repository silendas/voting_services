package com.service.voting.tps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.service.voting.tps.model.TPS;

@Repository
public interface TPSRepository extends JpaRepository<TPS, Long>, JpaSpecificationExecutor<TPS>{
    
}
