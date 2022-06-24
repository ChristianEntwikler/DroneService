/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msoft.drone.service.process;

import com.msoft.drone.service.entities.BatteryCheckLog;
import com.msoft.drone.service.entities.DroneLog;
import com.msoft.drone.service.repositories.BatteryCheckRepository;
import com.msoft.drone.service.repositories.DroneRepository;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author PSB
 */
@Component
public class TaskProcess {
   
    @Autowired BatteryCheckRepository batteryCheckRepo;
    @Autowired DroneRepository droneLog;
    
    private static final Logger logger = LoggerFactory.getLogger(TaskProcess.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    @Scheduled(fixedDelay = 300000, initialDelay = 3000)
    public void scheduleBatteryCheckTask() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
      Date now = new Date();
      String strDate = sdf.format(now);
      System.out.println("Battery Check Schedule Started:: " + strDate);
      
      List<DroneLog> drones = droneLog.findAll();
      
      for(DroneLog item : drones)
      {
          BatteryCheckLog bclog = new BatteryCheckLog();
          bclog.setBattery(item.getBattery());
          bclog.setSerialNumber(item.getSerialNumber());
          bclog.setDateChecked(new Timestamp(System.currentTimeMillis()));
          batteryCheckRepo.save(bclog);
          
          if(Integer.parseInt(item.getBattery()) < 25)
          {
              item.setState("IDLE");
              droneLog.save(item);
          }
      }
    }

}
