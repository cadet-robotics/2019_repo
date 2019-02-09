package frc.robot.io;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.Nexus;

public class Elevator {
    Encoder encoder;

    SpeedController motor;

    public Elevator(Motors m) {
        motor = m.getSpeedController("elevator");
    }

    public Elevator(Nexus n) {
        this(n.getMotors());
    }
}