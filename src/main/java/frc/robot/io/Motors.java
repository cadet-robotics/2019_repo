package frc.robot.io;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import frc.robot.config.ConfigLoader;

/**
 * The legibility-orient rewrite of the motors class
 * 
 * @author Alex Pickering
 */
public class Motors {
	//Config object
	JsonObject configJSON;
	
	//Configured motors record
	ArrayList<String> configuredMotors = new ArrayList<>();
	
	//Motor Objects
	public Spark frontLeftDrive,
				 frontRightDrive,
				 backLeftDrive,
				 backRightDrive;
	
	public Talon leftElevator,
				 rightElevator;
			 
	
	boolean debug = true;
	
	/**
	 * Gets the list of configured motors
	 * 
	 * @return A list of configured motors
	 */
	public ArrayList<String> getConfiguredMotors() {
		return configuredMotors;
	}
	
	/**
	 * Initalizes the motors
	 * 
	 * @param configJSON The isntance of the configuration file
	 */
	public void init(JsonObject configJSON) {
		this.configJSON = configJSON;
		
		try {
			loadMotors();
		} catch(IOException e) {
			System.err.println("Failed to load motors");
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the motors from the config
	 * 
	 * @throws IOException
	 */
	public void loadMotors() throws IOException {
		JsonObject pwmJSON = ConfigLoader.loadConfigFile().getAsJsonObject("pwm");
		
		//Debug - output all json
		if(debug) {
			for(String s : configJSON.keySet()) {
				System.out.println(s + ": " + pwmJSON.get(s));
			}
		}
		
		configuredMotors = new ArrayList<>();
		
		for(String k : pwmJSON.keySet()) {
			JsonElement item = pwmJSON.get(k);
			int itemInt = item.getAsInt();
			configuredMotors.add(k);
			
			switch(k) {
				case "front left":
					frontLeftDrive = new Spark(itemInt);
					break;
				
				case "front right":
					frontRightDrive = new Spark(itemInt);
					break;
				
				case "rear left":
					backLeftDrive = new Spark(itemInt);
					break;
				
				case "rear right":
					backRightDrive = new Spark(itemInt);
					break;
				
				case "left elevator":
					leftElevator = new Talon(itemInt);
					break;
				
				case "right elevator":
					rightElevator = new Talon(itemInt);
					break;
				
				default:
					configuredMotors.remove(k);
                    if(!k.equals("desc") && !k.contains("placeholder")) System.err.println("Unrecognized control: " + k);
			}
		}
	}
}
