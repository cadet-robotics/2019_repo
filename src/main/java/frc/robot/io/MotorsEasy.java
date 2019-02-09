package frc.robot.io;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import frc.robot.config.ConfigLoader;

/**
 * The legibility-orient rewrite of the motors class
 * 
 * @author Alex Pickering
 */
public class MotorsEasy {
	//Config object
	JsonObject configJSON;
	
	//Configured motors record
	ArrayList<String> configuredMotors = new ArrayList<>();
	
	//Motor Objects
	//This example uses victors
	PWMVictorSPX frontLeftDrive,
				 frontRightDrive,
				 backLeftDrive,
				 backRightDrive;
	
	boolean debug = true;
	
	//Getters
	/**
	 * Gets the front left drive motor
	 * 
	 * @return The front left drive motor
	 */
	public PWMVictorSPX getFrontLeftDrive() {
		return frontLeftDrive;
	}
	
	/**
	 * Gets the front right drive motor
	 * 
	 * @return The front right drive motor
	 */
	public PWMVictorSPX getFrontRightDrive() {
		return frontRightDrive;
	}
	
	/**
	 * Gets the back left drive motor
	 * 
	 * @return The back left drive motor
	 */
	public PWMVictorSPX getBackLeftDrive() {
		return backLeftDrive;
	}
	
	/**
	 * Gets the back right drive motor
	 * 
	 * @return The back right drive motor
	 */
	public PWMVictorSPX getBackRightDrive() {
		return backRightDrive;
	}
	
	/**
	 * Gets the list of configured motors
	 * 
	 * @return A list of configured motors
	 */
	public ArrayList<String> getConfiguredMotors() {
		return configuredMotors;
	}
	
	/**
	 * Initializes the motors
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
					frontLeftDrive = new PWMVictorSPX(itemInt);
					break;
				
				case "front right":
					frontRightDrive = new PWMVictorSPX(itemInt);
					break;
				
				case "rear left":
					backLeftDrive = new PWMVictorSPX(itemInt);
					break;
				
				case "rear right":
					backRightDrive = new PWMVictorSPX(itemInt);
					break;
				
				default:
					configuredMotors.remove(k);
                    if(!k.equals("desc") && !k.contains("placeholder")) System.err.println("Unrecognized control: " + k);
			}
		}
	}
}
