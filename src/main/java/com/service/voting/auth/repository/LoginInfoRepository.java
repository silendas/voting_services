package com.service.voting.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.service.voting.auth.model.LoginInfo;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfo, Long>, JpaSpecificationExecutor<LoginInfo>{
    
}
