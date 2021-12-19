package com.microservice;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.entity.ExternalServicesConfiguration;
import com.microservice.repository.GenericEntityRepository;
import com.microservice.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;


import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ExternalServicesController {


   private ObjectMapper objectMapper =null;
   private JsonGenerator jsonGenerator;

   @Autowired
   private GenericEntityRepository repository;
   public ExternalServicesController() throws IOException {
       objectMapper = new ObjectMapper();
       JsonFactory factory = new JsonFactory();
       jsonGenerator = factory.createGenerator(
               new File(Utils.FILE_NAME), JsonEncoding.UTF8);


   }

    @PostConstruct
    private void postConstruct() {
        ExternalServicesConfiguration extConfDefault = new ExternalServicesConfiguration(1,"Selenium",true);
        repository.save(extConfDefault);
    }

   @GetMapping(value = "/allconf")
   public List<ExternalServicesConfiguration> getAllConfigurations(){
       ExternalServicesConfiguration conf = null;
       try {

    	  return repository.findAll();

       }catch (Exception ioEx){
           ioEx.printStackTrace();
           conf = new ExternalServicesConfiguration();
           conf.setId(-99);
           conf.setName("error - see console");
           
           
           List<ExternalServicesConfiguration> errorlist = new ArrayList<ExternalServicesConfiguration>();
           errorlist.add(conf);
           return errorlist;
       }
     
   }
   
   
    @GetMapping(value = "/currentconf/{id}")
    public ExternalServicesConfiguration getCurrentConfiguration(@PathVariable("id") int id){
        ExternalServicesConfiguration conf = null;
        try {

        	conf= repository.findById(id).get();

        }catch (Exception ioEx){
            ioEx.printStackTrace();
            conf = new ExternalServicesConfiguration();
        }
        return conf;
    }
 
    @PutMapping(value = "/updateconf")
    public ResponseEntity<Map<String, Object>> updateCurrentConfiguration(@RequestBody ExternalServicesConfiguration extConf){
        HttpStatus httpStatus;
        Map<String, Object> body;
        try {

        	 repository.save(extConf);
            httpStatus  = HttpStatus.CREATED;
            body = Map.of("status", httpStatus.value(),
                    "result", "current configuration was saved",
                    "timestamp", Instant.now().toEpochMilli());
        }catch (Exception ioEx){
            ioEx.printStackTrace();
            httpStatus  = HttpStatus.INTERNAL_SERVER_ERROR;
            body = Map.of("status", httpStatus.value(),
                    "result", "current  configuration was not saved, see console",
                    "timestamp", Instant.now().toEpochMilli());
        }
        return new ResponseEntity<>(body, httpStatus);
    }
    
    @PostMapping(value = "/newconf")
    public ResponseEntity<Map<String, Object>> SaveNewConfiguration(@RequestBody ExternalServicesConfiguration extConf){
        HttpStatus httpStatus;
        Map<String, Object> body;
        try {

   
        	repository.save(extConf);
            objectMapper.writeValue(new File(Utils.FILE_NAME),extConf);
            
            httpStatus  = HttpStatus.CREATED;
            body = Map.of("status", httpStatus.value(),
                    "result", "New configuration is saved",
                    "timestamp", Instant.now().toEpochMilli());
            
            
        }catch (IOException ioEx){
            httpStatus  = HttpStatus.BAD_REQUEST;
             body = Map.of("status", httpStatus.value(),
                    "error", "File could not be created, see console",
                    "timestamp", Instant.now().toEpochMilli());
              ioEx.printStackTrace();
        }catch(Exception e){
            httpStatus  = HttpStatus.NOT_ACCEPTABLE;
            body = Map.of("status", httpStatus.value(),
                    "error", "File could not be created, see console",
                    "timestamp", Instant.now().toEpochMilli());
            e.printStackTrace();
        }
        return new ResponseEntity<>(body, httpStatus);
    }

    @DeleteMapping(value = "/deleteconf/{id}")
    public ResponseEntity<Map<String, Object>> deleteCurrentconfiguration(@PathVariable("id") int id){
        HttpStatus httpStatus;
        Map<String, Object> body;

        try{
            
                repository.deleteById(id);  
                httpStatus  = HttpStatus.CREATED;
                body = Map.of("status", httpStatus.value(),
                        "result", "current con figuration is deleted",
                        "timestamp", Instant.now().toEpochMilli());

                /*
                httpStatus = HttpStatus.CREATED;
                body = Map.of("status", httpStatus.value(),
                        "result", "there is no configuration to be delete",
                        "timestamp", Instant.now().toEpochMilli());
            */
        }catch(Exception e){
            httpStatus  = HttpStatus.BAD_REQUEST;
            body = Map.of("status", httpStatus.value(),
                    "error", "File could not be delete, see console",
                    "timestamp", Instant.now().toEpochMilli());
            e.printStackTrace();
        }finally {

        }
        return new ResponseEntity<>(body, httpStatus);
    }
}
