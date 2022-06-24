package com.msoft.drone.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msoft.drone.service.entities.DroneLog;
import com.msoft.drone.service.entities.MedicationLog;
import com.msoft.drone.service.repositories.DroneRepository;
import com.msoft.drone.service.repositories.LoadDroneRepository;
import com.msoft.drone.service.repositories.MedicationRepository;
import com.msoft.drone.service.repositories.ModelRepository;
import com.msoft.drone.service.repositories.StateRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DroneServiceController.class)
class DroneServiceApplicationTests {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    ObjectMapper mapper;
    
    @MockBean
    DroneRepository droneRepository;
    
    @MockBean
    ModelRepository modelRepository;
    
    @MockBean
    StateRepository stateRepository;
    
    @MockBean
    MedicationRepository medRepository;
    
    @MockBean
    LoadDroneRepository loaderRepository;
    
    
@Test
public void getListDrone() throws Exception {
            DroneLog req = new DroneLog();          
            req.setBattery("50");
            req.setModel("Lightweight");
            req.setSerialNumber("adfswrtret89");
            req.setState("IDLE");
            req.setWeight("50");
            droneRepository.save(req);
            List<DroneLog> records = new ArrayList<>(Arrays.asList(req));
            
    Mockito.when(droneRepository.findAll()).thenReturn(records);
    
    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/list/drone")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].weight", is("50"))
            );
}

@Test
public void getListMedication() throws Exception {
            MedicationLog req = new MedicationLog();          
            req.setCode("afsg5etdg".toUpperCase());
            req.setImage("adfswrtret89");
            req.setName("AsfdfFGSFG");
            req.setWeight("50");   
            medRepository.save(req);
            List<MedicationLog> records = new ArrayList<>(Arrays.asList(req));
            
    Mockito.when(medRepository.findAll()).thenReturn(records);
    
    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/list/medication")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].weight", is("50"))
            );
}

@Test
public void getDroneBySerialNumber() throws Exception {
            DroneLog req = new DroneLog();          
            req.setBattery("50");
            req.setModel("Lightweight");
            req.setSerialNumber("adfswrtret89");
            req.setState("IDLE");
            req.setWeight("50");
            Mockito.when(droneRepository.save(req)).thenReturn(req);
    
    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/find/drone/byserialnumber/" + req.getSerialNumber())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            ;
}

@Test
public void getMedicationByCode() throws Exception {
            MedicationLog req = new MedicationLog();          
            req.setCode("afsg5etdg".toUpperCase());
            req.setImage("adfswrtret89");
            req.setName("AsfdfFGSFG");
            req.setWeight("50");       
    Mockito.when(medRepository.save(req)).thenReturn(req);

    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/find/medication/bycode/" + req.getCode().toUpperCase())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            ;
}


@Test
public void addMedication() throws Exception {
     MedicationLog req = new MedicationLog();          
            req.setCode("afsg5etdg".toUpperCase());
            req.setImage("adfswrtret89");
            req.setName("AsfdfFGSFG");
            req.setWeight("50");

    Mockito.when(medRepository.save(req)).thenReturn(req);

    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/add/medication")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.mapper.writeValueAsString(req));

    mockMvc.perform(mockRequest)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$.statusCode", is("00")));
    }


@Test
public void getAvailableDrone() throws Exception {
            DroneLog req = new DroneLog();          
            req.setBattery("50");
            req.setModel("Lightweight");
            req.setSerialNumber("adfswrtret89");
            req.setState("IDLE");
            req.setWeight("50");
            droneRepository.save(req);
            List<DroneLog> records = new ArrayList<>(Arrays.asList(req));
            
    Mockito.when(droneRepository.findAll()).thenReturn(records);
    
    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/checkavailable/drones")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            ;
}


}
