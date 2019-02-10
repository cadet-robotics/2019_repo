package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command that moves the elevator to a certain position
 * 
 * @author Alex Pickering
 */
public class ElevatorCommand extends Command {
	
	/**
	 * Default constructor
	 * Moves the elevator to the given position
	 * 
	 * @param position The position to move to
	 * @param startPos The position the elevator started in
	 */
	public ElevatorCommand(int position, int startPos) {
		super("ElevatorCommand");
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
