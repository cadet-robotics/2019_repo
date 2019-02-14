/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.google.gson.JsonObject;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.config.ConfigLoader;
import frc.robot.io.*;

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
						ELEVATOR_MANUAL_SPEED = 0.4;	//Manual control speed for the elevator

	private static final boolean debug = true;

	public JsonObject configJSON;

	public Controls controls = new Controls();

	public Motors motors = new Motors();

	public Drive drive = null;

	public Sensors sensors = new Sensors();
	
	public SightData sightData;

	public Elevator elevator;
	
	boolean newElevatorPress = true,
			throttle = true;

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
		
		drive = new Drive(this);
		elevator = new Elevator(this);

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
		System.out.println("X: " + controls.getXAxis() + " Y: " + controls.getYAxis() + " Z: " + controls.getZAxis());
		
		Scheduler.getInstance().run();
		drivePeriodic();
		runElevator();
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
		
		if(elevatorButtonsPressed && newElevatorPress) {
			elevator.moveTo(elevatorPressIndex);
		} else if(!elevatorButtonsPressed && !newElevatorPress) {
			newElevatorPress = true;
		}
		
		//This section runs the elevator manually
		double elevatorSpeed = 0;
		if(controls.getElevatorUp()) elevatorSpeed += ELEVATOR_MANUAL_SPEED;
		if(controls.getElevatorDown()) elevatorSpeed -= ELEVATOR_MANUAL_SPEED;
		motors.leftElevator.set(-elevatorSpeed);
		motors.rightElevator.set(elevatorSpeed);
	}
	
	/**
	 * Runs the mecanum drive
	 */
	public void drivePeriodic() {
		double xAxis = controls.getXAxis(),
			   yAxis = controls.getYAxis(),
			   zAxis = controls.getZAxis();
		
		if(Math.abs(xAxis) < 0.25) xAxis = 0;
		if(Math.abs(yAxis) < 0.25) yAxis = 0;
		if(Math.abs(zAxis) < 0.25) zAxis = 0;
		
		if (!controls.isAutoLock()) drive.driveCartesian(xAxis, yAxis, zAxis);
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
	public Elevator getElevator() {
		return elevator;
	}

	@Override
	public Robot getRobot() {
		return this;
	}
}