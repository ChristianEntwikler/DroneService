/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msoft.drone.service.entities;

import java.sql.Timestamp;
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
@Table(name="batterychecklog")
public class BatteryCheckLog {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    long id;
    
    @Column(name="serialnumber")
    private String serialNumber;
    
    @Column(name="battery")
    private String battery;
    
    @Column(name="datechecked")
    private Timestamp dateChecked;

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

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public Timestamp getDateChecked() {
        return dateChecked;
    }

    public void setDateChecked(Timestamp dateChecked) {
        this.dateChecked = dateChecked;
    }
    
    
}
