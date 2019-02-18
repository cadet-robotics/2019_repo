package frc.robot;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.sensors.SightData;

/**
 * Works with networktables to get vision data
 * <p>Javadoc comments lovingly provided by Alex Pickering
 * 
 * @author Owen Avery
 */
public class UpdateLineManager {
    /**
     * Updates the data for line tracking in the vision system
     * 
     * @param nt NetworkTables instance
     * @param see SightData instance
     */
    public static int startListener(NetworkTableInstance nt, SightData see) {
        NetworkTableEntry dataTable = nt.getTable("ShuffleBoard").getEntry("line");
        
        return dataTable.addListener((e) -> {
            double[] n;
            
            try {
                n = e.value.getDoubleArray();
            } catch (ClassCastException ex) {
                ex.printStackTrace();
                return;
            }
            
            if (n.length != 4) return;
            see.setPoints(n[0], n[1], n[2], n[3]);
        }, EntryListenerFlags.kUpdate + EntryListenerFlags.kNew);
    }
}