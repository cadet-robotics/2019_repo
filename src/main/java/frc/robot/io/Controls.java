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
        zAxis = 2;
    
    //Buttons
    int autoLockButtonPort = 5;
    int[] elevatorPos = new int[6];

    boolean debug = true;
    
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

                default:
                    configuredControls.remove(k);
                    if(!k.equals("desc") && !k.contains("placeholder")) System.err.println("Unrecognized control: " + k);
            }
        }
    }
}