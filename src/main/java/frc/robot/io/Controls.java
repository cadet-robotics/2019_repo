package frc.robot.io;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.config.ConfigLoader;

/**
 * Contains controls objects and handles loading them from config
 * 
 * @author Alex Pickering
 */
public class Controls {
    //Config object
    JsonObject configJSON;
    
    //Config'd controls record
    ArrayList<String> configuredControls = new ArrayList<>();
    
    //Controls objects
    Joystick mainJoystick;
    
    //Configured control ports and such
    //Axes
    int mainJoystickPort = 0,
        xAxis = 0,
        yAxis = 1,
        zAxis = 2,
        throttleAxis = 3;
    
    //Buttons
    int autoLockButtonPort = 0,
    	elevatorUp = 5,
    	elevatorDown = 3,
    	clawWheelsIn = 1,
    	clawWheelsOut = 2,
    	toggleClaw = 4;
    int[] elevatorPos = new int[6];

    boolean debug = false;
    
    //Getters
    /**
     * Gets the X Axis control
     * @return The X Axis
     */
    public double getXAxis(){
        return mainJoystick.getRawAxis(xAxis);
    }
    
    /**
     * Gets the Y Axis control
     * @return The Y Axis
     */
    public double getYAxis(){
        return mainJoystick.getRawAxis(yAxis);
    }
    
    /**
     * Gets the Z Axis control
     * @return The Z Axis
     */
    public double getZAxis(){
        return mainJoystick.getRawAxis(zAxis);
    }
    
    /**
     * Gets the throttle axis control
     * 
     * @return The throttle axis
     */
    public double getThrottleAxis() {
    	return mainJoystick.getRawAxis(throttleAxis);
    }
    
    /**
     * Gets the manual raise elevator button
     * 
     * @return Elevator Up button state
     */
    public boolean getElevatorUp() {
    	return mainJoystick.getRawButton(elevatorUp);
    }
    
    /**
     * Gets the manual lower elevator button
     * 
     * @return Elevator Down button state
     */
    public boolean getElevatorDown() {
    	return mainJoystick.getRawButton(elevatorDown);
    }
    
    /**
     * Gets the run claw wheels in button
     * 
     * @return Claw Wheels In button state
     */
    public boolean getClawWheelsIn() {
    	return mainJoystick.getRawButton(clawWheelsIn);
    }
    
    /**
     * Gets the run claw wheels out button
     * 
     * @return Claw Wheels Out button state
     */
    public boolean getClawWheelsOut() {
    	return mainJoystick.getRawButton(clawWheelsOut);
    }
    
    /**
     * Gets the toggle claw button
     * 
     * @return Toggle Claw button state
     */
    public boolean getToggleClaw() {
    	return mainJoystick.getRawButton(toggleClaw);
    }
    
    /**
     * Gets an elevator position button
     * 
     * @param pos The position to check
     * @return Whether or not the position's button is pressed
     */
    public boolean getElevatorButton(int pos) {
    	return mainJoystick.getRawButton(elevatorPos[pos]);
    }

    /**
     * Gets the Auto Lock Button state
     * @return
     */
    public boolean isAutoLock() {
        return mainJoystick.getRawButton(autoLockButtonPort);
    }
    
    public ArrayList<String> getConfiguredControls(){
        return configuredControls;
    }
    
    /**
     * Initializes the controls
     * Loads from config and creates the objects
     */
    public void init(JsonObject configJSON){
        this.configJSON = configJSON;
        
        try{
            loadControls();
        } catch(IOException e){
            System.err.println("Failed to load controls");
            e.printStackTrace();
        }
        
        mainJoystick = new Joystick(mainJoystickPort);
    }
    
    /**
     * Loads the controls from config
     * @throws IOException
     */
    public void loadControls() throws IOException {
        JsonObject configJSON = ConfigLoader.loadConfigFile();
        JsonObject controlsJSON = configJSON.getAsJsonObject("controls");
        
        //Debug - outputs all the json
        if(debug){
            for(String s : configJSON.keySet()){
                System.out.println(s + ": " + configJSON.get(s));
            }
        }
        
        configuredControls = new ArrayList<>();
        
        for(String k : controlsJSON.keySet()){
            JsonElement item = controlsJSON.get(k);
            if(k.equals("desc") || k.contains("placeholder")) continue;
            int itemInt = item.getAsInt();
            configuredControls.add(k);
            
            switch(k){
                case "main joystick":
                    mainJoystickPort = itemInt;
                    break;
                
                case "main joystick x-axis":
                    xAxis = itemInt;
                    break;
                
                case "main joystick y-axis":
                    yAxis = itemInt;
                    break;
                
                case "main joystick z-axis":
                    zAxis = itemInt;
                    break;
                
                case "main joystick throttle axis":
                	throttleAxis = itemInt;
                	break;

                case "main joystick auto-lock":
                    autoLockButtonPort = itemInt;
                    break;
                
                case "elevator pos 1":
                	elevatorPos[0] = itemInt;
                	break;
                
                case "elevator pos 2":
                	elevatorPos[1] = itemInt;
                	break;
                
                case "elevator pos 3":
                	elevatorPos[2] = itemInt;
                	break;
                
                case "elevator pos 4":
                	elevatorPos[3] = itemInt;
                	break;
                
                case "elevator pos 5":
                	elevatorPos[4] = itemInt;
                	break;
                
                case "elevator pos 6":
                	elevatorPos[5] = itemInt;
                	break;
                
                case "elevator up":
                	elevatorUp = itemInt;
                	break;
                
                case "elevator down":
                	elevatorDown = itemInt;
                	break;
                
                case "claw in":
                	clawWheelsIn = itemInt;
                	break;
                
                case "claw out":
                	clawWheelsOut = itemInt;
                	break;
                
                case "toggle claw":
                	toggleClaw = itemInt;
                	break;

                default:
                    configuredControls.remove(k);
                    System.err.println("Unrecognized control: " + k);
            }
        }
    }
}