/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msoft.drone.service.repositories;

import com.msoft.drone.service.entities.MedicationLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author PSB
 */
public interface MedicationRepository extends JpaRepository<MedicationLog, Long>{
    
    public MedicationLog findBycode(String code);
}
