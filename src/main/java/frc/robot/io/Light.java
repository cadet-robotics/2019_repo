package frc.robot.io;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import edu.wpi.first.wpilibj.PWM;

/**
 * Handles the camera light on the front of the robot
 */
public class Light {
    public PWM light = null;

    /**
     * Default constructor
     * Doesn't extend ConfigHandlerInt because we're looking for a single config item
     * @param o
     */
    public Light(JsonObject o) {
        JsonObject dio = null;
        JsonElement dioE;
        if (o.has("dio") && (dioE = o.get("dio")).isJsonObject()) {
            dio = dioE.getAsJsonObject();
            JsonElement lightE;
            if (dio.has("light") && (lightE = dio.get("light")).isJsonPrimitive() && ((JsonPrimitive) lightE).isNumber()) {
                light = new PWM(lightE.getAsInt());
            }
        }
        if (light == null) light = new PWM(2);
    }

    /**
     * Sets the light's intensity
     *
     * @param d the light's intensity, 0-1 inclusive
     */
    public void setIntensity(double d) {
        if (d < 0) d = 0;
        if (d > 1) d = 1;
        light.setRaw((int) (d * 255));
    }
}