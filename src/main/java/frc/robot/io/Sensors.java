package frc.robot.io;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.SightData;

public class Sensors {
    public static Gyro gyro = new ADXRS450_Gyro();

    public static SightData seeInstance = new SightData();
}
