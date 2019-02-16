package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.commands.subsystems.ElevatorSubsystem;

/**
 * Command that moves the elevator to a certain position
 * 
 * @author Alex Pickering
 */
public class ElevatorCommand extends Command {
	static final double SPEED = 0.25;
	
	int currentPosition,
		toPosition;
	
	boolean dir;
	
	Robot nexus;
	
	/**
	 * Default constructor
	 * Moves the elevator to the given position
	 * 
	 * @param position The position to move to
	 * @param startPosition The position the elevator started in
	 * @param nexus The robot instance
	 */
	public ElevatorCommand(int position, int startPosition, Robot nexus) {
		super("ElevatorCommand");
		requires(ElevatorSubsystem.getInstance());
		
		this.nexus = nexus;
		currentPosition = startPosition;
		toPosition = position;
		
		dir = currentPosition < toPosition;
	}
	
	@Override
	protected void execute() {
		//Find current position
		for(int i = 0; i < nexus.getSensors().elevatorSensors.length; i++) {
			if(nexus.getSensors().elevatorSensors[i].detected()) {
				currentPosition = i;
			}
		}
		
		if(isFinished()) return;
		
		if(currentPosition > toPosition) { //Move down
			nexus.getMotors().leftElevator.set(SPEED);
			nexus.getMotors().rightElevator.set(-SPEED);
		} else { //Move up
			nexus.getMotors().leftElevator.set(-SPEED);
			nexus.getMotors().rightElevator.set(SPEED);
		}
	}
	
	@Override
	protected boolean isFinished() {
		return currentPosition == toPosition;
	}
	
	@Override
	protected void end() {
		nexus.getMotors().leftElevator.set(0);
		nexus.getMotors().rightElevator.set(0);
	}
}
