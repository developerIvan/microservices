package com.microservice.entity;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonSetter;
@Entity
public class ExternalServicesConfiguration {
	@Id 
	private int id;
	@Column
    private String name;
	@Column
    private boolean isConfigured;

    public ExternalServicesConfiguration(){
        name = "undefined";
    }

    public ExternalServicesConfiguration(int id,String name,boolean isConfigured){
        this.id = id;
        this.name = name;
        this.isConfigured= isConfigured;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getIsConfigured() {
        return isConfigured;
    }

    public void setIsConfigured(boolean isConfigured) {
        this.isConfigured = isConfigured;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", isConfigured=" + isConfigured +
                '}';
    }




}
