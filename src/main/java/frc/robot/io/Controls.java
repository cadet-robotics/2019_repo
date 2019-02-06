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
            configuredControls.add(k);
            
            switch(k){
                case "main joystick":
                    mainJoystickPort = item.getAsInt();
                    break;
                
                case "main joystick x-axis":
                    xAxis = item.getAsInt();
                    break;
                
                case "main joystick y-axis":
                    yAxis = item.getAsInt();
                    break;
                
                case "main joystick z-axis":
                    zAxis = item.getAsInt();
                    break;
                
                default:
                    configuredControls.remove(k);
                    if(!k.equals("desc")) System.err.println("Unrecognized control: " + k);
            }
        }
    }
}