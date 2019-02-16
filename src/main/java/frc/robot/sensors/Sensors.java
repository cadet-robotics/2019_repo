package frc.robot.sensors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.google.gson.JsonPrimitive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.config.ConfigHandlerInt;

/**
 * Contains the sensors
 * Javadoc comment lovingly provided by Alex Pickering
 *
 * Later modified to extend ConfigHandlerInt
 *
 * @author Owen Avery, Alex Pickering
 */
public class Sensors extends ConfigHandlerInt {
    //JsonObject configJSON;

    //null until we have a gyro on the robot
    public Gyro gyro = null; //new ADXRS450_Gyro();

    public ProximitySensor[] elevatorSensors = new ProximitySensor[6];

    public Sensors(JsonObject configIn) {
        super(configIn, "dio");
    }

    /**
     * Copied nearly wholesale from the old init method, initializes this class' objects
     *
     * @param k The name of the object
     * @param itemInt The index of the object in the pcm/pwm ports/digital io ports/etc.
     */
    @Override
    public void loadItem(String k, int itemInt) {
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

    @Override
    public void finalizeItems() {
    }

    @Override
    public void error() {
    }
}