package frc.robot.config;

import com.google.gson.*;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;

/**
 * A class that provides config parsing utility functions
 *
 * @author Owen Avery
 */
public class ConfigUtil {
    /**
     * A method that turns a JsonElement into an Integer
     * Doesn't throw errors, instead returns null
     *
     * ConfigUtil.loadAll() or derivatives of it should probably be used instead
     *
     * @param e The JsonElement to convert
     * @return The JsonElement as an Integer or null
     */
    private static Integer getInt(JsonElement e) {
        if (e instanceof JsonPrimitive) {
            try {
                return e.getAsInt();
            } catch (ClassCastException | NumberFormatException ex) {
                return null;
            }
        } else return null;
    }

    // This is the best way by far to do this
    // This uses reflection, but it makes much more sense than ~8 if statements
    // It makes JsonPrimitive.value, a normally private field, accessible to this class
    private static Field valueField = null;

    static {
        try {
            // Get the value field and make it accessible
            Field f = JsonPrimitive.class.getDeclaredField("value");
            f.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the class of the JsonElement we should use for error messages
     * Handles JsonPrimitives
     *
     * @param e The JsonElement to get the class of
     * @return The class we should use for the JsonElement in error messages
     */
    public static Class<?> getJSONClass(JsonElement e) {
        if ((e == null) || (e instanceof JsonNull)) {
            // It's null
            return JsonNull.class;
        } else if (e instanceof JsonObject) {
            // It's a JSON object
            return JsonObject.class;
        } else if (e instanceof JsonArray) {
            // It's a JSON array
            return JsonArray.class;
        } else if (e instanceof JsonPrimitive) {
            try {
                // It's a primitive, get the primitive's class
                return valueField.get(e).getClass();
            } catch (IllegalAccessException ex) {
                // We're screwed
                throw new RuntimeException(ex);
            }
        } else {
            // A fallback, should never really be used
            return e.getClass();
        }
    }

    /**
     * Loads a subconfig from the main config file
     *
     * Throws ConfigSubConfigLoadExceptions as necessary
     *
     * @param config The main config file, represented as a JsonObject
     * @param key The key that the subconfig we're reading is stored at in the main config
     * @param con A lambda that takes key (String) and value (JsonElement) pairs
     *            It is executed on each item and its value in the subconfig itself
     */
    public static void loadAll(JsonObject config, String key, BiConsumer<String, JsonElement> con) {
        JsonElement subconfigElement = config.get(key);
        if (subconfigElement instanceof JsonObject) {
            JsonObject subconfigObject = (JsonObject) subconfigElement;
            for (String s : subconfigObject.keySet()) {
                con.accept(s, subconfigObject.get(s));
            }
        } else {
            throw new ConfigSubConfigLoadException(key, config.get(key));
        }
    }

    /**
     * Loads a subconfig from the main config file
     *
     * Automatically converts the values in key/value pairs into Integers
     *
     * Throws ConfigSubConfigLoadExceptions and as necessary
     *
     * @param config The main config file, represented as a JsonObject
     * @param key The key that the subconfig we're reading is stored at in the main config
     * @param con A lambda that takes key (String) and value (Integer) pairs
     *            It is executed on each item and its value, provided the value is an Integer, in the subconfig itself
     */
    public static void loadAllInts(JsonObject config, String key, BiConsumer<String, Integer> con) {
        loadAll(config, key, (k, v) -> {
            Integer i = getInt(v);
            if ((v instanceof JsonPrimitive) && (((JsonPrimitive) v).isNumber())) {
                con.accept(k, i);
            } else {
                throw new ConfigItemLoadException(key, k, Integer.class, v);
            }
        });
    }
}