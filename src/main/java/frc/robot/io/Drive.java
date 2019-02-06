package frc.robot.io;

import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class Drive {
    public MecanumDrive drive;

    private boolean isAuto = false;

    public Drive(Motors m) {
        drive = new MecanumDrive(m.frontLeft, m.rearLeft, m.frontRight, m.rearRight);
    }

    public void setIsAuto(boolean in) {
        isAuto = in;
    }

    public void driveCartesian(double y, double x, double r, boolean isAutoCommand) {
        if (isAuto == isAutoCommand) {
            System.out.println(String.format("X: %d, Y: %d, R: %d", x, y, r) + y);
            drive.driveCartesian(y, x, r);
        }
    }
}