package frc.robot.io;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.SightData;

/**
 * Contains the sensors
 * Javadoc comment lovingly provided by Alex Pickering
 * 
 * @author Owen Avery
 */
public class Sensors {
    public static Gyro gyro = new ADXRS450_Gyro();

    public static SightData seeInstance = new SightData();
}
