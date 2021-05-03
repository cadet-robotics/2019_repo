package frc.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.AutoLock;
import frc.robot.Nexus;
import frc.robot.Robot;
import frc.robot.SightTarget;
import frc.robot.io.Drive;
import frc.robot.sensors.SightData;

public class AutoStrafeCommand extends PIDCommand {
    private static final double P = 0.4;
    private static final double I = 0;
    private static final double D = 0;

    Robot robot;
    Drive drive;
    SightData see;

    public AutoStrafeCommand(Nexus nexus) {
        super(P, I, D);
        robot = nexus.getRobot();
        drive = nexus.getDriveSystem();
        see = nexus.getSightData();
    }

    @Override
    protected boolean isFinished() {
        return !robot.isAutonomous();
    }

    private double runningAverage = 0;
    private SightTarget old = null;

    @Override
    protected double returnPIDInput() {
        SightTarget t = see.getBest();
        if ((t == null) || (t == old)) return 0;
        return runningAverage = runningAverage / 5 * 4 + (Math.tan(t.getHAngle()) * t.getDist() * 5) / 5;
    }

    @Override
    protected void usePIDOutput(double output) {
        drive.autoStrafe = AutoLock.clampAbs(output, 0.1, 0.3);
    }
}