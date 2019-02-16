package frc.robot.io;

import com.google.gson.JsonObject;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.config.ConfigHandlerInt;

/**
 * Contains pneumatics objects and handles loading their config
 *
 * Later modified to extend ConfigHandlerInt
 *
 * @author Alex Pickering + Owen Avery
 */
public class Pneumatics extends ConfigHandlerInt {
	public DoubleSolenoid clawSolenoid;
	
	int[] clawSolenoidPorts;

	@Override
	public void preInit() {
		clawSolenoidPorts = new int[2];
	}

	boolean debug = false;

	@Override
	public boolean isDebug() {
		return debug;
	}

	public Pneumatics(JsonObject configIn) {
		super(configIn, "pcm");
	}

	/**
	 * Copied nearly wholesale from the old init method, initializes this class' objects
	 *
	 * @param k The name of the object
	 * @param itemInt The index of the object in the pcm/pwm ports/digital io ports/etc.
	 */
	@Override
	public void loadItem(String k, int itemInt) {
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

	@Override
	public void finalizeItems() {
		clawSolenoid = new DoubleSolenoid(clawSolenoidPorts[0], clawSolenoidPorts[1]);
	}

	@Override
	public void error() {

	}
}