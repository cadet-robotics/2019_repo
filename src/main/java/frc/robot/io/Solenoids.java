package frc.robot.io;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.config.ConfigHandler;

public class Solenoids extends ConfigHandler {
    public DoubleSolenoid clawSolenoid;

    public Solenoids(JsonObject configIn) {
        super(configIn, "solenoids");
    }

    @Override
    public void loadItem(String k, JsonElement v) {

    }

    @Override
    public void finalizeItems() {

    }

    @Override
    public void error() {

    }
}
