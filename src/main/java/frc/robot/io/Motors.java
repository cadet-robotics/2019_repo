package frc.robot.io;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import frc.robot.config.ConfigLoader;

import java.io.IOException;

public class Motors {
    public PWMVictorSPX frontLeft;
    public PWMVictorSPX rearLeft;
    public PWMVictorSPX frontRight;
    public PWMVictorSPX rearRight;

    public Motors() throws IOException {
        int fl = 2, rl = 3, fr = 1, rr = 0; //Default values
        try {
            JsonObject con = ConfigLoader.loadConfigFile().getAsJsonObject("motors");
            for (String s : con.keySet()) {
                JsonElement e = con.get(s);
                switch (s) {
                    case "front left":
                        fl = e.getAsInt();
                        break;
                    case "rear left":
                        rl = e.getAsInt();
                        break;
                    case "front right":
                        fr = e.getAsInt();
                        break;
                    case "rear right":
                        rr = e.getAsInt();
                        break;
                }
            }
        } catch (UnsupportedOperationException e) {
            throw new IOException(e);
        }
        frontLeft = new PWMVictorSPX(fl);
        rearLeft = new PWMVictorSPX(rl);
        frontRight = new PWMVictorSPX(fr);
        rearRight = new PWMVictorSPX(rr);
    }
}