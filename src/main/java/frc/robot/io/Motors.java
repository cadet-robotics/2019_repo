package frc.robot.io;

import com.google.gson.JsonObject;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.config.ConfigUtil;

import java.util.ArrayList;

/**
 * The legibility-orient rewrite of the motors class
 *
 * <p>Later modified to use ConfigUtil
 *
 * @author Alex Pickering, Owen Avery
 */
public class Motors {
	JsonObject config;
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

    /**
     * Gets the list of configured motors
     *
     * @return A list of configured motors
     */
    public ArrayList<String> getConfiguredMotors() {
        return configuredMotors;
    }

    public Motors(JsonObject configIn) {
        config = configIn;
		ConfigUtil.loadAll(configIn, "pwm", (k, v) -> {
			JsonObject obj = ConfigUtil.getObject(v);
			if (obj == null) return;
			Integer itemInt = ConfigUtil.getInt(obj.get("id"));
			if (itemInt == null) return;
			switch(k) {
				case "front left":
					frontLeftDrive = new CANSparkMax(itemInt, MotorType.kBrushed);
					break;

				case "front right":
					frontRightDrive = new CANSparkMax(itemInt, MotorType.kBrushed);
					break;

				case "rear left":
					backLeftDrive = new CANSparkMax(itemInt, MotorType.kBrushed);
					break;

				case "rear right":
					backRightDrive = new CANSparkMax(itemInt, MotorType.kBrushed);
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
					System.err.println("Unrecognized motor: " + k);
					return;
			}
			configuredMotors.add(k);
		});
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