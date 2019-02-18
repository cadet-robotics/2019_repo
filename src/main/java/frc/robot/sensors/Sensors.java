package frc.robot.sensors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * Contains the sensors
 * <p>Javadoc comment lovingly provided by Alex Pickering
 * 
 * @author Owen Avery, Alex Pickering
 */
public class Sensors {
	JsonObject configJSON;
	
	//null until we have a gyro on the robot
    public Gyro gyro = null; //new ADXRS450_Gyro();
    
    public DigitalInput[] elevatorSensors = new DigitalInput[6];
    
    public DigitalInput topLimitSwitch,
    					bottomLimitSwitch,
    					ballLimitSwitch;
    
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
    		if(k.equals("desc") || k.contains("placeholder")) continue;
    		
    		JsonElement item = dioJSON.get(k);
    		int itemInt = item.getAsInt();
    		
    		switch(k) {
    			case "proximity sensor 1":
    				elevatorSensors[0] = new DigitalInput(itemInt);
    				break;
    			
    			case "proximity sensor 2":
    				elevatorSensors[1] = new DigitalInput(itemInt);
    				break;
    			
    			case "proximity sensor 3":
    				elevatorSensors[2] = new DigitalInput(itemInt);
    				break;
    			
    			case "proximity sensor 4":
    				elevatorSensors[3] = new DigitalInput(itemInt);
    				break;
    			
    			case "proximity sensor 5":
    				elevatorSensors[4] = new DigitalInput(itemInt);
    				break;
    			
    			case "proximity sensor 6":
    				elevatorSensors[5] = new DigitalInput(itemInt);
    				break;
    			
    			case "bottom limit switch":
    				bottomLimitSwitch = new DigitalInput(itemInt);
    				break;
    			
    			case "top limit switch":
    				topLimitSwitch = new DigitalInput(itemInt);
    				break;
    			
    			case "ball limit switch":
    				ballLimitSwitch = new DigitalInput(itemInt);
    				break;
    			
    			default:
    				System.err.println("Unrecognized Sensor: " + k);
    		}
    	}
    }
}