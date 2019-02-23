package frc.robot.sensors;

import com.google.gson.JsonObject;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.config.ConfigHandlerInt;

/**
 * Contains the sensors
 * <p>Javadoc comment lovingly provided by Alex Pickering
 *
 * Javadoc comment lovingly provided by Alex Pickering
 *
 * Later modified to extend ConfigHandlerInt
 *
 * @author Owen Avery, Alex Pickering
 */
public class Sensors extends ConfigHandlerInt {
	//null until we have a gyro on the robot
    public Gyro gyro = null; //new ADXRS450_Gyro();
    
    public DigitalInput[] elevatorSensors = new DigitalInput[6];
    
    public DigitalInput topLimitSwitch,
    					bottomLimitSwitch,
    					ballLimitSwitch;

	@Override
	public void error() {
	}

    public Sensors(JsonObject configIn) {
        super(configIn, "dio");
        finishInit();
    }

	@Override
	public void loadItem(String k, int itemInt) {
		switch(k) {
			case "proximity sensor 1":
				elevatorSensors[0] = new DigitalInput(itemInt);
				break;
				case "proximity sensor 2":
					elevatorSensors[1] = new DigitalInput(itemInt);
					break;

				case "proximity sensor 3":
					elevatorSensors[2] = new DigitalInput(itemInt);
					break;

				case "proximity sensor 4":
					elevatorSensors[3] = new DigitalInput(itemInt);
					break;

				case "proximity sensor 5":
					elevatorSensors[4] = new DigitalInput(itemInt);
					break;

				case "proximity sensor 6":
					elevatorSensors[5] = new DigitalInput(itemInt);
					break;

				case "bottom limit switch":
					bottomLimitSwitch = new DigitalInput(itemInt);
					break;

				case "top limit switch":
					topLimitSwitch = new DigitalInput(itemInt);
					break;

				case "ball limit switch":
					ballLimitSwitch = new DigitalInput(itemInt);
					break;

				default:
					System.err.println("Unrecognized Sensor: " + k);
			}
	}
}