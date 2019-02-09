package frc.robot.io;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.javafx.collections.UnmodifiableObservableMap;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;

import java.io.IOException;
import java.util.HashMap;

/**
 * Contains values for RIO ports
 * Javadoc comments lovingly provided by Alex Pickering
 * 
 * @author Owen Avery
 */
public class Ports {
    private HashMap<String, DigitalInput> diPorts = new HashMap<>();
    private HashMap<String, DigitalOutput> doPorts = new HashMap<>();
    private HashMap<String, AnalogInput> aiPorts = new HashMap<>();
    private HashMap<String, AnalogOutput> aoPorts = new HashMap<>();

    private static HashMap<String, String> typeMap = new HashMap<>();

    static {
        //typeMap.put("")
    }
    
    /**
     * Default constructor
     * Currently empty
     * 
     * @param jsonConfig Config instance to read from
     * @throws IOException
     */
    public Ports(JsonObject jsonConfig) throws IOException {
        /*
        JsonObject con = null;
        if (!jsonConfig.has("dio")) return;
        try {
            con = jsonConfig.get("dio").getAsJsonObject();
        } catch (UnsupportedOperationException e) {
            return;
        }
        for (String s : con.keySet()) {
            if (s.equals("desc")) continue;
            try {
                JsonElement e = con.get(s);
                dio.put(s, new DigitalOutput(e.getAsInt()));
            } catch (IndexOutOfBoundsException|UnsupportedOperationException e) {
                e.printStackTrace();
            }
        }
        */
    }
}