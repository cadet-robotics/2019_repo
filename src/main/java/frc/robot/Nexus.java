package frc.robot;

import frc.robot.io.*;

/**
 * Interface for things containing all subsystems
 * 
 * @author Owen Avery
 */
public interface Nexus {
    public Controls getControls();

    public Drive getDriveSystem();

    public SightData getSightData();

    public Motors getMotors();

    public Sensors getSensors();

    public Elevator getElevator();

    public Robot getRobot();
}