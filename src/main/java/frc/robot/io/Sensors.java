package frc.robot.io;

import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * Contains the sensors
 * Javadoc comment lovingly provided by Alex Pickering
 * 
 * @author Owen Avery, Alex Pickering
 */
public class Sensors {
	JsonObject configJSON;
	
	//null until we have a gyro on the robot
    public Gyro gyro = null; //new ADXRS450_Gyro();
    
    public ProximitySensor[] elevatorSensors = new ProximitySensor[6];
    
    /**
     * Initializes any sensors that need to use the config
     * 
     * @param config The robot config file
     * @author Alex Pickering
     */
    public void init(JsonObject config) {
    	this.configJSON = config;
    	
    	loadDIOSensors();
    }
    
    public void loadDIOSensors() {
    	JsonObject dioJSON = configJSON.getAsJsonObject("dio");
    	
    	for(String k : dioJSON.keySet()) {
    		JsonElement item = dioJSON.get(k);
    		int itemInt = item.getAsInt();
    		
    		switch(k) {
    			case "proximity sensor 1":
    				elevatorSensors[0] = new ProximitySensor(itemInt);
    				break;
    			
    			case "proximity sensor 2":
    				elevatorSensors[1] = new ProximitySensor(itemInt);
    				break;
    			
    			case "proximity sensor 3":
    				elevatorSensors[2] = new ProximitySensor(itemInt);
    				break;
    			
    			case "proximity sensor 4":
    				elevatorSensors[3] = new ProximitySensor(itemInt);
    				break;
    			
    			case "proximity sensor 5":
    				elevatorSensors[4] = new ProximitySensor(itemInt);
    				break;
    			
    			case "proximity sensor 6":
    				elevatorSensors[5] = new ProximitySensor(itemInt);
    				break;
    		}
    	}
    }
}