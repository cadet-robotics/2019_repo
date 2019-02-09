package frc.robot.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.javaws.exceptions.InvalidArgumentException;
import edu.wpi.first.wpilibj.*;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Contains motor objects and loads their config
 * Javadoc comments lovingly provided by Alex Pickering
 * 
 * @author Owen Avery
 */
public class Motors {
    private HashMap<String, SpeedController> map = new HashMap<>();

    private static HashMap<String, String> typeMap = new HashMap<>();

    static {
        typeMap.put("front left", "victor");
        typeMap.put("rear left", "victor");
        typeMap.put("front right", "victor");
        typeMap.put("rear right", "victor");
    }

    public SpeedController getSpeedControllerOrInert(String s) {
        return getSpeedControllerOrFallback(s, new InertSpeedController());
    }

    public SpeedController getSpeedControllerOrFallback(String s, SpeedController sf) {
        if (!map.containsKey(s)) return sf;
        return map.get(s);
    }

    public SpeedController getSpeedController(String s) {
        return map.get(s);
    }

    public boolean hasSpeedController(String s) {
        return map.containsKey(s);
    }

    /**
     * Default constructor
     * 
     * @param configIn JSON Config reading
     * @throws IOException
     */
    public Motors(JsonObject configIn) throws IOException {
        //int fl = 2, rl = 3, fr = 1, rr = 0; //Default values
        try {
            JsonObject con = configIn.getAsJsonObject("pwm");
            for (String s : con.keySet()) {
                if (s.equals("desc")) continue;
                JsonElement e = con.get(s);
                SpeedController speed = getSpeedController(e, s);
                if (speed != null) map.put(s, speed);
            }
            SpeedController t;
            if (!map.containsKey("front left")) {
                t = getMotor(2, typeMap.get("front left"));
                if (t != null) map.put("front left", t);
            }
            if (!map.containsKey("rear left")) {
                t = getMotor(3, typeMap.get("rear left"));
                if (t != null) map.put("rear left", t);
            }
            if (!map.containsKey("front right")) {
                t = getMotor(1, typeMap.get("front right"));
                if (t != null) map.put("front right", t);
            }
            if (!map.containsKey("rear right")) {
                t = getMotor(0, typeMap.get("rear right"));
                if (t != null) map.put("rear right", t);
            }
        } catch (UnsupportedOperationException e) {
            throw new IOException(e);
        }
    }

    public static SpeedController getSpeedController(JsonElement e, String n) {
        try {
            int j = e.getAsInt();
            String t = "victor";
            if (typeMap.containsKey(n)) {
                t = typeMap.get(n);
            }
            return getMotor(j, t);
        } catch (UnsupportedOperationException ex2) {}
        JsonObject obj;
        try {
            obj = e.getAsJsonObject();
        } catch (UnsupportedOperationException ex) {return null;}
        try {
            int port;
            try {
                port = obj.get("port").getAsInt();
            } catch (UnsupportedOperationException ex) {
                try {
                    JsonArray motorList = obj.get("port").getAsJsonArray();
                    ArrayList<SpeedController> s = new ArrayList<>();
                    SpeedController t;
                    for (int i = 0; i < motorList.size(); i++) {
                        if ((t = getSpeedController(motorList.get(i), n + "#" + i)) != null) {
                            s.add(t);
                        }
                    }
                    if (s.size() == 0) return null;
                    if (s.size() == 1) return s.get(0);
                    SpeedController speedFirst = s.remove(0);
                    SpeedController[] list = s.toArray(new SpeedController[0]);
                    return new SpeedControllerGroup(speedFirst, list);
                } catch (UnsupportedOperationException ex2) {
                    return null;
                }
            }
            String type;
            try {
                type = obj.get("type").getAsString();
            } catch (UnsupportedOperationException ex) {
                type = "victor";
            }
            SpeedController s = getMotor(port, type);
            if (s == null) return null;
            try {
                if (obj.get("reverse").getAsBoolean()) {
                    s.setInverted(true);
                }
            } catch (UnsupportedOperationException ex) {}
            return s;
        } catch (UnsupportedOperationException ex) {
            return null;
        }
    }

    public static SpeedController getMotor(int port, String type) {
        switch (type) {
            case "victor":
                return new PWMVictorSPX(port);
            case "talon":
                return new PWMTalonSRX(port);
            case "spark":
                return new Spark(port);
        }
        return null;
    }
}

class InertSpeedController implements SpeedController {
    double speed = 0;
    boolean inverted = false;

    @Override
    public void set(double speedIn) {
        speed = speedIn;
    }

    @Override
    public double get() {
        return speed;
    }

    @Override
    public void setInverted(boolean isInverted) {
        inverted = isInverted;
    }

    @Override
    public boolean getInverted() {
        return inverted;
    }

    @Override
    public void disable() {
    }

    @Override
    public void stopMotor() {
    }

    @Override
    public void pidWrite(double output) {
        speed = 0;
    }
}