/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msoft.drone.service.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author PSB
 */
@Entity
@Table(name="loaddronelog")
public class LoadDroneLog {
        @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
        
	@Column(name="serialnumber")
	private String serialNumber;
        
        @Column(name="medcode")
        private String medCode;
        private String weight;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMedCode() {
        return medCode;
    }

    public void setMedCode(String medCode) {
        this.medCode = medCode;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

         
}
