package com.microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.entity.ExternalServicesConfiguration;

public interface GenericEntityRepository extends JpaRepository<ExternalServicesConfiguration, Integer> {
	
}
