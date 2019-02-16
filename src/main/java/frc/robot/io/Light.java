package frc.robot.io;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import edu.wpi.first.wpilibj.PWM;

public class Light {
    public PWM light = null;

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
}
