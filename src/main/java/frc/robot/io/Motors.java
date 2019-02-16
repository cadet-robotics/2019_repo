package frc.robot.io;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;

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
	public CANSparkMax frontLeftDrive,
					   frontRightDrive,
					   backLeftDrive,
					   backRightDrive;
	
	public Talon leftElevator,
				 rightElevator;
	
	public VictorSP leftClaw,
					rightClaw;
			 
	
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
		if(debug) System.out.println("INIT MOTORS");
		
		this.configJSON = configJSON;
		
		loadMotors();
	}
	
	/**
	 * Loads the motors from the config
	 */
	public void loadMotors() {
		JsonObject pwmJSON = configJSON.getAsJsonObject("pwm");
		
		configuredMotors = new ArrayList<>();
		
		for(String k : pwmJSON.keySet()) {
			if(k.equals("desc") || k.contains("placeholder")) continue;
			
			JsonObject item = pwmJSON.getAsJsonObject(k);
			int itemInt = item.get("id").getAsInt();
			configuredMotors.add(k);
			
			switch(k) {
				case "front left":
					frontLeftDrive = new CANSparkMax(itemInt, MotorType.kBrushed);
					System.out.println("FLD: " + itemInt);
					break;
				
				case "front right":
					frontRightDrive = new CANSparkMax(itemInt, MotorType.kBrushed);
					System.out.println("FRD: " + itemInt);
					break;
				
				case "rear left":
					backLeftDrive = new CANSparkMax(itemInt, MotorType.kBrushed);
					System.out.println("BLD: " + itemInt);
					break;
				
				case "rear right":
					backRightDrive = new CANSparkMax(itemInt, MotorType.kBrushed);
					System.out.println("BRD: " + itemInt);
					break;
				
				case "left elevator":
					leftElevator = new Talon(itemInt);
					break;
				
				case "right elevator":
					rightElevator = new Talon(itemInt);
					break;
				
				case "left claw wheel":
					leftClaw = new VictorSP(itemInt);
					break;
				
				case "right claw wheel":
					rightClaw = new VictorSP(itemInt);
					break;
				
				default:
					configuredMotors.remove(k);
                    System.err.println("Unrecognized motor: " + k);
			}
		}
	}
	
	/**
	 * Sets all motors to 0 for safety
	 */
	public void resetAll() {
		frontLeftDrive.set(0);
		frontRightDrive.set(0);
		backLeftDrive.set(0);
		backRightDrive.set(0);
		
		leftElevator.set(0);
		rightElevator.set(0);
		
		leftClaw.set(0);
		rightClaw.set(0);
	}
}
