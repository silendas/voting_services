package com.service.voting.users.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.service.voting.users.models.Registrations;

@Repository
public interface PagRegistrationRepository extends PagingAndSortingRepository<Registrations, Long>, JpaSpecificationExecutor<Registrations>{
    
}
