package frc.robot;

import frc.robot.io.*;

/**
 * Interface for things containing all subsystems
 * Javadoc by Alex Pickering
 * 
 * @author Owen Avery
 */
public interface Nexus {
    Controls getControls();

    Drive getDriveSystem();

    SightData getSightData();

    Motors getMotors();

    Sensors getSensors();

    Elevator getElevator();

    Robot getRobot();
}