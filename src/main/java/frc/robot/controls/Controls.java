package frc.robot.controls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.config.ConfigLoader;

/**
 * Contains controls objects and handles loading them from config
 * 
 * @author Alex Pickering
 */
public class Controls {
    //Config object
    JSONObject configJSON;
    
    //Config'd controls record
    ArrayList<String> configuredControls = new ArrayList<>();
    
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
    
    public ArrayList<String> getConfiguredControls(){
        return configuredControls;
    }
    
    /**
     * Initializes the controls
     * Loads from config and creates the objects
     */
    public void init(JSONObject configJSON){
        this.configJSON = configJSON;
        
        try{
            loadControls();
        } catch(IOException | ParseException e){
            System.err.println("Failed to load controls");
            e.printStackTrace();
        }
        
        mainJoystick = new Joystick(mainJoystickPort);
    }
    
    /**
     * Loads the controls from config
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public void loadControls() throws IOException, ParseException {
        JSONObject configJSON = ConfigLoader.loadConfigFile();
        JSONObject controlsJSON = (JSONObject) configJSON.get("controls");
        
        //Debug - outputs all the json
        if(debug){
            for(Iterator<Object> iter = configJSON.keySet().iterator(); iter.hasNext();){
                String key = (String) iter.next();
                System.out.println(key + ": " + configJSON.get(key));
            }
        }
        
        configuredControls = new ArrayList<>();
        
        for(Iterator<Object> iter = controlsJSON.keySet().iterator(); iter.hasNext();){
            String k = (String) iter.next();
            Object obj = controlsJSON.get(k);
            configuredControls.add(k);
            
            switch(k){
                case "main joystick":
                    mainJoystickPort = lti(obj);
                    break;
                
                case "main joystick x-axis":
                    xAxis = lti(obj);
                    break;
                
                case "main joystick y-axis":
                    yAxis = lti(obj);
                    break;
                
                case "main joystick z-axis":
                    zAxis = lti(obj);
                    break;
                
                default:
                    configuredControls.remove(k);
                    if(!k.equals("desc")) System.err.println("Unrecognized control: " + k);
            }
        }
    }
    
    /**
     * Long to int
     * because JSON integers are longs
     * 
     * @param l the Long
     * @return the int
     */
    int lti(Object l){
        return ((Long) l).intValue();
    }
}