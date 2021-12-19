DROP TABLE IF EXISTS ExternalServicesConfiguration;  
CREATE TABLE ExternalServicesConfiguration (  
id INT AUTO_INCREMENT  PRIMARY KEY,  
name VARCHAR(50) NOT NULL,  
isConfigured BOOLEAN NOT NULL  
);  