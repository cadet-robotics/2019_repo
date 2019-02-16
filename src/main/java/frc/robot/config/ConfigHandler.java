package frc.robot.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Provides a base for config loading classes
 *
 * @author Owen Avery
 */
public abstract class ConfigHandler {
    private static final boolean DEBUG_DEFAULT = true;

    private JsonObject configJSON;

    public ConfigHandler(JsonObject configIn, String subItemName) {
        this.configJSON = configIn;
        System.out.println("[INIT] Loading " + getClass().getSimpleName());
        JsonElement sube = configJSON.get(subItemName);
        if ((sube == null) || !sube.isJsonObject()) {
            error();
            return;
        }
        JsonObject subconfig = sube.getAsJsonObject();

        //Debug - outputs all the json
        if(isDebug()){
            for(String s : configJSON.keySet()){
                System.out.println(s + ": " + configJSON.get(s));
            }
        }

        for(String k : subconfig.keySet()){
            if(k.equals("desc") || k.contains("placeholder")) continue;
            loadItem(k, subconfig.get(k));
        }

        finalizeItems();
    }

    public abstract void loadItem(String k, JsonElement v);

    public abstract void finalizeItems();

    public abstract void error();

    public boolean isDebug() {
        return DEBUG_DEFAULT;
    }
}