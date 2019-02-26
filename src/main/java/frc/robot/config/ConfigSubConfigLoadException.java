package frc.robot.config;

import com.google.gson.JsonElement;

/**
 * An error representing a failure to load a sub-config
 *
 * @author Owen Avery
 */
public class ConfigSubConfigLoadException extends RuntimeException {
    private String subconfig;
    private Class<?> receivedType;

    /**
     * The default constructor
     *
     * @param subconfigIn The name of the subconfig ("pwm", "controls", etc.) we were parsing
     * @param receivedTypeIn The type/class of the subconfig we got instead of the JsonObject we were expecting
     */
    public ConfigSubConfigLoadException(String subconfigIn, Class<?> receivedTypeIn) {
        super("Failed to load subconfig " + subconfigIn + ", expected object and got type [" + receivedTypeIn.getSimpleName() + "]");
        subconfig = subconfigIn;
        receivedType = receivedTypeIn;
    }

    /**
     * An alternate constructor for simpler use
     * Takes the subconfig's value directly, instead of its type/class
     *
     * @param subconfigIn The name of the subconfig ("pwm", "controls", etc.) we were parsing
     * @param receivedValue The value of the subconfig we got instead of the JsonObject we were expecting
     */
    public ConfigSubConfigLoadException(String subconfigIn, JsonElement receivedValue) {
        this(subconfigIn, ConfigUtil.getJSONClass(receivedValue));
    }

    public String getSubconfig() {
        return subconfig;
    }

    public Class<?> getReceivedType() {
        return receivedType;
    }
}