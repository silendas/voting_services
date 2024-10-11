package com.service.voting.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.service.voting.users.models.Registrations;

@Repository
public interface RegistrationRepository extends JpaRepository<Registrations, Long>, JpaSpecificationExecutor<Registrations>{
    
}
