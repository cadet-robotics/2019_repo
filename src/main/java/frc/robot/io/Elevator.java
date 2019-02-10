package frc.robot.io;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.Nexus;
import frc.robot.commands.ElevatorCommand;

/**
 * Contains stuff for the elevator
 * Javadoc comments lovingly provided by Alex Pickering
 * 
 * @author Owen Avery, Alex Pickering
 */
public class Elevator {
    Encoder encoder;
    Motors motors;
    
    //The current location or height of the elevator
    int location;
    
    /**
     * Default constructor
     * 
     * @param m Motors instance to use
     * @author Alex Pickering
     */
    public Elevator(Motors m) {
        motors = m;
    }
    
    /**
     * Nexus constructor
     * 
     * @param n Nexus instance to use
     * @author Owen Avery
     */
    public Elevator(Nexus n) {
        this(n.getMotors());
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
    	
    	ElevatorCommand movement = new ElevatorCommand(toPosition, location);
    	movement.start();
    	
    	location = toPosition;
    	return true;
    }
}