package frc.robot;

import frc.robot.io.*;

public interface Nexus {
    Controls getControls();

    Drive getDriveSystem();

    SightData getSightData();

    Motors getMotors();

    Sensors getSensors();

    Elevator getElevator();

    Robot getRobot();
}