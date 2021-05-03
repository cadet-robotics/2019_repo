package frc.robot.io;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.AutoLock;
import frc.robot.Nexus;

/**
 * Contains the MecanumDrive for the robot
 * <p>Javadoc comments lovingly provided by Alex Pickering
 * 
 * @author Owen Avery
 */
public class Drive {
    private MecanumDrive drive;
    public boolean autoActive = false;
    public double autoStrafe = 0;
    
    /**
     * Default constructor
     * 
     * @param m The Motors instance to use
     */
    public Drive(Motors m) {
    	drive = new MecanumDrive(m.frontLeftDrive,
    							 m.backLeftDrive,
    							 m.frontRightDrive,
    							 m.backRightDrive);
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
     * <p>All values are the standard -1 to 1 range
     *
     * 
     * @param y Y speed to use
     * @param x X speed to use
     * @param r Rotation speed to use
     */
    public void driveCartesian(double y, double x, double r) {
        drive.driveCartesian(autoActive ? (y + AutoLock.clampAbs(autoStrafe, 0.2, 0.5)) : y, x, r);
    }
}