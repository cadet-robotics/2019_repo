package frc.robot.io;

import com.google.gson.JsonObject;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.config.ConfigHandlerInt;

/**
 * Contains pneumatics objects and handles loading their config
 *
 * Later modified to extend ConfigHandlerInt
 *
 * @author Alex Pickering, Owen Avery
 */
public class Pneumatics extends ConfigHandlerInt {
	public DoubleSolenoid clawSolenoid;
	
	int[] clawSolenoidPorts = new int[2];

	public Pneumatics(JsonObject configIn) {
		super(configIn, "pcm");
		finishInit();
		clawSolenoid = new DoubleSolenoid(clawSolenoidPorts[0], clawSolenoidPorts[1]);
	}

	@Override
	public void error() {
	}

	boolean debug = false;

	@Override
	public boolean isDebug() {
		return debug;
	}

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
}