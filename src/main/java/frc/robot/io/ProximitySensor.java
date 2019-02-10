package frc.robot.io;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Contains a proximity sensor
 * 
 * TODO: everything
 * @author Alex Pickering
 */
public class ProximitySensor {
	//I think (hope) they're DI
	DigitalInput in;
	
	/**
	 * Default constructor
	 * 
	 * @param The analog input port
	 */
	public ProximitySensor(int port) {
		in = new DigitalInput(port);
	}
	
	/**
	 * Gets whether or not the sensor is tripped
	 * 
	 * @return Whether or not something has been detected
	 */
	public boolean detected() {
		return false;
	}
}
