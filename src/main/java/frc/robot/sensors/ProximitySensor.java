package frc.robot.sensors;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Contains a proximity sensor
 * 
 * @author Alex Pickering
 */
public class ProximitySensor {
	DigitalInput in;
	
	/**
	 * Default constructor
	 * 
	 * @param port The analog input port
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
		return in.get();
	}
}