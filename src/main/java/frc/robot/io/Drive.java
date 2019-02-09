package frc.robot.io;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.Nexus;

/**
 * Contains the mecanumdrive for the robot
 * Javadoc comments lovingly provided by Alex Pickering
 * 
 * @author Owen Avery
 */
public class Drive {
    private MecanumDrive drive;
    
    /**
     * Default constructor
     * 
     * @param m The Motors instance to use
     */
    public Drive(Motors m) {
        drive = new MecanumDrive(m.frontLeft, m.rearLeft, m.frontRight, m.rearRight);
    }

    /**
     * Extra constructor that supports Nexus objects
     *
     * @param n The Nexus instance to use
     */
    public Drive(Nexus n) {
        this(n.getMotors());
    }


    
    /**
     * Runs the mecanum drive
     * All values are the standard -1 to 1 range
     * 
     * @param y Y speed to use
     * @param x X speed to use
     * @param r Rotation speed to use
     */
    public void driveCartesian(double y, double x, double r) {
        System.out.println(String.format("X: %.02f, Y: %.02f, R: %.02f", x, y, r) + y);
        drive.driveCartesian(y, x, r);
    }
}