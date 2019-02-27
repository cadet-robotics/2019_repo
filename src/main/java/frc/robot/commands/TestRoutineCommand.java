package frc.robot.commands;

import java.util.HashMap;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Test Routine
 * <p>Runs a routine to test the various parts of the robot
 * 
 * @author Alex Pickering
 */
public class TestRoutineCommand extends Command {
	int END_TIME = 1000;	//Number of ticks to run for
	
	int totalTime = 0,
		taskTime = 0,
		currentTask;
	
	Robot nexus;
	
	TestRoutineTask prevTask;
	
	HashMap<String, Integer> taskLengths = new HashMap<>();
	
	TestRoutineTask[] taskOrder = {
		TestRoutineTask.DRIVE_FORWARDS,
		TestRoutineTask.DRIVE_BACKWARDS,
		TestRoutineTask.DRIVE_LEFT,
		TestRoutineTask.DRIVE_RIGHT,
		TestRoutineTask.TURN_LEFT,
		TestRoutineTask.TURN_RIGHT,
		TestRoutineTask.ELEVATOR_UP,
		TestRoutineTask.ELEVATOR_DOWN,
		TestRoutineTask.ELEVATOR_0,
		TestRoutineTask.DELAY,
		TestRoutineTask.ELEVATOR_1,
		TestRoutineTask.DELAY,
		TestRoutineTask.ELEVATOR_2,
		TestRoutineTask.DELAY,
		TestRoutineTask.ELEVATOR_3,
		TestRoutineTask.DELAY,
		TestRoutineTask.ELEVATOR_4,
		TestRoutineTask.DELAY,
		TestRoutineTask.ELEVATOR_5,
		TestRoutineTask.DELAY,
		TestRoutineTask.ELEVATOR_0,
		TestRoutineTask.GET_BALL,
		TestRoutineTask.EJECT_BALL_MOVE,
		TestRoutineTask.EJECT_BALL_EJECT,
		TestRoutineTask.CLOSE_CLAW,
		TestRoutineTask.OPEN_CLAW,
		TestRoutineTask.DELAY
	};
	
	boolean elevatorCommandMade = false;
	
	@Override
	protected void initialize() {
		//Claim the subsystems from the driver
		nexus.setClawRunning(true);
		nexus.setElevatorRunning(true);
		
		//Common lengths
		int one = fromSeconds(1),
			half = fromSeconds(0.5);
		
		//Setup lengths map
		taskLengths.put(TestRoutineTask.CLOSE_CLAW.toString(), one);
		taskLengths.put(TestRoutineTask.DRIVE_BACKWARDS.toString(), one);
		taskLengths.put(TestRoutineTask.DRIVE_FORWARDS.toString(), one);
		taskLengths.put(TestRoutineTask.DRIVE_LEFT.toString(), one);
		taskLengths.put(TestRoutineTask.DRIVE_RIGHT.toString(), one);
		taskLengths.put(TestRoutineTask.EJECT_BALL_EJECT.toString(), half);
		taskLengths.put(TestRoutineTask.EJECT_BALL_MOVE.toString(), fromSeconds(1.5));
		taskLengths.put(TestRoutineTask.ELEVATOR_0.toString().substring(0, 8), one);
		taskLengths.put(TestRoutineTask.ELEVATOR_DOWN.toString(), one);
		taskLengths.put(TestRoutineTask.ELEVATOR_UP.toString(), one);
		taskLengths.put(TestRoutineTask.GET_BALL.toString(), fromSeconds(2.5));
		taskLengths.put(TestRoutineTask.OPEN_CLAW.toString(), half);
		taskLengths.put(TestRoutineTask.TURN_LEFT.toString(), half);
		taskLengths.put(TestRoutineTask.TURN_RIGHT.toString(), half);
		taskLengths.put(TestRoutineTask.DELAY.toString(), half);
		
		END_TIME = 0;
		
		//Get total time
		for(TestRoutineTask t : taskOrder) {
			END_TIME += taskLengths.get(t.toString()) + 1;
		}
		
		System.out.println("TEST ROUTINE BEGINNING");
	}
	
	@Override
	protected void execute() {
		//Get current task
		TestRoutineTask task = taskOrder[currentTask = getCurrentTask()];
		
		if(task != prevTask) {
			nexus.getMotors().resetAll();
			
			//Maintain elevators
			nexus.getMotors().leftElevator.set(-Robot.ELEVATOR_MAINTENANCE_SPEED);
			nexus.getMotors().rightElevator.set(Robot.ELEVATOR_MAINTENANCE_SPEED);
			
			prevTask = task;
			taskTime = 0;
			elevatorCommandMade = false;
		}
		
		System.out.println(task);
		
		//Run the task
		switch(task) {
			case CLOSE_CLAW:
				nexus.pneumatics.clawSolenoid.set(Robot.CLAW_CLOSED);
				break;
				
			case DELAY:
				break;
				
			case DRIVE_BACKWARDS:
				nexus.getDriveSystem().driveCartesian(-0.75, 0, 0);
				break;
				
			case DRIVE_FORWARDS:
				nexus.getDriveSystem().driveCartesian(0.75, 0, 0);
				break;
				
			case DRIVE_LEFT:
				nexus.getDriveSystem().driveCartesian(0, 0.75, 0);
				break;
				
			case DRIVE_RIGHT:
				nexus.getDriveSystem().driveCartesian(0, -0.75, 0);
				break;
				
			case EJECT_BALL_EJECT:
				nexus.getMotors().leftClaw.set(Robot.GET_BALL_DIRECTION ? Robot.CLAW_WHEEL_SPEED : -Robot.CLAW_WHEEL_SPEED);
				nexus.getMotors().rightClaw.set(Robot.GET_BALL_DIRECTION ? -Robot.CLAW_WHEEL_SPEED : Robot.CLAW_WHEEL_SPEED);
				break;
			
			case ELEVATOR_0:
				if(!elevatorCommandMade) {
					nexus.getElevator().moveTo(0);
					elevatorCommandMade = true;
				}
				break;
			
			case ELEVATOR_1:
				if(!elevatorCommandMade) {
					nexus.getElevator().moveTo(1);
					elevatorCommandMade = true;
				}
				break;
			
			case ELEVATOR_2:
				if(!elevatorCommandMade) {
					nexus.getElevator().moveTo(2);
					elevatorCommandMade = true;
				}
				break;
			
			case ELEVATOR_3:
				if(!elevatorCommandMade) {
					nexus.getElevator().moveTo(3);
					elevatorCommandMade = true;
				}
				break;
			
			case ELEVATOR_4:
				if(!elevatorCommandMade) {
					nexus.getElevator().moveTo(4);
					elevatorCommandMade = true;
				}
				break;
			
			case ELEVATOR_5:
				if(!elevatorCommandMade) {
					nexus.getElevator().moveTo(5);
					elevatorCommandMade = true;
				}
				break;
			
			case ELEVATOR_DOWN:
				nexus.getMotors().leftElevator.set(ElevatorCommand.SPEED + Robot.ELEVATOR_MAINTENANCE_SPEED);
				nexus.getMotors().rightElevator.set(-(ElevatorCommand.SPEED + Robot.ELEVATOR_MAINTENANCE_SPEED));
				break;
			
			case ELEVATOR_UP:
			case EJECT_BALL_MOVE:
				nexus.getMotors().leftElevator.set(-(ElevatorCommand.SPEED + Robot.ELEVATOR_MAINTENANCE_SPEED));
				nexus.getMotors().rightElevator.set(ElevatorCommand.SPEED + Robot.ELEVATOR_MAINTENANCE_SPEED);
				break;
			
			case GET_BALL:
				nexus.getMotors().leftClaw.set(Robot.GET_BALL_DIRECTION ? -Robot.CLAW_WHEEL_SPEED : Robot.CLAW_WHEEL_SPEED);
				nexus.getMotors().rightClaw.set(Robot.GET_BALL_DIRECTION ? Robot.CLAW_WHEEL_SPEED : -Robot.CLAW_WHEEL_SPEED);
				break;
			
			case OPEN_CLAW:
				nexus.getPneumatics().clawSolenoid.set(Robot.CLAW_OPEN);
				break;
			
			case TURN_LEFT:
				nexus.getDriveSystem().driveCartesian(0, 0, 0.75);
				break;
			
			case TURN_RIGHT:
				nexus.getDriveSystem().driveCartesian(0, 0, -0.75);
				break;
			
			default:
				System.out.println("Task not yet implemented");
		}
		
		taskTime++;
		totalTime++;
	}
	
	/**
	 * Determines the current task based on the time
	 * 
	 * @return The id of the current task
	 */
	private int getCurrentTask() {
		if(taskTime < taskLengths.get(prevTask.toString()) || currentTask >= taskOrder.length - 1)
			return currentTask;
		
		return currentTask;
	}
	
	/**
	 * Converts seconds to robot ticks
	 * 
	 * @param seconds The number of seconds
	 * @return The number of ticks
	 */
	private int fromSeconds(double seconds) {
		return (int) (seconds * 20);
	}
	
	@Override
	protected boolean isFinished() {
		return totalTime == END_TIME;
	}
	
	@Override
	protected void end() {
		System.out.println("TEST ROUTINE COMPLETE");
		
		nexus.setClawRunning(false);
		nexus.setElevatorRunning(false);
	}
	
	/**
	 * Basically a list of task names
	 * 
	 * @author Alex Pickering
	 */
	private enum TestRoutineTask {
		DRIVE_FORWARDS,
		DRIVE_BACKWARDS,
		DRIVE_LEFT,
		DRIVE_RIGHT,
		TURN_LEFT,
		TURN_RIGHT,
		ELEVATOR_UP,
		ELEVATOR_DOWN,
		ELEVATOR_0,
		ELEVATOR_1,
		ELEVATOR_2,
		ELEVATOR_3,
		ELEVATOR_4,
		ELEVATOR_5,
		GET_BALL,
		EJECT_BALL_MOVE,
		EJECT_BALL_EJECT,
		CLOSE_CLAW,
		OPEN_CLAW,
		DELAY
	}
}
