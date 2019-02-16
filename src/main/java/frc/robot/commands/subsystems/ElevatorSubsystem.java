package frc.robot.commands.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ElevatorSubsystem extends Subsystem {
    private static ElevatorSubsystem instance = null;

    private ElevatorSubsystem() {
    }

    public static ElevatorSubsystem getInstance() {
        if (instance == null) instance = new ElevatorSubsystem();
        return instance;
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(null);
    }
}