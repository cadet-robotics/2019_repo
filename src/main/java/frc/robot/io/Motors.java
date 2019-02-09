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
        int fl = 2, rl = 3, fr = 1, rr = 0; //Default values
        int e1 = -1, e2 = -1, e3 = -1, e4 = -1;
        try {
            JsonObject con = configIn.getAsJsonObject("pwm");
            for (String s : con.keySet()) {
                JsonElement e = con.get(s);
                SpeedController speed = getSpeedController(e);
                if (speed != null) map.put(s, speed);
            }
        } catch (UnsupportedOperationException e) {
            throw new IOException(e);
        }
    }

    public static SpeedController getSpeedController(JsonElement e) {
        JsonObject obj;
        try {
            obj = e.getAsJsonObject();
        } catch (UnsupportedOperationException ex) {
            try {
                int j = e.getAsInt();
                return new PWMVictorSPX(j);
            } catch (UnsupportedOperationException ex2) {
                return null;
            }
        }
        try {
            int port;
            try {
                port = obj.get("port").getAsInt();
            } catch (UnsupportedOperationException ex) {
                try {
                    JsonArray motorList = obj.get("port").getAsJsonArray();
                    ArrayList<SpeedController> s = new ArrayList<>();
                    SpeedController t;
                    for (JsonElement e2 : motorList) {
                        if ((t = getSpeedController(e2)) != null) {
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
            SpeedController s = null;
            switch (type) {
                case "victor":
                    s = new PWMVictorSPX(port);
                    break;
                case "talon":
                    s = new PWMTalonSRX(port);
                    break;
                case "spark":
                    s = new Spark(port);
                    break;
            }
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