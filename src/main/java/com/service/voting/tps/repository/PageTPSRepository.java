package com.service.voting.tps.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.service.voting.tps.model.TPS;

@Repository
public interface PageTPSRepository extends PagingAndSortingRepository<TPS, Long>, JpaSpecificationExecutor<TPS>{
    
}
