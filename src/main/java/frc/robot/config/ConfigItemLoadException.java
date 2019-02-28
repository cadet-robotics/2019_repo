package frc.robot.config;

import com.google.gson.JsonElement;

/**
 * An error representing a failure to load an item in a sub-config
 *
 * @author Owen Avery
 */
public class ConfigItemLoadException extends RuntimeException {
    private String subconfig;
    private String itemName;
    private Class<?> expectedType;
    private Class<?> receivedType;

    /**
     * The default constructor
     *
     * @param subconfigIn The name of the subconfig ("pwm", "controls", etc.) we were parsing
     * @param itemNameIn The name of the item in the subconfig that we tried and failed to read
     * @param expectedTypeIn The type/class we wanted the item's value to be
     * @param receivedTypeIn The type/class the item's value actually was
     */
    public ConfigItemLoadException(String subconfigIn, String itemNameIn, Class<?> expectedTypeIn, Class<?> receivedTypeIn) {
        super(String.format("Error while loading %s subconfig: item %s expecting type [%s], got type [%s]", subconfigIn, itemNameIn, expectedTypeIn.getSimpleName(), receivedTypeIn.getSimpleName()));
        subconfig = subconfigIn;
        itemName = itemNameIn;
        expectedType = expectedTypeIn;
        receivedType = receivedTypeIn;
    }

    /**
     * An alternate constructor for simpler use
     * Takes the item's value directly, instead of its type/class
     *
     * @param subconfigIn The name of the subconfig ("pwm", "controls", etc.) we were parsing
     * @param itemNameIn The name of the item in the subconfig that we tried and failed to read
     * @param expectedTypeIn The type/class we wanted the item's value to be
     * @param receivedValue The item's value
     */
    public ConfigItemLoadException(String subconfigIn, String itemNameIn, Class<?> expectedTypeIn, JsonElement receivedValue) {
        this(subconfigIn, itemNameIn, expectedTypeIn, ConfigUtil.getJSONClass(receivedValue));
    }

    public String getSubConfig() {
        return subconfig;
    }

    public String getItemName() {
        return itemName;
    }

    public Class<?> getExpectedType() {
        return expectedType;
    }

    public Class<?> getReceivedType() {
        return receivedType;
    }
}