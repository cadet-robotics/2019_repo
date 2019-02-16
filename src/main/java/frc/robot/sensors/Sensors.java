package frc.robot.sensors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.google.gson.JsonPrimitive;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * Contains the sensors
 * Javadoc comment lovingly provided by Alex Pickering
 *
 * @author Owen Avery, Alex Pickering
 */
public class Sensors {
    //JsonObject configJSON;

    //null until we have a gyro on the robot
    public Gyro gyro = null; //new ADXRS450_Gyro();

    public ProximitySensor[] elevatorSensors = new ProximitySensor[6];

    /**
     * Initializes any sensors that need to use the config
     *
     * @param config The robot config file
     * @author Alex Pickering
     */
    public void init(JsonObject config) {
        //this.configJSON = config;

        JsonElement dioe = config.get("dio");
        if ((dioe == null) || !dioe.isJsonObject()) return;
        JsonObject dioJSON = dioe.getAsJsonObject();

        for(String k : dioJSON.keySet()) {
            JsonElement item = dioJSON.get(k);
            if ((item == null) || !item.isJsonPrimitive() || !(item instanceof JsonPrimitive)) continue;
            int itemInt = item.getAsInt();

            switch(k) {
                case "proximity sensor 1":
                    elevatorSensors[0] = new ProximitySensor(itemInt);
                    break;

                case "proximity sensor 2":
                    elevatorSensors[1] = new ProximitySensor(itemInt);
                    break;

                case "proximity sensor 3":
                    elevatorSensors[2] = new ProximitySensor(itemInt);
                    break;

                case "proximity sensor 4":
                    elevatorSensors[3] = new ProximitySensor(itemInt);
                    break;

                case "proximity sensor 5":
                    elevatorSensors[4] = new ProximitySensor(itemInt);
                    break;

                case "proximity sensor 6":
                    elevatorSensors[5] = new ProximitySensor(itemInt);
                    break;
            }
        }
    }
}