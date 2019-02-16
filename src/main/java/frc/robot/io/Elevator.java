package frc.robot.io;

import edu.wpi.first.wpilibj.Encoder;
import frc.robot.Robot;
import frc.robot.commands.ElevatorCommand;

/**
 * Contains stuff for the elevator
 * Javadoc comments lovingly provided by Alex Pickering
 * 
 * @author Owen Avery, Alex Pickering
 */
public class Elevator {
    Encoder encoder;
    Robot nexus;
    
    //The current location or height of the elevator
    int location;
    
    /**
     * Default constructor
     * 
     * @param nexus Nexus instance to use
     * @author Alex Pickering
     */
    public Elevator(Robot nexus) {
        this.nexus = nexus;
    }
    
    /**
     * Moves the elevator to a given position
     * 
     * @param toPosition The position to move to (0-n)
     * @return Whether or not it moved
     */
    public boolean moveTo(int toPosition) {
    	//Make sure the position is correct
    	if(toPosition < 0 || toPosition > 5 || location == toPosition) return false;
    	
    	ElevatorCommand movement = new ElevatorCommand(toPosition, location, nexus);
    	movement.start();
    	
    	location = toPosition;
    	return true;
    }
}