package frc.robot.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ConfigUtil {
    public static JsonObject getObject(JsonElement e) {
        if (e == null) return null;
        if (e.isJsonObject()) return (JsonObject) e;
        else return null;
    }

    public static Integer getInt(JsonElement e) {
        if (e instanceof JsonPrimitive) {
            try {
                return e.getAsInt();
            } catch (ClassCastException|NumberFormatException ex) {
                return null;
            }
        } else return null;
    }

    public static String getString(JsonElement e) {
        if (e instanceof JsonPrimitive) {
            try {
                return e.getAsString();
            } catch (ClassCastException ex) {
                return null;
            }
        } else return null;
    }

    public static boolean loadAll(JsonObject config, String key, BiConsumer<String, JsonElement> con) {
        JsonObject obj = getObject(config.get(key));
        if (obj == null) return false;
        for (String s : obj.keySet()) {
            con.accept(s, obj.get(s));
        }
        return true;
    }

    public static boolean loadAllInts(JsonObject config, String key, BiConsumer<String, Integer> con) {
        return loadAll(config, key, (k, v) -> {
            Integer i = getInt(v);
            if (i != null) con.accept(k, i);
        });
    }

    public static boolean loadAllInts(JsonObject config, String key, BiConsumer<String, Integer> con, Consumer<String> error) {
        if (error == null) return loadAllInts(config, key, con);
        return loadAll(config, key, (k, v) -> {
            Integer i = getInt(v);
            if (i == null) {
                error.accept(k);
            } else {
                con.accept(k, i);
            }
        });
    }
}