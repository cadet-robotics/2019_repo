package frc.robot.io;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.wpi.first.wpilibj.PWMVictorSPX;

import java.io.IOException;

public class Motors {
    public PWMVictorSPX frontLeft = null;
    public PWMVictorSPX rearLeft = null;
    public PWMVictorSPX frontRight = null;
    public PWMVictorSPX rearRight = null;

    public Motors(JsonObject configIn) throws IOException {
        int fl = 2, rl = 3, fr = 1, rr = 0; //Default values
        try {
            JsonObject con = configIn.getAsJsonObject("motors");
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
        if (fl != -1) frontLeft = new PWMVictorSPX(fl);
        if (rl != -1) rearLeft = new PWMVictorSPX(rl);
        if (fr != -1) frontRight = new PWMVictorSPX(fr);
        if (rr != -1) rearRight = new PWMVictorSPX(rr);
    }
}