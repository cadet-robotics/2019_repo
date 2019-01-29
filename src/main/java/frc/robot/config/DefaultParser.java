package frc.robot.config;

/**
 * Default config value parser
 * Parses for ints, doubles, booleans, and Strings
 * 
 * @author Alex Pickering
 */
public class DefaultParser implements ConfigParser {
    @Override
    public Object parse(String val, String type) throws IllegalArgumentException{
        switch(type){
            case "int":
                return Integer.parseInt(val);
            
            case "double":
                return Double.parseDouble(val);
            
            case "boolean":
                return Boolean.parseBoolean(val);
            
            case "String":
                return val;
                
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}