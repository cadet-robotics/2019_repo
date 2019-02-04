package frc.robot.io;

import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class Drive {
    public MecanumDrive drive = null;

    public Drive(Motors m) {
        drive = new MecanumDrive(m.frontLeft, m.rearLeft, m.frontRight, m.rearRight);
    }
}