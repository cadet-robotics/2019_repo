package frc.robot.sensors;

import com.google.gson.JsonObject;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.config.ConfigHandlerInt;

/**
 * Contains the sensors
 * Javadoc comment lovingly provided by Alex Pickering
 *
 * Later modified to extend ConfigHandlerInt
 *
 * @author Owen Avery, Alex Pickering
 */
public class Sensors extends ConfigHandlerInt {
	//null until we have a gyro on the robot
    public Gyro gyro = null; //new ADXRS450_Gyro();

    public ProximitySensor[] elevatorSensors = new ProximitySensor[6];

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
				elevatorSensors[0] = new ProximitySensor(itemInt);
				break;

			case "proximity sensor 2":
				elevatorSensors[1] = new ProximitySensor(itemInt);
				break;

			case "proximity sensor 3":
				elevatorSensors[2] = new ProximitySensor(itemInt);
				break;

			case "proximity sensor 4":
				elevatorSensors[3] = new ProximitySensor(itemInt);
				break;

			case "proximity sensor 5":
				elevatorSensors[4] = new ProximitySensor(itemInt);
				break;

			case "proximity sensor 6":
				elevatorSensors[5] = new ProximitySensor(itemInt);
				break;

			default:
				System.err.println("Unrecognized Sensor: " + k);
		}
	}
}