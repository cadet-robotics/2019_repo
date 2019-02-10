package frc.robot.io;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Contains a proximity sensor
 * 
 * @author Alex Pickering
 */
public class ProximitySensor {
	AnalogInput in;
	
	/**
	 * Default constructor
	 * 
	 * @param The analog input port
	 */
	public ProximitySensor(int port) {
		in = new AnalogInput(port);
	}
	
	public boolean detected() {
		return false;
	}
}
