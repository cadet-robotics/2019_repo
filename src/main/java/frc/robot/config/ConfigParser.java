package frc.robot.config;

/**
 * Interface for classes that parse a config value
 * 
 * @author Alex Pickering
 */
public interface ConfigParser {
    /**
     * Parses the given value for the given type
     * Implementations of this method should switch type and return the results
     * of type-specific methods
     * 
     * @param val The value to parse
     * @param type The type to parse for
     * @return The parsed value
     */
    public abstract Object parse(String val, String type) throws IllegalArgumentException;
}