package frc.robot.controls;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.config.ConfigLoader;
import frc.robot.config.DefaultParser;

/**
 * Contains controls objects and handles loading them from config
 * 
 * @author Alex Pickering
 */
public class Controls {
    //Controls objects
    Joystick mainJoystick;
    
    //Configured control ports and such
    //Axes
    static int mainJoystickPort = 0,
               xAxis = 0,
               yAxis = 1,
               zAxis = 2;
    
    static boolean debug = true;
    
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
     * Initializes the controls
     * Loads from config and creates the objects
     */
    public void init(){
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
        //Why did this take so much effort to figure out and get it to stop yelling at me
        @SuppressWarnings({"unchecked", "rawtypes"})
        HashMap<String, Integer> controlsMap = new HashMap<String, Integer>((Map) new ConfigLoader().load("CONTROLS", new DefaultParser()));
        
        for(String s : controlsMap.keySet()){
            if(debug) System.out.println(s);
            
            //Parse which control this is
            switch(s){
                case "joystick":
                    mainJoystickPort = controlsMap.get(s);
                    break;
                
                case "x_axis":
                    xAxis = controlsMap.get(s);
                    break;
                
                case "y_axis":
                    yAxis = controlsMap.get(s);
                    break;
                
                case "z_axis":
                    zAxis = controlsMap.get(s);
                    break;
                
                default:
                    System.err.println("Unknown control: " + s);
            }
        }
    }
}