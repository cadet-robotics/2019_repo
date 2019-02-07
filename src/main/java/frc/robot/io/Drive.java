package frc.robot.io;

import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * Contains the mecanumdrive for the robot
 * Javadoc comments lovingly provided by Alex Pickering
 * 
 * @author Owen Avery
 */
public class Drive {
    public MecanumDrive drive;

    private boolean isAuto = false;
    
    /**
     * Default constructor
     * 
     * @param m The motors instance to use
     */
    public Drive(Motors m) {
        drive = new MecanumDrive(m.frontLeft, m.rearLeft, m.frontRight, m.rearRight);
    }
    
    /**
     * Sets whether or not auto is active
     * @param in
     */
    public void setIsAuto(boolean in) {
        isAuto = in;
    }
    
    /**
     * Runs the mecanum drive
     * All values are the standard -1 to 1 range
     * 
     * @param y Y speed to use
     * @param x X speed to use
     * @param r Rotation speed to use
     * @param isAutoCommand Whether or not this originates from an auto command
     */
    public void driveCartesian(double y, double x, double r, boolean isAutoCommand) {
        if (isAuto == isAutoCommand) {
            System.out.println(String.format("X: %.02f, Y: %.02f, R: %.02f", x, y, r) + y);
            drive.driveCartesian(y, x, r);
        }
    }
}