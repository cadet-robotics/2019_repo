package frc.robot.io;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Contains pneumatics objects and handles loading their config
 * 
 * @author Alex Pickering
 */
public class Pneumatics {
	JsonObject configJSON;
	
	public DoubleSolenoid clawSolenoid;
	
	int[] clawSolenoidPorts = new int[2];
	
	boolean debug = false;
	
	/**
	 * Initialize objects, load config
	 * 
	 * @param configJSON The instance of the config file
	 */
	public void init(JsonObject configJSON) {
		if(debug) System.out.println("INIT PNEUMATICS");
		
		this.configJSON = configJSON;
		
		loadPneumatics();
		
		clawSolenoid = new DoubleSolenoid(clawSolenoidPorts[0], clawSolenoidPorts[1]);
	}
	
	/**
	 * Loads the pneumatics from the config
	 */
	public void loadPneumatics() {
		JsonObject pcmJSON = configJSON.getAsJsonObject("pcm");
		
		for(String k : pcmJSON.keySet()) {
			if(k.equals("desc") || k.contains("placeholder")) continue;
			JsonElement item = pcmJSON.get(k);
			int itemInt = item.getAsInt();
			
			switch(k) {
				case "left claw solenoid":
					clawSolenoidPorts[0] = itemInt;
					break;
				
				case "right claw solenoid":
					clawSolenoidPorts[1] = itemInt;
					break;
				
				default:
					System.err.println("Unrecognised Pneumatics Object: " + k);
			}
		}
	}
}
