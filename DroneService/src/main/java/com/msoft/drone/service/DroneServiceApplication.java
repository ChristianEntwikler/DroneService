package com.msoft.drone.service;

import com.msoft.drone.service.entities.ModelLog;
import com.msoft.drone.service.entities.StateLog;
import com.msoft.drone.service.repositories.ModelRepository;
import com.msoft.drone.service.repositories.StateRepository;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableScheduling
public class DroneServiceApplication {
    
     @Value ("${model.default.list}")
     private String modelValues;
     
     @Value ("${state.default.list}")
     private String stateValues;
     
     @Autowired ModelRepository modelRepo;
     @Autowired StateRepository stateRepo;

	public static void main(String[] args) {
		SpringApplication.run(DroneServiceApplication.class, args);
	}
        
        
        @Bean
        public RestTemplate getRestTemplate() 
        {
            
            return new RestTemplate();
        }
        
        @Bean
	CommandLineRunner runner()
	{
                return args ->
		{
            if(modelRepo.findAll().size()<1)
            {
                try
                {
                String[] modelArr = modelValues.split(",");
                for(int i=0; i<modelArr.length; i++)
                {
                    ModelLog modelData = new ModelLog();
                    modelData.setModel(modelArr[i].trim());
                    modelRepo.save(modelData);
                }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                    Logger.getLogger(DroneServiceApplication.class.getName()).log(Level.SEVERE, (String)null, ex);
                }
            }
            
            if(stateRepo.findAll().size()<1)
            {
                try
                {
                String[] stateArr = stateValues.split(",");
                for(int i=0; i<stateArr.length; i++)
                {
                    StateLog stateData = new StateLog();
                    stateData.setState(stateArr[i].trim());
                    stateRepo.save(stateData);
                }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                    Logger.getLogger(DroneServiceApplication.class.getName()).log(Level.SEVERE, (String)null, ex);
                }
            }			
		};
	}

}
