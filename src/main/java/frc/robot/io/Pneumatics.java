package frc.robot.io;

import com.google.gson.JsonObject;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.config.ConfigUtil;

/**
 * Contains pneumatics objects and handles loading their config
 *
 * Later modified to use ConfigUtil
 *
 * @author Alex Pickering, Owen Avery
 */
public class Pneumatics {
	public DoubleSolenoid clawSolenoid;
	
	int[] clawSolenoidPorts = new int[2];

	public Pneumatics(JsonObject configIn) {
		ConfigUtil.loadAllInts(configIn, "pcm", (k, itemInt) -> {
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
		});
		clawSolenoid = new DoubleSolenoid(clawSolenoidPorts[0], clawSolenoidPorts[1]);
	}
}