package com.microservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.microservice.MicroserviceexampleeApplication;
import com.microservice.entity.ExternalServicesConfiguration;
import com.microservice.repository.GenericEntityRepository;


@SpringBootTest(classes = MicroserviceexampleeApplication.class)
public class SpringBootJPAIntegrationTest {

	 @Autowired
	    private GenericEntityRepository genericEntityRepository;

	    @Test
	    public void givenGenericEntityRepository_whenSaveAndRetreiveEntity_thenOK() {
	    	
	    	
	    	ExternalServicesConfiguration genericConfiguration = new ExternalServicesConfiguration();
	    	genericConfiguration.setId(1);
	    	genericConfiguration.setIsConfigured(true);
	    	genericConfiguration.setName("Selenium");
	    	genericConfiguration =		genericEntityRepository
	          .save(genericConfiguration);
	    	/*ExternalServicesConfiguration foundEntity = genericEntityRepository.getById(1);
	     
	 
	        assertNotNull(foundEntity);
	        assertEquals(genericConfiguration.getName(), foundEntity.getName());*/
	    }
}
