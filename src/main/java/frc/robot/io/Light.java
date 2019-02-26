package frc.robot.io;

import com.google.gson.JsonObject;
import edu.wpi.first.wpilibj.DigitalOutput;
import frc.robot.config.ConfigUtil;

/**
 * Handles the camera light on the front of the robot
 *
 * @author Owen Avery
 */
public class Light {
    public DigitalOutput light = null;

    /**
     * Default constructor
     * Doesn't extend ConfigHandlerInt because we're looking for a single config item
     * @param configIn
     */
    public Light(JsonObject configIn) {
        ConfigUtil.loadAllInts(configIn, "dio", (k, v) -> {
            if (k.equals("light")) {
                light = new DigitalOutput(v);
            }
        });
        if (light == null) light = new DigitalOutput(2);
        light.enablePWM(0);
        light.setPWMRate(2000);
    }

    /**
     * Sets the light's intensity
     *
     * @param d the light's intensity, 0-1 inclusive
     */
    public void setIntensity(double d) {
        if (d < 0) d = 0;
        if (d > 1) d = 1;
        light.updateDutyCycle(d);
    }
}