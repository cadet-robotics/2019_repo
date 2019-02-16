/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.google.gson.JsonObject;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.config.ConfigLoader;
import frc.robot.io.*;
import frc.robot.sensors.ProximitySensor;
import frc.robot.sensors.Sensors;
import frc.robot.sensors.SightData;

import java.io.IOException;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot implements Nexus {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private final SendableChooser<String> m_chooser = new SendableChooser<>();
	
	static final double DRIVE_MODIFIER = 0.8,			//Multiplier for teleop drive motors
						DRIVE_THRESHOLD = 0.1,			//Threshold for the teleop controls
						ELEVATOR_MANUAL_SPEED = 0.4,	//Manual control speed for the elevator
						ELEVATOR_MAINTENANCE_SPEED = 0,	//Speed to keep the elevator in place
						CLAW_WHEEL_SPEED = 0.7;			//Speed of the claw's wheels

	private static final boolean debug = true;

	public JsonObject configJSON;
	
	public UsbCamera driverCamera;

	public Controls controls = new Controls();

	public Motors motors = new Motors();

	public Drive drive = null;

	public Sensors sensors = new Sensors();
	
	public Pneumatics pneumatics = new Pneumatics();
	
	public SightData sightData;

	public Elevator elevator;
	
	boolean throttle = true,
			elevatorThrottle = false,
			clawOpen = true;
	
	//New Press booleans
	boolean newElevatorPress = true,
			newClawTogglePress = true;

	//public UpdateLineManager lineManager = null;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
		m_chooser.addOption("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		sightData = new SightData(NetworkTableInstance.getDefault());
		
		try{
			configJSON = ConfigLoader.loadConfigFile();
		} catch(IOException e){
			e.printStackTrace();
		}
		
		//Initialize configured classes
		controls.init(configJSON);
		motors.init(configJSON);
		sensors.init(configJSON);
		pneumatics.init(configJSON);
		
		drive = new Drive(this);
		elevator = new Elevator(this);
		
		driverCamera = CameraServer.getInstance().startAutomaticCapture(0);
		driverCamera.setFPS(15);

		if(debug){
			for(String s : controls.getConfiguredControls()){
				System.out.println(s);
			}
		}
		/*
		drive =
		UpdateLineManager m = new UpdateLineManager(NetworkTableInstance.getDefault(), seeInstance);
		*/
	}

	/**
	 * This function is called every robot packet, no matter the mode. Use
	 * this for items like diagnostics that you want ran during disabled,
	 * autonomous, teleoperated and test.
	 *
	 * <p>This runs after the mode specific periodic functions, but before
	 * LiveWindow and SmartDashboard integrated updating.
	 */
	@Override
	public void robotPeriodic() {
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */

	private AutoLock autoCommand;
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
		if (autoCommand != null) autoCommand.cancel();
		(autoCommand = new AutoLock(this)).start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		/*
		switch (m_autoSelected) {
		case kCustomAuto:
			// Put custom auto code here
			break;
		case kDefaultAuto:
		default:
			// Put default auto code here
			break;
		}
		*/
		Scheduler.getInstance().run();
		drivePeriodic();
	}

	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		motors.resetAll();
		drivePeriodic();
		runElevator();
		runClaw();
		
		if(debug) runDebug();
	}
	
	/**
	 * Outputs debug
	 */
	public void runDebug() {
		String s = "Proximity ";
		
		for(int i = 0; i < sensors.elevatorSensors.length; i++) {
			s += i + ": " + sensors.elevatorSensors[i].detected() + " ";
		}
		
		//System.out.println(s);
		//System.out.println("X: " + controls.getXAxis() + " Y: " + controls.getYAxis() + " Z: " + controls.getZAxis());
		System.out.println(clawOpen);
		//System.out.println(controls.getThrottleAxis());
	}
	
	/**
	 * Runs the claw (opening, closing, wheels)
	 */
	public void runClaw() {
		//Open and close the claw
		if(controls.getToggleClaw() && newClawTogglePress) {
			clawOpen = !clawOpen;
			newClawTogglePress = false;
			
			//toggle solenoids
			if(clawOpen) {
				pneumatics.clawSolenoid.set(DoubleSolenoid.Value.kForward);
			} else {
				pneumatics.clawSolenoid.set(DoubleSolenoid.Value.kReverse);
			}
		} else if(!controls.getToggleClaw() && !newClawTogglePress) {
			newClawTogglePress = true;
		}
		
		//Manage the ball
		if(clawOpen) { //Can only run if the claw is open
			double clawSpeed = 0;
			
			if(controls.getClawWheelsIn()) clawSpeed += CLAW_WHEEL_SPEED;
			if(controls.getClawWheelsOut()) clawSpeed -= CLAW_WHEEL_SPEED;
			
			motors.leftClaw.set(clawSpeed);
			motors.rightClaw.set(clawSpeed);
		}
	}
	
	/**
	 * Runs the elevator
	 */
	public void runElevator() {
		//This section runs the elevator to specific positions
		boolean elevatorButtonsPressed = false;
		int elevatorPressIndex = -1;
		
		for(int i = 0 ; i < 6; i++) {
			if(controls.getElevatorButton(i)) {
				elevatorButtonsPressed = true;
				elevatorPressIndex = i;
				break;
			}
		}
		
		//New button presses (don't spam every tick)
		if(elevatorButtonsPressed && newElevatorPress) {
			elevator.moveTo(elevatorPressIndex);
		} else if(!elevatorButtonsPressed && !newElevatorPress) {
			newElevatorPress = true;
		}
		
		//This section runs the elevator manually
		double elevatorSpeed = 0;
		if(controls.getElevatorUp()) elevatorSpeed += ELEVATOR_MANUAL_SPEED;
		if(controls.getElevatorDown()) elevatorSpeed -= ELEVATOR_MANUAL_SPEED;
		
		double t = mapDouble(controls.getThrottleAxis(), 1, -1, 0, 1);
		//System.out.println(t);
		
		elevatorSpeed += t /*ELEVATOR_MAINTENANCE_SPEED*/;
		
		motors.leftElevator.set(-elevatorSpeed * (elevatorThrottle ? t : 1));
		motors.rightElevator.set(elevatorSpeed * (elevatorThrottle ? t : 1));
	}
	
	/**
	 * Runs the mecanum drive
	 */
	public void drivePeriodic() {
		double xAxis = -controls.getXAxis(),
			   yAxis = controls.getYAxis(),
			   zAxis = -controls.getZAxis();
		
		if(Math.abs(xAxis) < DRIVE_THRESHOLD) xAxis = 0;
		if(Math.abs(yAxis) < DRIVE_THRESHOLD) yAxis = 0;
		if(Math.abs(zAxis) < DRIVE_THRESHOLD) zAxis = 0;
		
		xAxis *= DRIVE_MODIFIER;
		yAxis *= DRIVE_MODIFIER;
		zAxis *= DRIVE_MODIFIER;
		
		if(throttle) {
			double t = mapDouble(controls.getThrottleAxis(), 1, -1, 0, 1);
			xAxis *= t;
			yAxis *= t;
			zAxis *= t;
		}
		
		if (!controls.isAutoLock()) drive.driveCartesian(xAxis, yAxis, zAxis);
	}
	
	/**
	 * Maps a value from range to range
	 * 
	 * @param val The value to map
	 * @param oldMin The old range's minimum
	 * @param oldMax The old range's maximum
	 * @param newMin The new range's minimum
	 * @param newMax The new range's maximum
	 * @return The new mapped value
	 */
	public double mapDouble(double val, double oldMin, double oldMax, double newMin, double newMax){
	  	return (((val - oldMin) * (newMax - newMin)) / (oldMax - oldMin)) + newMin;
  	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	@Override
	public void disabledInit() {
		if (autoCommand != null) {
			autoCommand.cancel();
			autoCommand = null;
		}
	}

	@Override
	public Controls getControls() {
		return controls;
	}

	@Override
	public Drive getDriveSystem() {
		return drive;
	}

	@Override
	public SightData getSightData() {
		return sightData;
	}

	@Override
	public Motors getMotors() {
		return motors;
	}

	@Override
	public Sensors getSensors() {
		return sensors;
	}
	
	@Override
	public Pneumatics getPneumatics() {
		return pneumatics;
	}

	@Override
	public Elevator getElevator() {
		return elevator;
	}

	@Override
	public Robot getRobot() {
		return this;
	}
}