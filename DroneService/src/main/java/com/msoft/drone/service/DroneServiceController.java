/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msoft.drone.service;

import com.msoft.drone.service.dao.ResponseDao;
import com.msoft.drone.service.dao.DroneLogDao;
import com.msoft.drone.service.dao.LoadDroneLogDao;
import com.msoft.drone.service.dao.MedicationLogDao;
import com.msoft.drone.service.entities.DroneLog;
import com.msoft.drone.service.entities.LoadDroneLog;
import com.msoft.drone.service.entities.MedicationLog;
import com.msoft.drone.service.entities.ModelLog;
import com.msoft.drone.service.entities.StateLog;
import com.msoft.drone.service.repositories.DroneRepository;
import com.msoft.drone.service.repositories.LoadDroneRepository;
import com.msoft.drone.service.repositories.MedicationRepository;
import com.msoft.drone.service.repositories.ModelRepository;
import com.msoft.drone.service.repositories.StateRepository;
import static com.msoft.drone.service.util.Converter.toJson;
import com.msoft.drone.service.util.LoggerUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PSB
 */
@RestController
@RequestMapping("/api")
public class DroneServiceController {
    
    @Autowired DroneRepository droneLog;
    @Autowired MedicationRepository medLog;
    @Autowired ModelRepository modelRepo;
    @Autowired StateRepository stateRepo;
    @Autowired LoadDroneRepository loadDroneRepo;
    
    @RequestMapping(value ="/add/drone",produces = "application/json",method=RequestMethod.POST)
    public ResponseEntity<ResponseDao> AddDrone(@RequestBody DroneLogDao request, @RequestHeader HttpHeaders headers)
        {
            new LoggerUtil().LogDisplay("request: " + toJson(request), Level.INFO);
            ResponseDao resp = new ResponseDao();
            List<ModelLog> modelList = modelRepo.findAll();
             System.out.println(modelList.size());
            List<StateLog> stateList = stateRepo.findAll();
            System.out.println(stateList.size());
            
            try
            {
            if((request.getSerialNumber()!=null && !request.getSerialNumber().isEmpty()) 
                    && (request.getBattery()!=null && !request.getBattery().isEmpty()) 
                    && (request.getModel()!=null && !request.getModel().isEmpty()) 
                    && (request.getState()!=null && !request.getState().isEmpty()) 
                    && (request.getWeight()!=null && !request.getWeight().isEmpty()))
            {
                //CHECK IF SERIAL NUMBER NUMBER OF CHARACTERS IS NOT ABOVE 100
                if(request.getSerialNumber().length() > 100)
                {
                    resp.setStatusCode("01");
                    resp.setStatusMessage("Maximum number characters exceeded");
                    return ResponseEntity.ok().body(resp);
                }
                //CHECK IF SERIAL NUMBER NUMBER OF CHARACTERS IS NOT ABOVE 100
                
                //CHECK IF CAP WEIGHT IS NOT ABOVE 500
                if(request.getWeight().length() > 500)
                {
                    resp.setStatusCode("01");
                    resp.setStatusMessage("Maximum weight exceeded");
                    return ResponseEntity.ok().body(resp);
                }
                //CHECK IF CAP WEIGHT IS NOT ABOVE 500
                
                
                //CHECK IF STATE PROVIDED IS CORRECT
                if(!stateList.stream().filter(p->p.getState().equalsIgnoreCase(request.getState())).findFirst().isPresent())
                {
                    resp.setStatusCode("02");
                    resp.setStatusMessage("Invalid State");
                    return ResponseEntity.ok().body(resp);
                }
                //CHECK IF STATE PROVIDED IS CORRECT
                
                //CHECK IF MODEL PROVIDED IS CORRECT
                if(!modelList.stream().filter(p->p.getModel().equalsIgnoreCase(request.getModel())).findFirst().isPresent())
                {
                    resp.setStatusCode("02");
                    resp.setStatusMessage("Invalid Model");
                    return ResponseEntity.ok().body(resp);
                }
                //CHECK IF MODEL PROVIDED IS CORRECT
                
               else
                {
                    try
                    {
            //SAVING NEW DRONE
            DroneLog req = new DroneLog();
           
            req.setBattery(request.getBattery());
            req.setModel(request.getModel());
            req.setSerialNumber(request.getSerialNumber());
            req.setState(request.getState());
            req.setWeight(request.getWeight());
            droneLog.save(req); 
            //SAVING NEW DRONE
            
            resp.setStatusCode("00");
            resp.setStatusMessage("Data submitted successfully");
            System.out.println("resp.getStatusMessage(): " + resp.getStatusMessage());
            
            return ResponseEntity.ok().body(resp);
            
            }
            catch(DataIntegrityViolationException dup)
		{
                        new LoggerUtil().LogDisplay("Error: " + dup.getStackTrace().toString(), Level.SEVERE);
			ResponseDao reply = new ResponseDao();
			reply.setStatusCode("94");
			reply.setStatusMessage("Data flagged as duplicate");			
			return new ResponseEntity<ResponseDao>(reply, HttpStatus.OK);
		}
            }

            }
            else
            {
                resp.setStatusCode("02");
                resp.setStatusMessage("All values required");
                System.out.println("resp.getStatusMessage(): " + resp.getStatusMessage());
                return ResponseEntity.ok().body(resp);
            }
            
            }
		catch(Exception e)
		{
			e.printStackTrace();
                        new LoggerUtil().LogDisplay("Error: " + e.getStackTrace().toString(), Level.SEVERE);
			return new ResponseEntity<ResponseDao>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
            
            
        }
    
    @RequestMapping(value ="/update/drone",produces = "application/json",method=RequestMethod.POST)
    public ResponseEntity<ResponseDao> UpdateDrone(@RequestBody DroneLogDao request, @RequestHeader HttpHeaders headers)
        {
            new LoggerUtil().LogDisplay("request: " + toJson(request), Level.INFO);
            ResponseDao resp = new ResponseDao();
            List<ModelLog> modelList = modelRepo.findAll();
             System.out.println(modelList.size());
            List<StateLog> stateList = stateRepo.findAll();
            System.out.println(stateList.size());
            try
            {
            if((request.getSerialNumber()!=null && !request.getSerialNumber().isEmpty()) 
                    && (request.getBattery()!=null && !request.getBattery().isEmpty()) 
                    && (request.getModel()!=null && !request.getModel().isEmpty()) 
                    && (request.getState()!=null && !request.getState().isEmpty()) 
                    && (request.getWeight()!=null && !request.getWeight().isEmpty()))
            {
                //CHECK IF SERIAL NUMBER NUMBER OF CHARACTERS IS NOT ABOVE 100
                if(request.getSerialNumber().length() > 100)
                {
                    resp.setStatusCode("01");
                    resp.setStatusMessage("Maximum number characters exceeded");
                    return ResponseEntity.ok().body(resp);
                }
                //CHECK IF SERIAL NUMBER NUMBER OF CHARACTERS IS NOT ABOVE 100
                
                //CHECK IF CAP WEIGHT IS NOT ABOVE 500
                if(request.getWeight().length() > 500)
                {
                    resp.setStatusCode("01");
                    resp.setStatusMessage("Maximum weight exceeded");
                    return ResponseEntity.ok().body(resp);
                }
                //CHECK IF CAP WEIGHT IS NOT ABOVE 500
                
                
                //CHECK IF STATE PROVIDED IS CORRECT
                if(!stateList.stream().filter(p->p.getState().equalsIgnoreCase(request.getState())).findFirst().isPresent())
                {
                    resp.setStatusCode("02");
                    resp.setStatusMessage("Invalid State");
                    return ResponseEntity.ok().body(resp);
                }
                //CHECK IF STATE PROVIDED IS CORRECT
                
                //CHECK IF MODEL PROVIDED IS CORRECT
                if(!modelList.stream().filter(p->p.getModel().equalsIgnoreCase(request.getModel())).findFirst().isPresent())
                {
                    resp.setStatusCode("02");
                    resp.setStatusMessage("Invalid Model");
                    return ResponseEntity.ok().body(resp);
                }
                //CHECK IF MODEL PROVIDED IS CORRECT
                
               else
                {
                    try
                    {
            //UPDATING NEW DRONE
            
             //FETCH DRONE BY SERIAL NUMBER
             DroneLog req = droneLog.findByserialNumber(request.getSerialNumber());
             //FETCH DRONE BY SERIAL NUMBER
           if(req.getSerialNumber()!=null && !req.getSerialNumber().isEmpty())
           {
            req.setBattery(request.getBattery());
            req.setModel(request.getModel());
            req.setState(request.getState());
            req.setWeight(request.getWeight());
            droneLog.save(req); 
            //UPDATING NEW DRONE
            
            resp.setStatusCode("00");
            resp.setStatusMessage("Data updated successfully");
            System.out.println("resp.getStatusMessage(): " + resp.getStatusMessage());
            
            return ResponseEntity.ok().body(resp);
            
           }
           else
           {
            resp.setStatusCode("96");
            resp.setStatusMessage("Drone not found");
            System.out.println("resp.getStatusMessage(): " + resp.getStatusMessage());
            
            return ResponseEntity.ok().body(resp);  
           }
            
            }
            catch(Exception dup)
		{
                        new LoggerUtil().LogDisplay("Error: " + dup.getStackTrace().toString(), Level.SEVERE);
			ResponseDao reply = new ResponseDao();
			reply.setStatusCode("95");
			reply.setStatusMessage("Unable to update data");			
			return new ResponseEntity<ResponseDao>(reply, HttpStatus.OK);
		}
            }

            }
            else
            {
                resp.setStatusCode("02");
                resp.setStatusMessage("All values required");
                System.out.println("resp.getStatusMessage(): " + resp.getStatusMessage());
                return ResponseEntity.ok().body(resp);
            }
            
            }
		catch(Exception e)
		{
			e.printStackTrace();
                        new LoggerUtil().LogDisplay("Error: " + e.getStackTrace().toString(), Level.SEVERE);
			return new ResponseEntity<ResponseDao>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
            
            
        }
    
    @RequestMapping(value ="/add/medication",produces = "application/json",method=RequestMethod.POST)
    public ResponseEntity<ResponseDao> UpdateMedication(@RequestBody MedicationLogDao request, @RequestHeader HttpHeaders headers)
        {
            new LoggerUtil().LogDisplay("request: " + toJson(request), Level.INFO);
            ResponseDao resp = new ResponseDao();
            List<ModelLog> modelList = modelRepo.findAll();
             System.out.println(modelList.size());
            List<StateLog> stateList = stateRepo.findAll();
            System.out.println(stateList.size());
            try
            {
            if((request.getCode()!=null && !request.getCode().isEmpty()) 
                    && (request.getImage()!=null && !request.getImage().isEmpty()) 
                    && (request.getName()!=null && !request.getName().isEmpty()) 
                    && (request.getWeight()!=null && !request.getWeight().isEmpty()))
            {
                System.out.println(request.getName());
                System.out.println(request.getCode());
                //CHECK IF NAME MEETS WITH THE REQUIREMENTS
                if(Pattern.compile("[^A-Za-z0-9_-]", Pattern.CASE_INSENSITIVE).matcher(request.getName()).find())
                {
                    resp.setStatusCode("01");
                    resp.setStatusMessage("Invalid character found in medication name");
                    return ResponseEntity.ok().body(resp);
                }
                //CHECK IF NAME MEETS WITH THE REQUIREMENTS
                
                //CHECK IF CODE MEETS WITH THE REQUIREMENTS
                if(Pattern.compile("[^A-Z0-9_]", Pattern.CASE_INSENSITIVE).matcher(request.getCode()).find())
                {
                    resp.setStatusCode("01");
                    resp.setStatusMessage("Invalid character or case found in medication code");
                    return ResponseEntity.ok().body(resp);
                }
                //CHECK IF CODE MEETS WITH THE REQUIREMENTS
                if(request.getWeight().length() > 500)
                {
                    resp.setStatusCode("01");
                    resp.setStatusMessage("Maximum weight exceeded");
                    return ResponseEntity.ok().body(resp);
                }
               
               else
                {
                    try
                    {
             //SAVING NEW MEDICATION
            MedicationLog req = new MedicationLog();
           
            req.setCode(request.getCode().toUpperCase());
            req.setImage(request.getImage());
            req.setName(request.getName());
            req.setWeight(request.getWeight());
            medLog.save(req);
            //SAVING NEW MEDICATION
            
            resp.setStatusCode("00");
            resp.setStatusMessage("Data submitted successfully");
            System.out.println("resp.getStatusMessage(): " + resp.getStatusMessage());
            
            return ResponseEntity.ok().body(resp);
            
            }
            catch(DataIntegrityViolationException dup)
		{
                        new LoggerUtil().LogDisplay("Error: " + dup.getStackTrace().toString(), Level.SEVERE);
			ResponseDao reply = new ResponseDao();
			reply.setStatusCode("94");
			reply.setStatusMessage("Data flagged as duplicate");			
			return new ResponseEntity<ResponseDao>(reply, HttpStatus.OK);
		}
            }

            }
            else
            {
                resp.setStatusCode("02");
                resp.setStatusMessage("All values required");
                System.out.println("resp.getStatusMessage(): " + resp.getStatusMessage());
                return ResponseEntity.ok().body(resp);
            }
            
            }
		catch(Exception e)
		{
			e.printStackTrace();
                        new LoggerUtil().LogDisplay("Error: " + e.getStackTrace().toString(), Level.SEVERE);
			return new ResponseEntity<ResponseDao>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
            
            
        }
    
     @RequestMapping(value ="/update/medication",produces = "application/json",method=RequestMethod.POST)
    public ResponseEntity<ResponseDao> AddMedication(@RequestBody MedicationLogDao request, @RequestHeader HttpHeaders headers)
        {
            new LoggerUtil().LogDisplay("request: " + toJson(request), Level.INFO);
            ResponseDao resp = new ResponseDao();
            List<ModelLog> modelList = modelRepo.findAll();
             System.out.println(modelList.size());
            List<StateLog> stateList = stateRepo.findAll();
            System.out.println(stateList.size());
            try
            {
            if((request.getCode()!=null && !request.getCode().isEmpty()) 
                    && (request.getImage()!=null && !request.getImage().isEmpty()) 
                    && (request.getName()!=null && !request.getName().isEmpty()) 
                    && (request.getWeight()!=null && !request.getWeight().isEmpty()))
            {
                System.out.println(request.getName());
                System.out.println(request.getCode());
                //CHECK IF NAME MEETS WITH THE REQUIREMENTS
                if(Pattern.compile("[^A-Za-z0-9_-]", Pattern.CASE_INSENSITIVE).matcher(request.getName()).find())
                {
                    resp.setStatusCode("01");
                    resp.setStatusMessage("Invalid character found in medication name");
                    return ResponseEntity.ok().body(resp);
                }
                //CHECK IF NAME MEETS WITH THE REQUIREMENTS
                
                //CHECK IF CODE MEETS WITH THE REQUIREMENTS
                if(Pattern.compile("[^A-Z0-9_]", Pattern.CASE_INSENSITIVE).matcher(request.getCode()).find())
                {
                    resp.setStatusCode("01");
                    resp.setStatusMessage("Invalid character or case found in medication code");
                    return ResponseEntity.ok().body(resp);
                }
                //CHECK IF CODE MEETS WITH THE REQUIREMENTS
                if(request.getWeight().length() > 500)
                {
                    resp.setStatusCode("01");
                    resp.setStatusMessage("Maximum weight exceeded");
                    return ResponseEntity.ok().body(resp);
                }
               
               else
                {
                    try
                    {
             //UPDATING NEW MEDICATION
           //FETCH MEDICATION BY SERIAL NUMBER
             MedicationLog req = medLog.findBycode(request.getCode().toUpperCase());
             //FETCH MEDICATION BY SERIAL NUMBER
           if(req.getCode()!=null && !req.getCode().isEmpty())
           {
            req.setCode(request.getCode().toUpperCase());
            req.setImage(request.getImage());
            req.setName(request.getName());
            req.setWeight(request.getWeight());
            medLog.save(req);
            //UPDATING NEW MEDICATION
            
            resp.setStatusCode("00");
            resp.setStatusMessage("Data updated successfully");
            System.out.println("resp.getStatusMessage(): " + resp.getStatusMessage());
            
            return ResponseEntity.ok().body(resp);
            
            }
           else
           {
            resp.setStatusCode("96");
            resp.setStatusMessage("Medication not found");
            System.out.println("resp.getStatusMessage(): " + resp.getStatusMessage());
            
            return ResponseEntity.ok().body(resp);  
           }
            
            }
            catch(Exception dup)
		{
                        new LoggerUtil().LogDisplay("Error: " + dup.getStackTrace().toString(), Level.SEVERE);
			ResponseDao reply = new ResponseDao();
			reply.setStatusCode("95");
			reply.setStatusMessage("Unable to update data");			
			return new ResponseEntity<ResponseDao>(reply, HttpStatus.OK);
		}
            }

            }
            else
            {
                resp.setStatusCode("02");
                resp.setStatusMessage("All values required");
                System.out.println("resp.getStatusMessage(): " + resp.getStatusMessage());
                return ResponseEntity.ok().body(resp);
            }
            
            }
		catch(Exception e)
		{
			e.printStackTrace();
                        new LoggerUtil().LogDisplay("Error: " + e.getStackTrace().toString(), Level.SEVERE);
			return new ResponseEntity<ResponseDao>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
            
            
        }
    
    
    @RequestMapping(value ="/list/drone",produces = "application/json",method=RequestMethod.GET)
    public ResponseEntity<List<DroneLog>> fetchAllDrones()
        {    
             List<DroneLog> resp = new ArrayList<DroneLog>();
             try
             {
             //FETCH ALL DRONES
             resp = droneLog.findAll();
             //FETCH ALL DRONES
             }
		catch(Exception e)
		{
			e.printStackTrace();
                        new LoggerUtil().LogDisplay("Error: " + e.getStackTrace().toString(), Level.SEVERE);
			return new ResponseEntity<List<DroneLog>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
            return ResponseEntity.ok().body(resp);
        }
    
    @RequestMapping(value ="/list/medication",produces = "application/json",method=RequestMethod.GET)
    public ResponseEntity<List<MedicationLog>> fetchAllMedication()
        {    
             List<MedicationLog> resp = new ArrayList<MedicationLog>();
             try
             {
             //FETCH ALL MEDICATION
             resp = medLog.findAll();
             //FETCH ALL MEDICATION
             }
		catch(Exception e)
		{
			e.printStackTrace();
                        new LoggerUtil().LogDisplay("Error: " + e.getStackTrace().toString(), Level.SEVERE);
			return new ResponseEntity<List<MedicationLog>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
            return ResponseEntity.ok().body(resp);
        }
    
    @RequestMapping(value ="/find/drone/byserialnumber/{serialnumber}",produces = "application/json",method=RequestMethod.GET)
    public ResponseEntity<DroneLog> fetchDroneBySerialNumber(@PathVariable("serialnumber")  String serialnumber)
        {    
            DroneLog resp = new DroneLog();
             try
             {
             //FETCH DRONE BY SERIAL NUMBER
             resp = droneLog.findByserialNumber(serialnumber);
             //FETCH DRONE BY SERIAL NUMBER
             }
		catch(Exception e)
		{
			e.printStackTrace();
                        new LoggerUtil().LogDisplay("Error: " + e.getStackTrace().toString(), Level.SEVERE);
			return new ResponseEntity<DroneLog>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
            return ResponseEntity.ok().body(resp);
        }
    
    @RequestMapping(value ="/find/medication/bycode/{code}",produces = "application/json",method=RequestMethod.GET)
    public ResponseEntity<MedicationLog> fetchMedicationByCode(@PathVariable("code")  String code)
        {    
            MedicationLog resp = new MedicationLog();
             try
             {
             //FETCH MEDICATION BY CODE
             resp = medLog.findBycode(code.toUpperCase());
             //FETCH MEDICATION BY CODE
             }
		catch(Exception e)
		{
			e.printStackTrace();
                        new LoggerUtil().LogDisplay("Error: " + e.getStackTrace().toString(), Level.SEVERE);
			return new ResponseEntity<MedicationLog>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
            return ResponseEntity.ok().body(resp);
        }
    
    
    @RequestMapping(value ="/loaddrone",produces = "application/json",method=RequestMethod.POST)
    public ResponseEntity<ResponseDao> LoadDrone(@RequestBody LoadDroneLogDao request, @RequestHeader HttpHeaders headers)
        {
            new LoggerUtil().LogDisplay("request: " + toJson(request), Level.INFO);
            ResponseDao resp = new ResponseDao();
             List<LoadDroneLog> droneItems = null;
             DroneLog droneResp = null;
             try
             {
             //FETCH DRONE DATA
             droneResp = droneLog.findByserialNumber(request.getSerialNumber());
             //FETCH DRONE DATA
             
             //FETCH ALL ITEMS ON A DRONE
             droneItems = loadDroneRepo.findByserialNumber(request.getSerialNumber());
             //FETCH ALL ITEMS ON A DRONE
             }
             catch(Exception ex)
             {
                 
             }
            try
            {
            if((request.getSerialNumber()!=null && !request.getSerialNumber().isEmpty()) 
                    && (request.getMedCode()!=null && !request.getMedCode().isEmpty()))
            {
                //CHECK IF SERIAL NUMBER NUMBER OF CHARACTERS IS NOT ABOVE 100
                if(request.getSerialNumber().length() > 100)
                {
                    resp.setStatusCode("01");
                    resp.setStatusMessage("Maximum number characters exceeded");
                    return ResponseEntity.ok().body(resp);
                }
                //CHECK IF SERIAL NUMBER NUMBER OF CHARACTERS IS NOT ABOVE 100
                
                //CHECK IF CODE MEETS WITH THE REQUIREMENTS
                if(Pattern.compile("[^A-Z0-9_]", Pattern.CASE_INSENSITIVE).matcher(request.getMedCode()).find())
                {
                    resp.setStatusCode("01");
                    resp.setStatusMessage("Invalid character or case found in medication code");
                    return ResponseEntity.ok().body(resp);
                }
                //CHECK IF CODE MEETS WITH THE REQUIREMENTS
                
                //CHECK IF CUMMULATIVE WEIGHT IS ABOVE 500
                if(droneItems.size() > 0 && ((loadDroneRepo.findByserialNumber(request.getSerialNumber()).stream().filter(p->p.getWeight()!=null).mapToInt(o->Integer.parseInt(o.getWeight())).sum()) + (Integer.parseInt(medLog.findBycode(request.getMedCode()).getWeight())) > 500))
                {
                    resp.setStatusCode("01");
                    resp.setStatusMessage("Maximum weight reached at " + (loadDroneRepo.findByserialNumber(request.getSerialNumber()).stream().filter(p->p.getWeight()!=null).mapToInt(o->Integer.parseInt(o.getWeight())).sum()));
                    return ResponseEntity.ok().body(resp);
                }             
                //CHECK IF CUMMULATIVE WEIGHT IS ABOVE 500
                
                //CHECK IF MEDICATION WEIGHT IS ABOVE 500
                if(droneItems.size() < 1 && ((Integer.parseInt(medLog.findBycode(request.getMedCode()).getWeight())) > 500))
                {
                    resp.setStatusCode("01");
                    resp.setStatusMessage("Maximum weight reached at " + medLog.findBycode(request.getMedCode()).getWeight());
                    return ResponseEntity.ok().body(resp);
                }
                //CHECK IF MEDICATION WEIGHT IS ABOVE 500
                
               else
                {
                    try
                    {
                   
             
             if((droneResp.getSerialNumber()!=null && !droneResp.getSerialNumber().isEmpty()))
             {
             if(Integer.parseInt(droneResp.getBattery()) > 25)
             {
             //LOADING DRONE
            LoadDroneLog req = new LoadDroneLog();
            req.setMedCode(request.getMedCode());
            req.setSerialNumber(request.getSerialNumber());
            req.setWeight(medLog.findBycode(request.getMedCode()).getWeight());
            loadDroneRepo.save(req);
            //LOADING DRONE
            
            //UPDATING DRONE STATE
            if(Integer.parseInt(req.getWeight()) < 500)
            {
             droneResp.setState("LOADING");
             droneLog.save(droneResp);
            }
            else
            {
             droneResp.setState("LOADED");
             droneLog.save(droneResp); 
            }
            //UPDATING DRONE STATE
            
            resp.setStatusCode("00");
            resp.setStatusMessage("Data submitted successfully");
            System.out.println("resp.getStatusMessage(): " + resp.getStatusMessage());
            
            }
            else
            {
             droneResp.setState("IDLE");
             droneLog.save(droneResp); 
             
             resp.setStatusCode("96");
            resp.setStatusMessage("Drone battery too low to be loaded");
            System.out.println("resp.getStatusMessage(): " + resp.getStatusMessage());
            }
            
            return ResponseEntity.ok().body(resp);
             }
             else
             {
                resp.setStatusCode("96");
                resp.setStatusMessage("Drone data not found"); 
                return ResponseEntity.ok().body(resp);
             }
            
            }
            catch(DataIntegrityViolationException dup)
		{
                        new LoggerUtil().LogDisplay("Error: " + dup.getStackTrace().toString(), Level.SEVERE);
			ResponseDao reply = new ResponseDao();
			reply.setStatusCode("94");
			reply.setStatusMessage("Data flagged as duplicate");			
			return new ResponseEntity<ResponseDao>(reply, HttpStatus.OK);
		}
            }

            }
            else
            {
                resp.setStatusCode("02");
                resp.setStatusMessage("All values required");
                System.out.println("resp.getStatusMessage(): " + resp.getStatusMessage());
                return ResponseEntity.ok().body(resp);
            }
            
            }
		catch(Exception e)
		{
			e.printStackTrace();
                        new LoggerUtil().LogDisplay("Error: " + e.getStackTrace().toString(), Level.SEVERE);
			return new ResponseEntity<ResponseDao>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
        }
    
    @RequestMapping(value ="/list/loadeddrones",produces = "application/json",method=RequestMethod.GET)
    public ResponseEntity<List<LoadDroneLog>> fetchAllLoadedDrones()
        {    
             List<LoadDroneLog> resp = new ArrayList<LoadDroneLog>();
             try
             {
                 //FETCH ALL LOADED ITEMS ON ALL LOADED DRONES
             resp = loadDroneRepo.findAll();
             //FETCH ALL LOADED ITEMS ON ALL LOADED DRONES
             }
		catch(Exception e)
		{
			e.printStackTrace();
                        new LoggerUtil().LogDisplay("Error: " + e.getStackTrace().toString(), Level.SEVERE);
			return new ResponseEntity<List<LoadDroneLog>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
            return ResponseEntity.ok().body(resp);
        }
    
    @RequestMapping(value ="/checkavailable/drones",produces = "application/json",method=RequestMethod.GET)
    public ResponseEntity<List<DroneLog>> checkAvailableDrones()
        {    
             List<DroneLog> resp = new ArrayList<DroneLog>();
             try
             {
             List<DroneLog> resp1 = droneLog.findAll();
             //FETCH ONLY DRONES WITH SPACE AND AVAILABLE FOR LOADING
             for(DroneLog item : resp1)
             {
             if(loadDroneRepo.findByserialNumber(item.getSerialNumber()).stream().filter(p->p.getWeight()!=null).mapToInt(o->Integer.parseInt(o.getWeight())).sum() < 500)
             {
               resp.add(item);
             }
             }
             //FETCH ONLY DRONES WITH SPACE AND AVAILABLE FOR LOADING
             }
		catch(Exception e)
		{
			e.printStackTrace();
                        new LoggerUtil().LogDisplay("Error: " + e.getStackTrace().toString(), Level.SEVERE);
			return new ResponseEntity<List<DroneLog>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
            return ResponseEntity.ok().body(resp);
        }
    
    @RequestMapping(value ="/find/loadeddrone/byserialnumber/{serialnumber}",produces = "application/json",method=RequestMethod.GET)
    public ResponseEntity<List<LoadDroneLog>> fetchLoadedDroneBySerialNumber(@PathVariable("serialnumber")  String serialnumber)
        {    
            List<LoadDroneLog> resp = new ArrayList<LoadDroneLog>();
             try
             {
                 //FETCH ALL ITEMS ON A SPECIFIED DRONE
             resp = loadDroneRepo.findByserialNumber(serialnumber);
             //FETCH ALL ITEMS ON A SPECIFIED DRONE
             }
		catch(Exception e)
		{
			e.printStackTrace();
                        new LoggerUtil().LogDisplay("Error: " + e.getStackTrace().toString(), Level.SEVERE);
			return new ResponseEntity<List<LoadDroneLog>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
            return ResponseEntity.ok().body(resp);
        }
    
}
