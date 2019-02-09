package frc.robot.io;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.Nexus;

/**
 * Contains stuff for the elevator
 * Javadoc comments lovingly provided by Alex Pickering
 * 
 * @author Owen Avery
 */
public class Elevator {
    Encoder encoder;

    SpeedController motor;
    
    /**
     * Default constructor
     * 
     * @param m Motors instance to use
     */
    public Elevator(Motors m) {
        motor = m.getSpeedController("elevator");
    }
    
    /**
     * Nexus constructor
     * 
     * @param n Nexus instance to use
     */
    public Elevator(Nexus n) {
        this(n.getMotors());
    }
}