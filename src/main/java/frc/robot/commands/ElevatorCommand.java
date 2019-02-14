package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Command that moves the elevator to a certain position
 * 
 * @author Alex Pickering
 */
public class ElevatorCommand extends Command {
	static final double SPEED = 0.25;
	
	int currentPosition,
		toPosition,
		counter = 0;
	
	boolean dir;
	
	Robot nexus;
	
	/**
	 * Default constructor
	 * Moves the elevator to the given position
	 * 
	 * @param position The position to move to
	 * @param startPos The position the elevator started in
	 * @param nexus The robot instance
	 */
	public ElevatorCommand(int position, int startPosition, Robot nexus) {
		super("ElevatorCommand");
		
		this.nexus = nexus;
		currentPosition = startPosition;
		toPosition = position;
		
		dir = currentPosition < toPosition;
	}
	
	@Override
	protected void execute() {
		//TODO: once sensors exist, uncomment this and remove the other
		/*for(int i = 0; i < nexus.getSensors().elevatorSensors.length; i++) {
			if(nexus.getSensors().elevatorSensors[i].detected()) {
				currentPosition = i;
			}
		}*/
		
		//temporary until sensors exist
		if(++counter % 20 == 0) currentPosition = (dir ? currentPosition + 1 : currentPosition - 1);
		
		if(isFinished()) return;
		
		if(!dir /*currentPosition > toPosition*/) {
			nexus.getMotors().leftElevator.set(SPEED);
			nexus.getMotors().rightElevator.set(-SPEED);
		} else {
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
